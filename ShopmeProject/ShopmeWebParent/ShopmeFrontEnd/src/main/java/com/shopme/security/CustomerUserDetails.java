package com.shopme.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shopme.common.entity.KhachHang;

public class CustomerUserDetails implements UserDetails{
	private KhachHang customer;

	public CustomerUserDetails(KhachHang customer) {
		this.customer = customer;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return customer.getMatKhau();
	}

	@Override
	public String getUsername() {
		return customer.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return customer.isTrangThai();
	}
	
	public String getFullName() {
		return customer.getHo()+ " " + customer.getTen();
	}

	public void setFirstName(String firstName) {
		this.customer.setHo(firstName);
	}
	
	public void setLastName(String lastName) {
		this.customer.setTen(lastName);
	}	
	
	public KhachHang getCustomer() {
		return this.customer;		
	}
}
