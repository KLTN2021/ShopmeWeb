package com.shopme.admin.sanpham;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.shopme.common.entity.HinhAnhSanPham;

@Controller
public class SanPhamController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SanPhamController.class);
	
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
			@RequestParam("extraImage") MultipartFile[] extraImageMultiparts,
			@RequestParam(name = "detailIDs", required = false) String[] detailIDs,
			@RequestParam(name = "detailNames", required = false) String[] detailNames,
			@RequestParam(name = "detailValues", required = false) String[] detailValues,
			@RequestParam(name = "imageIDs", required = false) String[] imageIDs,
			@RequestParam(name = "imageNames", required = false) String[] imageNames)
					throws IOException {
			
			setMainImageName(mainImageMultipart, product);
			setExistingExtraImageNames(imageIDs, imageNames, product);
			setNewExtraImageNames(extraImageMultiparts, product);
			setProductDetails(detailIDs, detailNames, detailValues, product);

			SanPham savedProduct = sanPhamService.save(product);

			saveUploadedImages(mainImageMultipart, extraImageMultiparts, savedProduct);
			
			deleteExtraImagesWeredRemovedOnForm(product);

			ra.addFlashAttribute("message", "The product has been saved successfully.");

			return "redirect:/sanpham";

	} 
	
	private void deleteExtraImagesWeredRemovedOnForm(SanPham product) {
		String extraImageDir = "../product-images/" + product.getMaSanPham() + "/extras";
		Path dirPath = Paths.get(extraImageDir);

		try {
			Files.list(dirPath).forEach(file -> {
				String filename = file.toFile().getName();

				if (!product.containsImageName(filename)) {
					try {
						Files.delete(file);
						LOGGER.info("Deleted extra image: " + filename);

					} catch (IOException e) {
						LOGGER.error("Could not delete extra image: " + filename);
					}
				}

			});
		} catch (IOException ex) {
			LOGGER.error("Could not list directory: " + dirPath);
		}
	}

	private void setExistingExtraImageNames(String[] imageIDs, String[] imageNames, 
			SanPham product) {
		if (imageIDs == null || imageIDs.length == 0) return;

		Set<HinhAnhSanPham> images = new HashSet<>();

		for (int count = 0; count < imageIDs.length; count++) {
			Integer maHinhAnh = Integer.parseInt(imageIDs[count]);
			String ten = imageNames[count];

			images.add(new HinhAnhSanPham(maHinhAnh, ten, product));
		}

		product.setHinhAnh(images);

	}
	
	private void setProductDetails(String[] detailIDs, String[] detailNames, 
			String[] detailValues, SanPham product) {
		if (detailNames == null || detailNames.length == 0) return;

		for (int count = 0; count < detailNames.length; count++) {
			String ten = detailNames[count];
			String value = detailValues[count];
			Integer maChiTietSP = Integer.parseInt(detailIDs[count]);

			if (maChiTietSP != 0) {
				product.themChiTiet(maChiTietSP, ten, value);
			} else if (!ten.isEmpty() && !value.isEmpty()) {
				product.themChiTietSP(ten, value);
			}
		}
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
	
	private void setNewExtraImageNames(MultipartFile[] extraImageMultiparts, SanPham product) {
		if (extraImageMultiparts.length > 0) {
			for (MultipartFile multipartFile : extraImageMultiparts) {
				if (!multipartFile.isEmpty()) {
					String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
					if (!product.containsImageName(fileName)) {
						product.themHinhAnh(fileName);
					}
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
	
	@GetMapping("/sanpham/edit/{maSanPham}")
	public String editProduct(@PathVariable("maSanPham") Integer maSanPham, Model model,
			RedirectAttributes ra) {
		try {
			SanPham product = sanPhamService.get(maSanPham);
			List<NhanHieu> listBrands = nhanHieuService.listAll();
			Integer numberOfExistingExtraImages = product.getHinhAnh().size();

			model.addAttribute("product", product);
			model.addAttribute("listBrands", listBrands);
			model.addAttribute("pageTitle", "Edit Product (ID: " + maSanPham + ")");
			model.addAttribute("numberOfExistingExtraImages", numberOfExistingExtraImages);


			return "sanpham/sanpham_form";

		} catch (SanPhamNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());

			return "redirect:/sanpham";
		}
	}
}
