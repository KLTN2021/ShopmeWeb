package com.shopme.admin.danhmuc;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.DanhMuc;

public interface DanhMucRepository extends PagingAndSortingRepository<DanhMuc, Integer> {
	
	@Query("SELECT c FROM DanhMuc c WHERE c.danhMucCha.maDanhMuc is NULL")
	public List<DanhMuc> findRootDanhMuc(Sort sort);
	
	public DanhMuc findByTen(String ten);
	
	public DanhMuc findByBiDanh(String biDanh);
}
