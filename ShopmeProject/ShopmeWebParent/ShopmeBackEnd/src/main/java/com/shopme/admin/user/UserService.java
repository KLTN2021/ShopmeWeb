package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

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
		boolean isUpdatingUser = (user.getMaTK() != null);
		
		if(isUpdatingUser) {
			TaiKhoan existingUser = userRepo.findById(user.getMaTK()).get();
			
			if (user.getMatKhau().isEmpty()) {
				user.setMatKhau(existingUser.getMatKhau());
			} else {
				encodePassword(user);
			}
		} else {
			encodePassword(user);
		}
		
		userRepo.save(user);	
	}
	
	private void encodePassword(TaiKhoan user) {
		String encodedPassword = passwordEncoder.encode(user.getMatKhau());
		user.setMatKhau(encodedPassword);
	}
	
	public boolean isEmailUnique(Integer maTK, String email) {
		TaiKhoan userByEmail = userRepo.getUserByEmail(email);
		
		if (userByEmail == null) return true;
		
		boolean isCreatingNew = (maTK == null);
		
		if (isCreatingNew) {
			if (userByEmail != null) return false;
		} else {
			if (userByEmail.getMaTK() != maTK) {
				return false;
			}
		}
		
		return true;
	}

	public TaiKhoan get(Integer maTK) throws UserNotFoundException {
		try {
			return userRepo.findById(maTK).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Không tìm thấy người dùng có mã tài khoản: " + maTK);
		}
	}
	
	public void delete(Integer maTK) throws UserNotFoundException {
		Long countByMaTK = userRepo.countByMaTK(maTK);
		if (countByMaTK == null || countByMaTK == 0) {
			throw new UserNotFoundException("Không tìm thấy người dùng có mã tài khoản: " + maTK);
		}
		
		userRepo.deleteById(maTK);
	}
}