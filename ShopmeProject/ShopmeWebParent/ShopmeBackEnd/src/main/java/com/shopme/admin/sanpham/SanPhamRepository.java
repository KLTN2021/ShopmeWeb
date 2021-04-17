package com.shopme.admin.sanpham;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.SanPham;

public interface SanPhamRepository extends PagingAndSortingRepository<SanPham, Integer> {
	
	public SanPham findByTen(String ten);
}
