package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link CatalogList} is used to manage one or more {@link Catalog}s.
 * 
 * @author Arndt Teinert
 *
 */
public class CatalogList extends Thing {

	protected Plant plant;
	protected ArrayList<Catalog> catalogs;
	
	/**
	 * Constructor, nothing special about this.
	 * @param plant the {@link Plant} this {@link CatalogList} belongs to.
	 */
	public CatalogList(Plant plant) {
		super("CatalogList");
		this.catalogs = new ArrayList<Catalog>();
		this.plant = plant;
	}
	
	/**
	 * Adds the given {@link Catalog}. Note that the caller must take care
	 * that the added {@link Catalog} has this instance set as it's parent.
	 * @param element the {@link Catalog} to add
	 */
	public void add(Catalog element) {
		catalogs.add(element);
	}
	
	/**
	 * Returns the list of {@link Catalog}s managed by this class.
	 * @return 
	 */
	public List<Catalog> getCatalogs() {
		return catalogs;
	}
	
	/**
	 * Returns the owning {@link Plant} object.
	 * @return the owning {@link Plant}
	 */
	public Plant getParent() {
		return plant;
	}

	/**
	 * Returns true if a {@link Catalog} with the given name already exists.
	 * @param name the name of the {@link Catalog} to find
	 * @return true or false
	 */
	public boolean hasCatalog(String name) {
		boolean result = false;
		for (Catalog catalog : catalogs) {
			if (catalog.getName().equals(name)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Creates a new {@link Catalog} with the given name. 
	 * The created {@link Catalog} is added to this instance.
	 * 
	 * @param name name of {@link Catalog}
	 * @param units not used!
	 * @return the newly created {@link Catalog}
	 */
	public Catalog addCatalog(String name, String units) {
		Catalog catalog = new Catalog(this, name);
		catalogs.add(catalog);
		return catalog;
	}
	
	/**
	 * Finds a {@link PlantItem} by name. All {@link Catalog}s are searched and
	 * the first found {@link PlantItem} is returned.
	 * 
	 * @param name name of the {@link PlantItem} to find
	 * @return an existing {@link PlantItem} or null
	 */
	public PlantItem findPlantItem(String name) {
		PlantItem result = null;
		for (Catalog catalog : catalogs) {
			result = catalog.findPlantItem(name);
			if (result == null) {
				break;
			}
		}
		return result;
	}
}
