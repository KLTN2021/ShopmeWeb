package com.shopme.danhmuc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopme.common.entity.DanhMuc;

@Service
public class DanhMucService {
	
	@Autowired private DanhMucRepository repo;

	public List<DanhMuc> listNoChildrenCategories() {
		List<DanhMuc> listNoChildrenCategories = new ArrayList<>();

		List<DanhMuc> listEnabledCategories = repo.findAllTrangThai();

		listEnabledCategories.forEach(category -> {
			Set<DanhMuc> children = category.getDanhMuccon();
			if (children == null || children.size() == 0) {
				listNoChildrenCategories.add(category);
			}
		});

		return listNoChildrenCategories;
	}
	
	public DanhMuc getCategory(String biDanh) {
		return repo.findByAliasEnabled(biDanh);
	}

	public List<DanhMuc> getCategoryParents(DanhMuc child) {
		List<DanhMuc> listParents = new ArrayList<>();

		DanhMuc parent = child.getDanhMucCha();

		while (parent != null) {
			listParents.add(0, parent);
			parent = parent.getDanhMucCha();
		}

		listParents.add(child);

		return listParents;
	}
}
