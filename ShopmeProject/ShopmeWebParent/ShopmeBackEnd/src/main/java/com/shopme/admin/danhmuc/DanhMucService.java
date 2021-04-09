package com.shopme.admin.danhmuc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.DanhMuc;

@Service
public class DanhMucService {
	@Autowired
	private DanhMucRepository repo;
	
	public List<DanhMuc> listAll() {
		List<DanhMuc> rootCategories = repo.timDanhMucGoc(); 
		return listHierarchicalCategories(rootCategories);
	}
	
	private List<DanhMuc> listHierarchicalCategories(List<DanhMuc> rootCategories) {
		List<DanhMuc> hierarchicalCategories = new ArrayList<>();
		
		for (DanhMuc rootCategory : rootCategories) {
			hierarchicalCategories.add(DanhMuc.copyTatCa(rootCategory));
			
			Set<DanhMuc> children = rootCategory.getDanhMuccon();
			
			for (DanhMuc subCategory : children) {
				String name = "--" + subCategory.getTen();
				hierarchicalCategories.add(DanhMuc.copyTatCa(subCategory, name));
				
				listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1);
			}
		}
		
		return hierarchicalCategories;
	}
	
	private void listSubHierarchicalCategories(List<DanhMuc> hierarchicalCategories, 
			DanhMuc parent, int subLevel) {
		Set<DanhMuc> children = parent.getDanhMuccon();
		int newSubLevel = subLevel + 1;
		
		for (DanhMuc subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {				
				name += "--";
			}
			name += subCategory.getTen();
			
			hierarchicalCategories.add(DanhMuc.copyTatCa(subCategory, name));
			
			listSubHierarchicalCategories(hierarchicalCategories, subCategory, newSubLevel);
		}
		
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

					listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, 1);
				}
			}
		}		

		return categoriesUsedInForm;
	}

	private void listSubCategoriesUsedInForm(List<DanhMuc> categoriesUsedInForm, 
			DanhMuc parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<DanhMuc> children = parent.getDanhMuccon();

		for (DanhMuc subCategory : children) {
			String name = "";
			for (int i = 0; i < newSubLevel; i++) {				
				name += "--";
			}
			name += subCategory.getTen();

			categoriesUsedInForm.add(DanhMuc.copyMaVaTen(subCategory.getMaDanhMuc(), name));

			listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
		}		
	}

	public DanhMuc get(Integer maDanhMuc) throws DanhMucNotFoundException {
		try {
			return repo.findById(maDanhMuc).get();
		} catch (NoSuchElementException ex) {
			throw new DanhMucNotFoundException("Could not find any category with ID " + maDanhMuc);
		}
	}
	
	
	
}
