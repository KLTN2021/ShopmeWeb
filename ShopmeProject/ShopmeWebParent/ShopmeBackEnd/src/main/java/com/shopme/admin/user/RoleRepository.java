package com.shopme.admin.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.PhanQuyen;

@Repository
public interface RoleRepository extends CrudRepository<PhanQuyen, Integer>{
               
}
