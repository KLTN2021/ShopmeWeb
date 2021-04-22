package com.shopme.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "khachhang")
public class KhachHang {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maKhachHang;
	
	private String email;
	private String matKhau;
	
	@Column(name = "ho")
	private String ho;
	
	@Column(name = "ten")
	private String ten;
	
	@Column(name = "so_dien_thoai")
	private String soDienThoai;
	
	@Column(name = "dia_chi1")
	private String diaChi1;
	
	@Column(name = "dia_chi2")
	private String diaChi2;
	
	private String thanhPho;
	private String tinh;
	
	@Column(name = "ma_buu_dien")
	private String maBuuDien;
	
	@Column(name = "thoi_gian_tao", updatable = false)
	private Date thoiGianTao;
	
	private boolean trangThai;
	
	

	public KhachHang() {
	}
	
	public KhachHang(Integer maKhachHang) {
		this.maKhachHang = maKhachHang;
	}
	
	public KhachHang(String email, String matKhau, String hoTen, String soDienThoai, String diaChi1,
			String diaChi2, String thanhPho, String tinh, String maBuuDien, boolean trangThai) {
		this.email = email;
		this.matKhau = matKhau;
		this.ho = hoTen;
		this.soDienThoai = soDienThoai;
		this.diaChi1 = diaChi1;
		this.diaChi2 = diaChi2;
		this.thanhPho = thanhPho;
		this.tinh = tinh;
		this.maBuuDien = maBuuDien;
		this.trangThai = trangThai;
	}

	public Integer getMaKhachHang() {
		return maKhachHang;
	}

	public void setMaKhachHang(Integer maKhachHang) {
		this.maKhachHang = maKhachHang;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public String getHo() {
		return ho;
	}

	public void setHo(String ho) {
		this.ho = ho;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getDiaChi1() {
		return diaChi1;
	}

	public void setDiaChi1(String diaChi1) {
		this.diaChi1 = diaChi1;
	}

	public String getDiaChi2() {
		return diaChi2;
	}

	public void setDiaChi2(String diaChi2) {
		this.diaChi2 = diaChi2;
	}

	public String getThanhPho() {
		return thanhPho;
	}

	public void setThanhPho(String thanhPho) {
		this.thanhPho = thanhPho;
	}

	public String getTinh() {
		return tinh;
	}

	public void setTinh(String tinh) {
		this.tinh = tinh;
	}

	public String getMaBuuDien() {
		return maBuuDien;
	}

	public void setMaBuuDien(String maBuuDien) {
		this.maBuuDien = maBuuDien;
	}

	public Date getThoiGianTao() {
		return thoiGianTao;
	}

	public void setThoiGianTao(Date thoiGianTao) {
		this.thoiGianTao = thoiGianTao;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
	
	@Transient
	public String getHoTen() {
		if (ten == null) return ho;
		return ho + " " + ten;
	}
	
	
	
}
