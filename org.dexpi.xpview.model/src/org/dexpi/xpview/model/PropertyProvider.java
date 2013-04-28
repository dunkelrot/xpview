package org.dexpi.xpview.model;

/**
 * All objects which have properties (or lists of properties) should implement this interface.
 * 
 * @author Arndt Teinert
 *
 */
public interface PropertyProvider {

	/**
	 * Returns the {@link PropertyData} objects which actually manages the {@link Property}s
	 * @return the returned value is never null
	 */
	PropertyData getPropertyData();
	
}
