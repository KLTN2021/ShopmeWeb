package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.PhanQuyen;
import com.shopme.common.entity.TaiKhoan;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<TaiKhoan> listAll(){
		return (List<TaiKhoan>) userRepo.findAll();
	}
	
	public List<PhanQuyen> listRoles(){
		return (List<PhanQuyen>) roleRepo.findAll();
	}

	public void save(TaiKhoan user) {
		encodePassword(user);
		userRepo.save(user);	
	}
	
	private void encodePassword(TaiKhoan user) {
		String encodedPassword = passwordEncoder.encode(user.getMatKhau());
		user.setMatKhau(encodedPassword);
	}
}
