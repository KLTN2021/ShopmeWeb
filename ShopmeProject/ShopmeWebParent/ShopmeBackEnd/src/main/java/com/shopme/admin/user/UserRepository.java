package com.shopme.admin.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.common.entity.TaiKhoan;

public interface UserRepository extends CrudRepository<TaiKhoan, Integer>{
	@Query("SELECT u FROM TaiKhoan u WHERE u.email = :email")
	public TaiKhoan getUserByEmail(@Param("email") String email);
	
	public Long countById(Integer maTK);
	
}
