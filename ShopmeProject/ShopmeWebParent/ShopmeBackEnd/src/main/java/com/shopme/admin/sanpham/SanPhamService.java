package com.shopme.admin.sanpham;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.SanPham;

@Service
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
}
