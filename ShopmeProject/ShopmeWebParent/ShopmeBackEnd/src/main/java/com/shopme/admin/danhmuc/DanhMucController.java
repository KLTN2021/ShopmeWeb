package com.shopme.admin.danhmuc;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import com.shopme.common.entity.DanhMuc;

@Controller
public class DanhMucController {
	@Autowired
	private DanhMucService service;
	
	@GetMapping("/danhmuc")
	public String listAll(@Param("sortDir") String sortDir, Model model) {
		if (sortDir ==  null || sortDir.isEmpty()) {
			sortDir = "asc";
		}

		List<DanhMuc> listCategories = service.listAll(sortDir);

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

		model.addAttribute("listCategories", listCategories);
		model.addAttribute("reverseSortDir", reverseSortDir);

		return "danhmuc/danhmuc";
	}
	
	@GetMapping("/danhmuc/new")
	public String newCategory(Model model) {
		List<DanhMuc> listCategories = service.listCategoriesUsedInForm();

		model.addAttribute("category", new DanhMuc());
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("pageTitle", "Tạo danh mục mới");

		return "danhmuc/danhmuc_form";
	}
	
	@PostMapping("/danhmuc/save")
	public String saveCategory(DanhMuc category, 
			@RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes ra) throws IOException {
		
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			category.setHinhAnh(fileName);

			DanhMuc savedCategory = service.save(category);
			String uploadDir = "../category-images/" + savedCategory.getMaDanhMuc();
			
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			service.save(category);
		}

		ra.addFlashAttribute("message", "Danh mục đã được lưu thành công.");
		return "redirect:/danhmuc";
	}
	
	@GetMapping("/danhmuc/edit/{maDanhMuc}")
	public String editCategory(@PathVariable(name = "maDanhMuc") Integer maDanhMuc, Model model,
			RedirectAttributes ra) {
		try {
			DanhMuc category = service.get(maDanhMuc);
			List<DanhMuc> listCategories = service.listCategoriesUsedInForm();

			model.addAttribute("category", category);
			model.addAttribute("listCategories", listCategories);
			model.addAttribute("pageTitle", "Edit Category (ID: " + maDanhMuc + ")");

			return "danhmuc/danhmuc_form";			
		} catch (DanhMucNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return "redirect:/danhmuc";
		}
	}
}
