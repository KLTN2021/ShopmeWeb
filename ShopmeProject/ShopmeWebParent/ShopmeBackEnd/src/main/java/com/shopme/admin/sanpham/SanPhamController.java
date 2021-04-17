package com.shopme.admin.sanpham;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String saveProduct(SanPham product, RedirectAttributes ra,
			@RequestParam("fileImage") MultipartFile mainImageMultipart,
			@RequestParam("extraImage") MultipartFile[] extraImageMultiparts) 
					throws IOException {
			
			setMainImageName(mainImageMultipart, product);
			setExtraImageNames(extraImageMultiparts, product);

			SanPham savedProduct = sanPhamService.save(product);

			saveUploadedImages(mainImageMultipart, extraImageMultiparts, savedProduct);

			ra.addFlashAttribute("message", "The product has been saved successfully.");

			return "redirect:/sanpham";

	} 
	
	private void saveUploadedImages(MultipartFile mainImageMultipart, 
			MultipartFile[] extraImageMultiparts, SanPham savedProduct) throws IOException {
		if (!mainImageMultipart.isEmpty()) {
			String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
			String uploadDir = "../product-images/" + savedProduct.getMaSanPham();

			FileUploadUtil.cleanDir(uploadDir);		
			FileUploadUtil.saveFile(uploadDir, fileName, mainImageMultipart);
		}
		
		if (extraImageMultiparts.length > 0) {
			String uploadDir = "../product-images/" + savedProduct.getMaSanPham() + "/extras";

			for (MultipartFile multipartFile : extraImageMultiparts) {
				if (multipartFile.isEmpty()) continue;

				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			}
		}
	}
	
	private void setExtraImageNames(MultipartFile[] extraImageMultiparts, SanPham product) {
		if (extraImageMultiparts.length > 0) {
			for (MultipartFile multipartFile : extraImageMultiparts) {
				if (!multipartFile.isEmpty()) {
					String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
					product.themHinhAnh(fileName);
				}
			}
		}
	}

	private void setMainImageName(MultipartFile mainImageMultipart, SanPham product) {
		if (!mainImageMultipart.isEmpty()) {
			String fileName = StringUtils.cleanPath(mainImageMultipart.getOriginalFilename());
			product.setHinhAnhChinh(fileName);
		}
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
			String productExtraImagesDir = "../product-images/" + maSanPham + "/extras";
			String productImagesDir = "../product-images/" + maSanPham;

			FileUploadUtil.removeDir(productExtraImagesDir);
			FileUploadUtil.removeDir(productImagesDir);

			redirectAttributes.addFlashAttribute("message", 
					"Sản phẩm có ID " + maSanPham + " đã được xóa thành công");
		} catch (SanPhamNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}

		return "redirect:/sanpham";
	}
}
