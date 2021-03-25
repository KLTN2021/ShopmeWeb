package com.shopme.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.DanhMuc;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository repo;
	
	public List<DanhMuc> listAll() {
		return (List<DanhMuc>) repo.findAll();
	}
}
