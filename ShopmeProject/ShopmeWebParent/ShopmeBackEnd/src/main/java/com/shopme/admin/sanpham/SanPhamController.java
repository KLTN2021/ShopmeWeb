package com.shopme.admin.sanpham;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.common.entity.SanPham;

@Controller
public class SanPhamController {
	@Autowired private SanPhamService sanPhamService;

	@GetMapping("/sanpham")
	public String listAll(Model model) {
		List<SanPham> listProducts = sanPhamService.listAll();

		model.addAttribute("listProducts", listProducts);

		return "sanpham/sanpham";
	}
}
