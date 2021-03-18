package com.shopme.common.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "donhang")
public class DonHang {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maDonHang;

	@ManyToOne
	@JoinColumn(name = "maKhachHang")
	private KhachHang khachhang;

	private LocalDate thoiGianDatHang;
	private String phuongThucThanhToan;
}