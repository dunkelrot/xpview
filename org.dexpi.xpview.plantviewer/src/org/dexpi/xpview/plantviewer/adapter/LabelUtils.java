package org.dexpi.xpview.plantviewer.adapter;

import org.dexpi.xpview.model.PlantItem;
import org.dexpi.xpview.model.PropertyList;

public class LabelUtils {

	static public String getPlantItemLabel(String initialLabel, PlantItem plantItem) {
		PropertyList defaultProps = plantItem.getPropertyData().getProperyList("Default");
		StringBuffer buffer = new StringBuffer();
		buffer.append(initialLabel);
		if (defaultProps != null) {
			
			if (defaultProps.getProperty("TagName") != null) {
				buffer.append(" ");
				buffer.append(defaultProps.getPropertyValue("TagName"));
			} else {
				buffer.append(" ");
				buffer.append(defaultProps.getPropertyValue("ComponentClass"));
				
				buffer.append(" ");
				buffer.append(defaultProps.getPropertyValue("ComponentName"));
			}
		}
		return buffer.toString().trim(); 
	}
}
