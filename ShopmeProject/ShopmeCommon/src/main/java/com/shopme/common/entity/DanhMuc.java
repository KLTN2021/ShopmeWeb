package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "danhmuc")
public class DanhMuc {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maDanhMuc;
	
	@Column(length = 128, nullable = false, unique = true)
	private String ten;
	
	@Column(length = 64, nullable = false, unique = true)
	private String bietHieu;
	
	@Column(length = 128, nullable = false)
	private String hinhAnh;
	
	private boolean trangThai;
	
	@OneToOne
	@JoinColumn(name = "cha_id")
	private DanhMuc cha;
	
	@OneToMany(mappedBy = "cha")
	private Set<DanhMuc> con = new HashSet<>();

	public Integer getMaDanhMuc() {
		return maDanhMuc;
	}

	public void setMaDanhMuc(Integer maDanhMuc) {
		this.maDanhMuc = maDanhMuc;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getBietHieu() {
		return bietHieu;
	}

	public void setBietHieu(String bietHieu) {
		this.bietHieu = bietHieu;
	}

	public String getHinhAnh() {
		return hinhAnh;
	}

	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public DanhMuc getCha() {
		return cha;
	}

	public void setCha(DanhMuc cha) {
		this.cha = cha;
	}

	public Set<DanhMuc> getCon() {
		return con;
	}

	public void setCon(Set<DanhMuc> con) {
		this.con = con;
	}
	
	
}
