package com.basf.xpview.model;

/**
 * An object like a {@link Thing} or {@link PlantItem} which has a TAGID (a unique identifier)
 * should implement this interface.
 * 
 * @author Arndt Teinert
 *
 */
public interface TaggedItem {

	/**
	 * Returns the TAGID.
	 * @return the TAGID
	 */
	public String getTagId();
}

