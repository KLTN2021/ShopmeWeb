package com.shopme.admin.sanpham;
import java.util.List;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.SanPham;

@Service
@Transactional
public class SanPhamService {
	@Autowired private SanPhamRepository repo;

	public List<SanPham> listAll() {
		return (List<SanPham>) repo.findAll();
	}
	
	public SanPham save(SanPham product) {
		if (product.getMaSanPham() == null) {
			product.setThoiGianTao(new Date());
		}

		if (product.getBiDanh() == null || product.getBiDanh().isEmpty()) {
			String defaultAlias = product.getTen().replaceAll(" ", "-");
			product.setBiDanh(defaultAlias);
		} else {
			product.setBiDanh(product.getBiDanh().replaceAll(" ", "-"));
		}

		product.setThoiGianCapNhat(new Date());

		return repo.save(product);
	}
	
	public String checkUnique(Integer maSanPham, String ten) {
		boolean isCreatingNew = (maSanPham == null || maSanPham == 0);
		SanPham productByName = repo.findByTen(ten);

		if (isCreatingNew) {
			if (productByName != null) return "Duplicate";
		} else {
			if (productByName != null && productByName.getMaSanPham() != maSanPham) {
				return "Duplicate";
			}
		}

		return "OK";
	}
	
	public void updateProductEnabledStatus(Integer maSanPham, boolean trangThai) {
		repo.updateEnabledStatus(maSanPham, trangThai);
	}
}
