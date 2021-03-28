package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.admin.nguoidung.NguoiDungRepository;
import com.shopme.common.entity.PhanQuyen;
import com.shopme.common.entity.TaiKhoan;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class NguoiDungRepositotyTest {
	@Autowired
	private NguoiDungRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		PhanQuyen roleAdmin = entityManager.find(PhanQuyen.class, 1);
		TaiKhoan userTam = new TaiKhoan("nguyentam@gmail.com", "tam2020", "Nguyễn Văn", "Tâm");
		userTam.themPhanQuyen(roleAdmin);
		
		TaiKhoan savedUser = repo.save(userTam);
		assertThat(savedUser.getMaTK()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRole() {
		TaiKhoan userRavi = new TaiKhoan("ravi@gmail.com", "ravi2020", "Ravi", "Khunmar");
		
		PhanQuyen roleEditor = new PhanQuyen(3);
		PhanQuyen roleAssistant = new PhanQuyen(5);
		
		userRavi.themPhanQuyen(roleEditor);
		userRavi.themPhanQuyen(roleAssistant);
		
		TaiKhoan savedUser = repo.save(userRavi);
		
		assertThat(savedUser.getMaTK()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<TaiKhoan> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		TaiKhoan userTam = repo.findById(1).get();
		System.out.println(userTam);
		assertThat(userTam).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		TaiKhoan userTam = repo.findById(1).get();
		userTam.setTrangThai(true);
		userTam.setEmail("nguyenvantam1999@gmail.com");
		repo.save(userTam);
	}
	
	@Test
	public void testUpdateUserRoles() {
		TaiKhoan userRavi = repo.findById(2).get();
		PhanQuyen roleEditor = new PhanQuyen(3);
		PhanQuyen roleSalesperson = new PhanQuyen(2);
		
		userRavi.getPhanquyen().remove(roleEditor);
		userRavi.themPhanQuyen(roleSalesperson);
		
		repo.save(userRavi);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 2;	
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "ravi@gmail.com";
		TaiKhoan user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountByMaTK() {
		Integer maTK = 3;
		Long countByMaTK = repo.countByMaTK(maTK);
		
		assertThat(countByMaTK).isNotNull().isGreaterThan(0);
	}
	
	@Test
	public void kiemThuTatTrangThaiNguoiDung() {
		Integer maTK = 12;
		repo.capNhatTrangThai(maTK, false);
	}
	
	@Test
	public void kiemThuBatTrangThaiNguoiDung() {
		Integer maTK = 12;
		repo.capNhatTrangThai(maTK, true);
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 1;
		int pageSize = 4;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<TaiKhoan> page = repo.findAll(pageable);
		
		List<TaiKhoan> listUsers = page.getContent();
		
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
}