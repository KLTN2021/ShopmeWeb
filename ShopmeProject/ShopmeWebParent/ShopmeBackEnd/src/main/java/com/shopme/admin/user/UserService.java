package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.TaiKhoan;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public List<TaiKhoan> listAll(){
		return (List<TaiKhoan>) repo.findAll();
	}
}
