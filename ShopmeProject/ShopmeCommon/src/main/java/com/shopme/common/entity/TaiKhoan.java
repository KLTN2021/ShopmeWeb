package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
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
	private String ho;
	
	@Column(name = "ten", length = 45, nullable = false)
	private String ten;
	
	@Column(length = 64)
	private String hinhAnh;
	
	private boolean trangThai;

	@ManyToMany
	@JoinTable(
			name = "vaitro_taikhoan",
			joinColumns = @JoinColumn(name = "ma_tk"),
			inverseJoinColumns = @JoinColumn(name = "ma_pq")
			)
	private Set<PhanQuyen> phanquyen = new HashSet<>();

	public TaiKhoan() {
	}
	
	

	public TaiKhoan(String email, String matKhau, String ho, String ten) {
		this.email = email;
		this.matKhau = matKhau;
		this.ho = ho;
		this.ten = ten;
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
	
	public void addPhanQuyen(PhanQuyen pq) {
		this.phanquyen.add(pq);
	}



	@Override
	public String toString() {
		return "TaiKhoan [maTK=" + maTK + ", email=" + email + ", Ho=" + ho + ", Ten=" + ten + ", phanquyen="
				+ phanquyen + "]";
	}
	
	
	
	
}
