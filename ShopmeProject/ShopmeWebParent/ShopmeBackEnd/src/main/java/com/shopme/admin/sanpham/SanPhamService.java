package com.shopme.admin.sanpham;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.SanPham;

@Service
public class SanPhamService {
	@Autowired private SanPhamRepository repo;

	public List<SanPham> listAll() {
		return (List<SanPham>) repo.findAll();
	}
}
