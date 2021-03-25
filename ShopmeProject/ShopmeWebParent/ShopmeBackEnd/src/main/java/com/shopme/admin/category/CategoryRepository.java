package com.shopme.admin.category;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.DanhMuc;

public interface CategoryRepository extends PagingAndSortingRepository<DanhMuc, Integer> {

}
