package com.shopme.admin.nhanhieu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NhanHieuRestController {
	@Autowired
	private NhanHieuService service;

	@PostMapping("/nhanhieu/check_unique")
	public String checkUnique(@Param("maNhanHieu") Integer maNhanHieu, @Param("ten") String ten) {
		return service.checkUnique(maNhanHieu, ten);
	}
}
