package com.shopme.admin.danhmuc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
