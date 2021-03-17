package com.shopme.common.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "giohang")
public class GioHang {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maGioHang;
	
	@OneToMany
	private Set<SanPham> sanpham = new HashSet<>() ;
	
	@OneToOne
	private KhachHang khachhang;
}
