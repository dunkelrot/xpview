package com.basf.xpview.model.io.xmplant;

import java.io.File;
import java.lang.reflect.Field;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;

import com.basf.xpview.model.EquipmentList;
import com.basf.xpview.model.Plant;
import com.basf.xpview.model.PropertyData;
import com.basf.xpview.model.PropertyList;
import com.basf.xpview.model.PropertyType;
import com.basf.xpview.model.io.Import;

public class XMpLantImport extends Import {

	private Plant plant;
	
	@Override
	public boolean canRead(File file) {
		return true;
	}

	@Override
	public Plant read(File file) throws Exception {
		plant = null;
		try {
			JAXBContext context = JAXBContext.newInstance(PlantModel.class);
			Unmarshaller um = context.createUnmarshaller();
			PlantModel plantModel = (PlantModel) um.unmarshal(file);
			plant = new Plant(plantModel.getPlantInformation().getProjectName());
			handlePlantModel(plantModel);
		} catch (JAXBException ex) {
			throw new Exception(ex);
		}
		return plant;
	}
	
	protected void handlePlantModel(PlantModel plantModel) {
		for (Object object : plantModel.getPresentationOrShapeCatalogueOrDrawing()) {
			if (object instanceof Equipment) {
				handleEquipment((Equipment) object);
			}
		}
	}
	
	protected void handleEquipment(Equipment _equipment) {
		EquipmentList eqList = plant.getEquipmentList();
		com.basf.xpview.model.Equipment equipment = new com.basf.xpview.model.Equipment(_equipment.getTagName(), _equipment.getTagName(), eqList);
		eqList.add(equipment);
		handlePlantItem(_equipment, equipment);
		plant.getItems().add(equipment);
	}
	
	protected void handlePlantItem(PlantItem _plantItem, com.basf.xpview.model.PlantItem plantItem) {
		
		PropertyData propData = plantItem.getPropertyData();
		handleAttributes(_plantItem, propData.addPropertyList("Default"));
		handleGenericAttributes(_plantItem, propData);
	}
	
	/**
	 * Reads all generic attribute sets and generic attributes from the given PlantItem.
	 * For each generic attribute set a new property list is created.
	 * 
	 * @param _plantItem	the XMpLant PlantItem object
	 * @param propertyData	the property data container to fill
	 */
	protected void handleGenericAttributes(PlantItem _plantItem, PropertyData propertyData) {
		for (Object object : _plantItem.getPresentationOrExtentOrPersistentID()) {
			if (object instanceof GenericAttributes) {
				GenericAttributes genAttrs = (GenericAttributes) object;
				PropertyList propList = propertyData.addPropertyList(genAttrs.getSet());
				for (Object genAttrObj : genAttrs.getContent()) {
					if (genAttrObj instanceof GenericAttribute) {
						GenericAttribute genAttr = (GenericAttribute) genAttrObj;
						propList.addProperty(PropertyType.GENERIC, genAttr.getName(), genAttr.getValue(), genAttr.getFormat(), genAttr.getURI());
					}
				}
			}
		}
	}
	
	/**
	 * Calls handleAttributesInternal to read all attributes (XmlAttribute) from the given object.
	 * The retrieved attributes are classified as PropertyType.BASE.
	 *  
	 * @param object		the XMpLant object from which all attributes should be retrieved.
	 * @param propertyList	the property list to fill
	 */
	protected void handleAttributes(Object object, PropertyList propertyList) {
		handleAttributesInternal(object, object.getClass(), propertyList);
	}
	
	/**
	 * Reads all attributes (XmlAttribute) from the given object and stores them in the 
	 * given property list. This methods works through all classes in the class hierarchy!
	 * The retrieved attributes are classified as PropertyType.BASE.
	 * 
	 * @param object		the XMpLant object from which all attributes should be retrieved.
	 * @param clazz			the Class object, if null the recursion stops.
	 * @param propertyList	the property list to fill
	 */
	protected void handleAttributesInternal(Object object, Class clazz, PropertyList propertyList) {
		if (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				XmlAttribute attribute = (XmlAttribute) field.getAnnotation(XmlAttribute.class);
				if (attribute != null) {
					try {
						Object value = field.get(object);
						if (value != null) {
							java.lang.String valueStr = value.toString();
							propertyList.addProperty(PropertyType.BASE, attribute.name(), valueStr);
						}
					} catch (IllegalAccessException ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
			handleAttributesInternal(object, clazz.getSuperclass(), propertyList);
		}
	}
}
