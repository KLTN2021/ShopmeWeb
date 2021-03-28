package com.shopme.admin.nguoidung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NguoiDungRestController {
	@Autowired
	private NguoiDungService service;
	
	@PostMapping("/nguoidung/check_email")
	public String checkDuplicateEmail(@Param("maTK") Integer maTK, @Param("email") String email) {
		return service.isEmailUnique(maTK ,email) ? "OK" : "Duplicated";
	}
}
