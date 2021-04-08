package com.shopme.admin.danhmuc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.DanhMuc;

@Service
public class DanhMucService {
	@Autowired
	private DanhMucRepository repo;
	
	public List<DanhMuc> listAll() {
		return (List<DanhMuc>) repo.findAll();
	}
	
	public DanhMuc save(DanhMuc category) {
		return repo.save(category);
	}

	public List<DanhMuc> listCategoriesUsedInForm() {
		List<DanhMuc> categoriesUsedInForm = new ArrayList<>();

		Iterable<DanhMuc> categoriesInDB = repo.findAll();

		for (DanhMuc category : categoriesInDB) {
			if (category.getDanhMucCha() == null) {
				categoriesUsedInForm.add(DanhMuc.copyMaVaTen(category));

				Set<DanhMuc> children = category.getDanhMuccon();

				for (DanhMuc subCategory : children) {
					String name = "--" + subCategory.getTen();
					categoriesUsedInForm.add(DanhMuc.copyMaVaTen(subCategory.getMaDanhMuc(), name));

					listChildren(categoriesUsedInForm, subCategory, 1);
				}
			}
		}		

		return categoriesUsedInForm;
	}

	private void listChildren(List<DanhMuc> categoriesUsedInForm, DanhMuc parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<DanhMuc> children = parent.getDanhMuccon();

		for (DanhMuc subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {				
				name += "--";
			}
			name += subCategory.getTen();

			categoriesUsedInForm.add(DanhMuc.copyMaVaTen(subCategory.getMaDanhMuc(), name));

			listChildren(categoriesUsedInForm, subCategory, newSubLevel);
		}		
	}

	
	
	
	
}
