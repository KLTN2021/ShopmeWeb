package com.shopme.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phanquyen")
public class PhanQuyen {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maPhanQuyen;
	
	@Column(length = 40, nullable = false, unique = true)
	private String ten;
	
	@Column(length = 150, nullable = false)
	private String moTa;
	
	
	
	public PhanQuyen() {
	}

	public PhanQuyen(String ten) {
		this.ten = ten;
	}

	public PhanQuyen(String ten, String moTa) {
		this.ten = ten;
		this.moTa = moTa;
	}

	public Integer getMaPhanQuyen() {
		return maPhanQuyen;
	}

	public void setMaPhanQuyen(Integer maPhanQuyen) {
		this.maPhanQuyen = maPhanQuyen;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	
	
	

}
