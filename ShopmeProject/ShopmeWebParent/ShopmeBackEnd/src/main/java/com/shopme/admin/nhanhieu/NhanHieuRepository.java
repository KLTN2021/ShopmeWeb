package com.shopme.admin.nhanhieu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.NhanHieu;

public interface NhanHieuRepository extends PagingAndSortingRepository<NhanHieu, Integer> {
	
	public Long countByMaNhanHieu(Integer maNhanHieu);
	
	public NhanHieu findByTen(String ten);
	
	@Query("SELECT b FROM NhanHieu b WHERE b.ten LIKE %?1%")
	public Page<NhanHieu> findAll(String keyword, Pageable pageable);
}
