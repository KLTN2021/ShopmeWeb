package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "taikhoan")
public class TaiKhoan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maTK;
	
	@Column(length = 128, nullable = false, unique = true)
	private String email;
	
	@Column(length = 64, nullable = false)
	private String matKhau;
	
	@Column(name = "ho", length = 45, nullable = false)
	private String Ho;
	
	@Column(name = "ten", length = 45, nullable = false)
	private String Ten;
	
	@Column(length = 64)
	private String hinhAnh;
	
	private boolean trangThai;
	
	@ManyToMany
	@JoinTable(
			name = "vaitro_taikhoan",
			joinColumns = @JoinColumn(name = "ma_tk"),
			inverseJoinColumns = @JoinColumn(name = "ma_pk")
			)
	private Set<PhanQuyen> phanquyen = new HashSet<>();
	
	

	public TaiKhoan() {
	}
	
	

	public TaiKhoan(String email, String matKhau, String ho, String ten) {
		this.email = email;
		this.matKhau = matKhau;
		Ho = ho;
		Ten = ten;
	}



	public Integer getMaTK() {
		return maTK;
	}

	public void setMaTK(Integer maTK) {
		this.maTK = maTK;
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
		return Ho;
	}

	public void setHo(String ho) {
		Ho = ho;
	}

	public String getTen() {
		return Ten;
	}

	public void setTen(String ten) {
		Ten = ten;
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

	public Set<PhanQuyen> getPhanquyen() {
		return phanquyen;
	}

	public void setPhanquyen(Set<PhanQuyen> phanquyen) {
		this.phanquyen = phanquyen;
	}
	
	public void addPhanQuyen(PhanQuyen pk) {
		this.phanquyen.add(pk);
	}



	@Override
	public String toString() {
		return "TaiKhoan [maTK=" + maTK + ", email=" + email + ", Ho=" + Ho + ", Ten=" + Ten + ", phanquyen="
				+ phanquyen + "]";
	}
	
	
	
	
}
