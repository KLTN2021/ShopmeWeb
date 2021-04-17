package com.shopme.admin.sanpham;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		model.addAttribute("pageTitle", "Tạo sản phẩm mới");

		return "sanpham/sanpham_form";
	}

	@PostMapping("/sanpham/save")
	public String saveProduct(SanPham product, RedirectAttributes ra) {
		sanPhamService.save(product);
		ra.addFlashAttribute("message", "Sản phẩm đã được lưu thành công.");

		return "redirect:/sanpham";
	}
	
	@GetMapping("/sanpham/{maSanPham}/trangThai/{status}")
	public String updateCategoryEnabledStatus(@PathVariable("maSanPham") Integer maSanPham,
			@PathVariable("status") boolean trangThai, RedirectAttributes redirectAttributes) {
		sanPhamService.updateProductEnabledStatus(maSanPham, trangThai);
		String status = trangThai ? "kích hoạt" : "tắt kích hoạt";
		String message = "Sản phẩm có ID " + maSanPham + " đã được " + status;
		redirectAttributes.addFlashAttribute("message", message);

		return "redirect:/sanpham";
	}
	
	@GetMapping("/sanpham/delete/{maSanPham}")
	public String deleteProduct(@PathVariable(name = "maSanPham") Integer maSanPham, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			sanPhamService.delete(maSanPham);

			redirectAttributes.addFlashAttribute("message", 
					"Sản phẩm có ID " + maSanPham + " đã được xóa thành công");
		} catch (SanPhamNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}

		return "redirect:/sanpham";
	}
}
