package com.basf.xpview.plantviewer.adapter;

import com.basf.xpview.model.PlantItem;
import com.basf.xpview.model.PropertyList;

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
