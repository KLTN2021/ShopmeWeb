package com.shopme.khachhang;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.KhachHang;

public interface KhachHangRepository extends CrudRepository<KhachHang, Integer>{
	@Query("SELECT c FROM KhachHang c WHERE c.email = ?1")
	public KhachHang findByEmail(String email);
}
