package com.shopme.admin.nhanhieu;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shopme.common.entity.NhanHieu;

@Controller
public class NhanHieuController {
	@Autowired
	private NhanHieuService service;

	@GetMapping("/nhanhieu")
	public String listAll(Model model) {
		List<NhanHieu> listBrands = service.listAll();
		model.addAttribute("listBrands", listBrands);

		return "nhanhieu/nhanhieu";
	}
}
