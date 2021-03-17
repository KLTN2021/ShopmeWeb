package com.shopme.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chitietdonhang")
public class ChiTietDonHang {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maCTDH;
	
	@ManyToOne
	@JoinColumn(name = "maDonHang")
	private DonHang donhang;
	
	@ManyToOne
	@JoinColumn(name = "maSanPham")
	private SanPham sanpham;
	
	private float tongTien;
}
