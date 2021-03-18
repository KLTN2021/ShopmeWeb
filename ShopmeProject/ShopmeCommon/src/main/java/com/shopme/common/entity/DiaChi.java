package com.shopme.common.entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
public class DiaChi {
	private String soNha;
	private String tenDuong;
	private String quan;
	private String thanhPho;
	private String quocGia;

}