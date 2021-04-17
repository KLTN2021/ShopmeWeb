package com.shopme.common.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sanpham_hinhanh")
public class HinhAnhSanPham {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maHinhAnh;
	
	@Column(nullable = false)
	private String ten;
	
	@ManyToOne
	@JoinColumn(name = "sanpham_id")
	private SanPham sanpham;

	public HinhAnhSanPham() {
	}

	public HinhAnhSanPham(String ten, SanPham sanpham) {
		this.ten = ten;
		this.sanpham = sanpham;
	}

	public Integer getMaHinhAnh() {
		return maHinhAnh;
	}

	public void setMaHinhAnh(Integer maHinhAnh) {
		this.maHinhAnh = maHinhAnh;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public SanPham getSanpham() {
		return sanpham;
	}

	public void setSanpham(SanPham sanpham) {
		this.sanpham = sanpham;
	}
	
}
