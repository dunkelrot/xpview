package com.basf.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class CatalogList extends Thing {

	protected Plant plant;
	protected ArrayList<Catalog> catalogs;
	
	public CatalogList(Plant plant) {
		super("CatalogList");
		this.catalogs = new ArrayList<Catalog>();
		this.plant = plant;
	}
	
	public void add(Catalog element) {
		catalogs.add(element);
	}
	
	public List<Catalog> getCatalogs() {
		return catalogs;
	}
	
	public Plant getParent() {
		return plant;
	}

	public Catalog addCatalog(String name, String units) {
		Catalog catalog = new Catalog(this, name);
		catalogs.add(catalog);
		return catalog;
	}
	
	public Equipment findEquipment(String name) {
		Equipment result = null;
		for (Catalog catalog : catalogs) {
			result = catalog.findEquipment(name);
			if (result == null) {
				break;
			}
		}
		return result;
	}
}
