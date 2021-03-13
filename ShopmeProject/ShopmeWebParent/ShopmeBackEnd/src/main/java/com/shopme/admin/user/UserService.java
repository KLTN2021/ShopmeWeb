package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.PhanQuyen;
import com.shopme.common.entity.TaiKhoan;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	public List<TaiKhoan> listAll(){
		return (List<TaiKhoan>) userRepo.findAll();
	}
	
	public List<PhanQuyen> listRoles(){
		return (List<PhanQuyen>) roleRepo.findAll();
	}

	public void save(TaiKhoan user) {
		userRepo.save(user);	
	}
}
