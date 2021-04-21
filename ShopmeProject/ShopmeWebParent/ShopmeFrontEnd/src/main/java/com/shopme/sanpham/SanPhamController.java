package com.shopme.sanpham;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopme.danhmuc.DanhMucService;
import com.shopme.common.entity.DanhMuc;
import com.shopme.common.entity.SanPham;

@Controller
public class SanPhamController {
	@Autowired private SanPhamService sanPhamService;
	@Autowired private DanhMucService danhMucService;

	@GetMapping("/c/{danhmuc_bidanh}")
	public String viewCategoryFirstPage(@PathVariable("danhmuc_bidanh") String biDanh,
			Model model) {
		return viewCategoryByPage(biDanh, 1, model);
	}

	@GetMapping("/c/{danhmuc_bidanh}/page/{pageNum}")
	public String viewCategoryByPage(@PathVariable("danhmuc_bidanh") String biDanh,
			@PathVariable("pageNum") int pageNum,
			Model model) {
		DanhMuc category = danhMucService.getCategory(biDanh);
		if (category == null) {
			return "error/404";
		}

		List<DanhMuc> listCategoryParents = danhMucService.getCategoryParents(category);

		Page<SanPham> pageProducts = sanPhamService.listByCategory(pageNum, category.getMaDanhMuc());
		List<SanPham> listProducts = pageProducts.getContent();

		long startCount = (pageNum - 1) * SanPhamService.PRODUCTS_PER_PAGE + 1;
		long endCount = startCount + SanPhamService.PRODUCTS_PER_PAGE - 1;
		if (endCount > pageProducts.getTotalElements()) {
			endCount = pageProducts.getTotalElements();
		}


		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", pageProducts.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", pageProducts.getTotalElements());
		model.addAttribute("pageTitle", category.getTen());
		model.addAttribute("listCategoryParents", listCategoryParents);
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("category", category);

		return "sanpham_by_danhmuc";
	}
}
