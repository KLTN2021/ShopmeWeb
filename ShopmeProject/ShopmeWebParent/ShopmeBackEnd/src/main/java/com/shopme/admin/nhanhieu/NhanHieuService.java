package com.shopme.admin.nhanhieu;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.NhanHieu;

@Service
public class NhanHieuService {
	@Autowired
	private NhanHieuRepository repo;

	public List<NhanHieu> listAll() {
		return (List<NhanHieu>) repo.findAll();
	}
}
