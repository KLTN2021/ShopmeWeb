package com.shopme.admin.sanpham;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.SanPham;

public interface SanPhamRepository extends PagingAndSortingRepository<SanPham, Integer> {
	
	public SanPham findByTen(String ten);
	
	@Query("UPDATE SanPham p SET p.trangThai = ?2 WHERE p.maSanPham = ?1")
	@Modifying
	public void updateEnabledStatus(Integer maSanPham, boolean trangThai);	
}
