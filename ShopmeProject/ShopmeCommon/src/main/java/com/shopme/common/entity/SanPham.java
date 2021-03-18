package com.shopme.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sanpham")
public class SanPham {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maSanPham;

	@ManyToOne
	@JoinColumn(name = "danhmuc_id")
	private DanhMuc danhMuc;

	private String tenSanPham;
	private String moTa;
	private String hinhAnh;
	private double gia;


}