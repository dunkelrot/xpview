package com.basf.xpview.model.report;

import java.util.LinkedHashSet;
import java.util.Set;

import com.basf.xpview.model.Plant;
import com.basf.xpview.model.PlantItem;
import com.basf.xpview.model.Property;
import com.basf.xpview.model.PropertyList;

public class ReportService {

	static final public String ClassNameALL = "All";
	
	static public ReportPropertyData createReportPropertyData(Plant plant, Class<?> plantItemType, String className) {
		ReportPropertyData propData = new ReportPropertyData();
		updateReportPropertyData(propData, plant, plantItemType, className);
		return propData;
	}
	
	static public void updateReportPropertyData(ReportPropertyData propData, Plant plant, Class<?> plantItemType, String className) {

		propData.clear();
		propData.setClassName(className);
		propData.setPlantItemType(plantItemType);
		
		for (PlantItem plantItem : plant.getAllPlantItems()) {
			if (plantItemType.isAssignableFrom(plantItem.getClass())) {
				if (plantItem.getClassName().equals(className) || className.equals(ClassNameALL)) {
					for (PropertyList propList : plantItem.getPropertyData().getProperyLists()) {
						ReportPropertyList repPropList = propData.add(propList.getName());
						for (Property property : propList.getProperties()) {
							repPropList.addProperty(property);
						}
					}
				}
			}
		}
	}
	
	static public Set<String> getAllComponentClasses(Plant plant, Class<?> plantItemType) {
		Set<String> classNames = new LinkedHashSet<String>();
		for (PlantItem plantItem : plant.getAllPlantItems()) {
			if (plantItemType.isAssignableFrom(plantItem.getClass())) {
				classNames.add(plantItem.getClassName());
			}
		}
		return classNames;
	}
	
}
