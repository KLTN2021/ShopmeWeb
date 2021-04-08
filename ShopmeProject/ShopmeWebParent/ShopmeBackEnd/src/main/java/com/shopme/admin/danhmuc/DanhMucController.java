package com.shopme.admin.danhmuc;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String listAll(Model model) {
		List<DanhMuc> listCategories = service.listAll();
		model.addAttribute("listCategories", listCategories);
		
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
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		category.setHinhAnh(fileName);

		DanhMuc savedCategory = service.save(category);
		String uploadDir = "../category-images/" + savedCategory.getMaDanhMuc();
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

		ra.addFlashAttribute("message", "The category has been saved successfully.");
		return "redirect:/danhmuc";
	}
}
