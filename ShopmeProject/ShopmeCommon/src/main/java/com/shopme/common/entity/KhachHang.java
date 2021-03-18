package com.shopme.common.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "khachhang")
public class KhachHang {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maKhachHang;
	private String email;
	private String matKhau;
	private String ho;
	private String ten;

	@Embedded
	private DiaChi diachi;

	@OneToOne
	private TaiKhoan taikhoan;
}