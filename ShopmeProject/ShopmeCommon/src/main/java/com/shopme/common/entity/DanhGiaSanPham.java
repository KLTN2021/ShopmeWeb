package com.shopme.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "danhgiasanpham")
public class DanhGiaSanPham {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maDanhGia;

	private String tieuDe;
	private String binhLuan;
	private Integer danhGia;	

	@ManyToOne
	@JoinColumn(name = "maKhachHang")
	private KhachHang khachhang;

	@ManyToOne
	@JoinColumn(name = "maSanPham")
	private SanPham sanpham;
}