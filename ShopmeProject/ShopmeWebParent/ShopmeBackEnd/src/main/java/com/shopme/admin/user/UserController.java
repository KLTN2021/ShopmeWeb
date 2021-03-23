package com.shopme.admin.user;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.common.entity.PhanQuyen;
import com.shopme.common.entity.TaiKhoan;

@Controller
public class UserController {
	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	public String listFirstPage(Model model) {
		return listByPage(1, model);
	}
	
	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model) {
		Page<TaiKhoan> page = service.listByPage(pageNum);
		List<TaiKhoan> listUsers = page.getContent();
		
		long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
		long endCount = startCount + UserService.USERS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listUsers", listUsers);
		
		return "users";
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<PhanQuyen> listRoles = service.listRoles();
		
		TaiKhoan user = new TaiKhoan();
		user.setTrangThai(true);
		
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Tạo tài khoản mới");
		
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(TaiKhoan user, RedirectAttributes redirectAttributes,
			 @RequestParam("image") MultipartFile multipartFile) throws IOException {
		
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setHinhAnh(fileName);
			TaiKhoan savedUser = service.save(user);
			
			String uploadDir = "user-photos/" + savedUser.getMaTK();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
		} else {
			if (user.getHinhAnh().isEmpty()) user.setHinhAnh(null);
			service.save(user);
		}
		redirectAttributes.addFlashAttribute("message", "Thông tin người dùng đã được lưu thành công");
		
		return "redirect:/users";
	}
	
	@GetMapping("/users/edit/{maTK}")
	public String editUser(@PathVariable(name = "maTK") Integer maTK,
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			TaiKhoan user = service.get(maTK);
			List<PhanQuyen> listRoles = service.listRoles();
			
			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Chỉnh sửa thông tin người dùng (mã tài khoản: " + maTK + ")");
			model.addAttribute("listRoles", listRoles);
			
			return "user_form";
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return "redirect:/users";
		}
	}
	
	@GetMapping("/users/delete/{maTK}")
	public String deleteUser(@PathVariable(name = "maTK") Integer maTK,
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			service.delete(maTK);;
			redirectAttributes.addFlashAttribute("message", "Xóa người dùng có mã tài khoản: " 
					+ maTK + " thành công");
		} catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		
		return "redirect:/users";
	}
	
	@GetMapping("users/{maTK}/trangThai/{itrangthai}") 
	public String capNhatTrangThaiKichHoatNguoiDung(@PathVariable("maTK") Integer maTK,
			@PathVariable ("itrangthai") boolean trangThai, RedirectAttributes redirectAttributes) {
		service.capNhatTrangThaiNguoiDung(maTK, trangThai);
		String itrangthai = trangThai? " kích hoạt" : " tắt kích hoạt";
		String thongbao = "Người dùng có mã tài khoản " + maTK + " đã" + itrangthai;
		redirectAttributes.addFlashAttribute("message", thongbao);
		
		return "redirect:/users";
		
		}
	
}