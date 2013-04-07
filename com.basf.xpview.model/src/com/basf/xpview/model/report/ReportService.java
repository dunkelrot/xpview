package com.basf.xpview.model.report;

import java.util.LinkedHashSet;
import java.util.Set;

import com.basf.xpview.model.Plant;
import com.basf.xpview.model.PlantItem;
import com.basf.xpview.model.Property;
import com.basf.xpview.model.PropertyList;

public class ReportService {

	static final public String ClassNameALL = "All";

	static public ReportPropertyData updateReportPropertyData(ReportPropertyData propData, Plant plant) {

		propData.clear();
		updateReportPropertyData(propData, plant, propData.getPlantItemType(), propData.getClassName());
		propData.setCheckedAll(true);
		return propData;
	}

	static public ReportPropertyData createReportPropertyData(Plant plant, Class<?> plantItemType, String className) {
		ReportPropertyData propData = new ReportPropertyData();
		updateReportPropertyData(propData, plant, plantItemType, className);
		return propData;
	}

	static public void updateReportPropertyData(ReportPropertyData propData, Plant plant, Class<?> plantItemType,
			String className) {

		propData.clear();
		propData.setClassName(className);
		propData.setPlantItemType(plantItemType);

		for (PlantItem plantItem : plant.getAllPlantItems()) {
			if (plantItemType.isAssignableFrom(plantItem.getClass())) {
				String itemClassName = plantItem.getClassName();
				boolean sameClass = false;
				if (itemClassName != null) {
					sameClass = itemClassName.equals(className);
				}
				if (sameClass || className.equals(ClassNameALL)) {
					for (PropertyList propList : plantItem.getPropertyData().getProperyLists()) {
						ReportPropertyList repPropList = propData.add(propList.getName());
						for (Property property : propList.getProperties()) {
							repPropList.addProperty(property);
						}
					}
				}
			}
		}

		propData.setCheckedAll(true);
	}

	static public Set<String> getAllComponentClasses(Plant plant, Class<?> plantItemType) {
		Set<String> classNames = new LinkedHashSet<String>();
		for (PlantItem plantItem : plant.getAllPlantItems()) {
			if (plantItemType.isAssignableFrom(plantItem.getClass())) {
				String className = plantItem.getClassName();
				if (className != null) {
					classNames.add(className);
				}
			}
		}
		return classNames;
	}

}
