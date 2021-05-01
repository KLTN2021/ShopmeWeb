package com.shopme.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopme.common.entity.KhachHang;
import com.shopme.khachhang.KhachHangRepository;

public class CustomerUserDetailsService implements UserDetailsService {
	@Autowired
	private KhachHangRepository repo;

	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		KhachHang customer = repo.findByEmail(email);
		if (customer == null) {
			throw new UsernameNotFoundException("Customer not found");
		}
		
		return new CustomerUserDetails(customer);
	}

}
