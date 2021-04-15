package com.shopme.admin.sanpham;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shopme.admin.nhanhieu.NhanHieuService;
import com.shopme.common.entity.NhanHieu;
import com.shopme.common.entity.SanPham;

@Controller
public class SanPhamController {
	@Autowired private SanPhamService sanPhamService;
	@Autowired private NhanHieuService nhanHieuService;

	@GetMapping("/sanpham")
	public String listAll(Model model) {
		List<SanPham> listProducts = sanPhamService.listAll();

		model.addAttribute("listProducts", listProducts);

		return "sanpham/sanpham";
	}
	
	@GetMapping("/sanpham/new")
	public String newProduct(Model model) {
		List<NhanHieu> listBrands = nhanHieuService.listAll();

		SanPham product = new SanPham();
		product.setTrangThai(true);
		product.setTrongKho(true);

		model.addAttribute("product", product);
		model.addAttribute("listBrands", listBrands);
		model.addAttribute("pageTitle", "Create New Product");

		return "sanpham/sanpham_form";
	}

	@PostMapping("/sanpham/save")
	public String saveProduct(SanPham product) {
		System.out.println("Product Name: " + product.getTen());
		System.out.println("Brand ID: " + product.getNhanhieu().getMaNhanHieu());
		System.out.println("Category ID: " + product.getDanhmuc().getMaDanhMuc());

		return "redirect:/sanpham";
	}
}
