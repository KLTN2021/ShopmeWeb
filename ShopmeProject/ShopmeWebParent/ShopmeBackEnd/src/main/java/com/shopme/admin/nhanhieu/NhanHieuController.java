package com.shopme.admin.nhanhieu;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.admin.danhmuc.DanhMucService;
import com.shopme.common.entity.NhanHieu;
import com.shopme.common.entity.DanhMuc;

@Controller
public class NhanHieuController {
	@Autowired
	private NhanHieuService nhanHieuService;
	
	@Autowired
	private DanhMucService danhMucService;

	@GetMapping("/nhanhieu")
	public String listAll(Model model) {
		List<NhanHieu> listBrands = nhanHieuService.listAll();
		model.addAttribute("listBrands", listBrands);

		return "nhanhieu/nhanhieu";
	}
	
	@GetMapping("/nhanhieu/new")
	public String newBrand(Model model) {
		List<DanhMuc> listCategories = danhMucService.listCategoriesUsedInForm();

		model.addAttribute("listCategories", listCategories);
		model.addAttribute("brand", new NhanHieu());
		model.addAttribute("pageTitle", "Tạo nhãn hiệu mới");

		return "nhanhieu/nhanhieu_form";		
	}
}
