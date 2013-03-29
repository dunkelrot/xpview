package com.basf.xpview.model.io.xmplant;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;

import com.basf.xpview.model.Activator;
import com.basf.xpview.model.Catalog;
import com.basf.xpview.model.EquipmentContainer;
import com.basf.xpview.model.Plant;
import com.basf.xpview.model.PlantItemContainer;
import com.basf.xpview.model.PlantSection;
import com.basf.xpview.model.PropertyData;
import com.basf.xpview.model.PropertyList;
import com.basf.xpview.model.PropertyType;
import com.basf.xpview.model.Workspace;
import com.basf.xpview.model.graphics.RepresentationManager;
import com.basf.xpview.model.graphics.SoCircle;
import com.basf.xpview.model.graphics.SoGroup;
import com.basf.xpview.model.graphics.SoNode;
import com.basf.xpview.model.graphics.SoPolyLine;
import com.basf.xpview.model.graphics.SoShape;
import com.basf.xpview.model.io.Import;
import com.basf.xpview.model.io.ImportHints;

public class XMpLantImport extends Import {

	private Plant plant;
	private SoGroup plantNode;
	private RepresentationManager repManager;
	
	@Override
	public boolean canRead(File file) {
		return true;
	}

	protected void tweak(java.lang.String systemId) {
		if (systemId.equals("AVEVAPID")) {
			setEquipmentPositionEnabled(Activator.getDefault().getPreferenceStore().getBoolean(Activator.AVEVA_APPLY_EQUIPMENT_POSITION));
		}
		if (systemId.equals("Autodesk AutoCAD P&ID")) {
			setEquipmentPositionEnabled(Activator.getDefault().getPreferenceStore().getBoolean(Activator.AUTODESK_APPLY_EQUIPMENT_POSITION));
		}
	}
	
	@Override
	public Plant read(File file, RepresentationManager repManager)
			throws Exception {
		this.repManager = repManager;
		try {
			JAXBContext context = JAXBContext.newInstance(PlantModel.class);
			Unmarshaller um = context.createUnmarshaller();
			PlantModel plantModel = (PlantModel) um.unmarshal(file);
			
			plant = new Plant(plantModel.getPlantInformation().getProjectName());
			Workspace.getInstance().setPlant(plant);
			
			tweak(plantModel.getPlantInformation().getOriginatingSystem());
			
			plantNode = new SoGroup(null, repManager.getFreeId(), "Plant");
			repManager.addNode(plantNode, plant);

			handlePlantModel(plantModel);
		} catch (JAXBException ex) {
			throw new Exception(ex);
		}
		return plant;
	}

	protected void handlePlantModel(PlantModel plantModel) {
		for (Object object : plantModel
				.getPresentationOrShapeCatalogueOrDrawing()) {
			if (object instanceof Equipment) {
				handleEquipment((Equipment) object, plant.getEquipmentList(), plantNode, false);
			}
			if (object instanceof Drawing) {
				handleDrawing((Drawing) object, plant);
			}
			if (object instanceof ShapeCatalogue) {
				handleShapeCatalog((ShapeCatalogue) object, plant);
			}
		}
	}

	protected void handleShapeCatalog(ShapeCatalogue _shapeCatalog, Plant plant) {
		Catalog catalog = plant.getCatalogList().addCatalog(
				_shapeCatalog.getName(), _shapeCatalog.getUnits());
		
		for (Object object : _shapeCatalog
				.getEquipmentOrNozzleOrPipingComponent()) {
			if (object instanceof Equipment) {
				handleEquipment((Equipment) object, catalog, null, true);
			}
			if (object instanceof Component) {
				handleComponent((Component) object, catalog, null, true);
			}
			if (object instanceof Nozzle) {
				handleNozzle((Nozzle) object, catalog, null, true);
			}
		}
	}

	protected void handleCurves(Object curve, SoGroup group) {
		if (curve instanceof PolyLine) {
			handleCurve((Curve) curve, group);
		}
		if (curve instanceof Line) {
			handleCurve((Curve) curve, group);
		}
		if (curve instanceof Shape) {
			handleShape((Shape) curve, group);
		}
		if (curve instanceof Circle) {
			handleCircle((Circle) curve, group);
		}
	}

	protected void handleDrawing(Drawing _drawing, PlantSection plantSection) {

		com.basf.xpview.model.Drawing drawing = plantSection.getDrawings()
				.addDrawing(_drawing.getName());
		PropertyData propData = drawing.getPropertyData();
		handleAttributes(_drawing, propData.addPropertyList("Default"));
		handleGenericAttributes(_drawing, propData);

		com.basf.xpview.model.graphics.SoGroup drawingNode = new com.basf.xpview.model.graphics.SoGroup(
				null, getNextId(), _drawing.getName());

		plantNode.addNode(drawingNode);

		repManager.addNode(drawingNode, drawing);
		for (Object object : _drawing.getComponentOrCurveOrText()) {
			if (object instanceof JAXBElement) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) object;
				Object obj = jaxbElement.getValue();
				handleCurves(obj, drawingNode);
			}
			if (object instanceof Text) {
				// TODO
			}
			if (object instanceof DrawingBorder) {
				// TODO
			}
			if (object instanceof Label) {
				// TODO
			}
			if (object instanceof ScopeBubble) {
				// TODO
			}
			if (object instanceof Component) {
				// TODO
			}
		}
	}
	
	protected SoGroup createSoGroup(com.basf.xpview.model.PlantItem plantItem, SoGroup parentNode, boolean fromCatalog) {
		SoGroup plantItemNode = null;
		if (fromCatalog == false) {
			com.basf.xpview.model.PlantItem plantItemCatalog = plant.getCatalogList().findPlantItem(plantItem.getName());
			if (plantItemCatalog != null) {
				plantItemNode = (SoGroup) repManager.getNode(plantItemCatalog).clone(parentNode, repManager);
			}
			if (plantItemNode == null) {
				plantItemNode = new SoGroup(parentNode, getNextId(), plantItem.getName());
			}
			parentNode.addNode(plantItemNode);
		} else {
			plantItemNode = new SoGroup(null, getNextId(), plantItem.getName());
		}
		repManager.addNode(plantItemNode, plantItem);
		return plantItemNode;
	}
	
	protected void handleNozzle(Nozzle _nozzle, PlantItemContainer parent, SoGroup parentNode, boolean fromCatalog) {
		
		com.basf.xpview.model.Nozzle nozzle = new com.basf.xpview.model.Nozzle(
				_nozzle.getComponentName(), _nozzle.getTagName(), parent);
		parent.addPlantItem(nozzle);
		
		SoGroup group = createSoGroup(nozzle, parentNode, fromCatalog);
		if (equipmentPositionEnabled == false) {
			group.getPosition().setEnabled(false);
		}
		
		handlePlantItem(_nozzle, nozzle, group);
	}

	protected void handleComponent(Component _component, PlantItemContainer parent, SoGroup parentNode, boolean fromCatalog) {
		
		com.basf.xpview.model.Component component = new com.basf.xpview.model.Component(
				_component.getComponentName(), _component.getTagName(), parent);
		parent.addPlantItem(component);
		
		SoGroup group = createSoGroup(component, parentNode, fromCatalog);
		if (equipmentPositionEnabled == false) {
			group.getPosition().setEnabled(false);
		}
		
		handlePlantItem(_component, component, group);
	}
	
	protected void handleEquipment(Equipment _equipment, PlantItemContainer parent, SoGroup parentNode, boolean fromCatalog) {
		
		com.basf.xpview.model.Equipment equipment = new com.basf.xpview.model.Equipment(
				_equipment.getComponentName(), _equipment.getTagName(), parent);
		parent.addPlantItem(equipment);
		
		SoGroup equipmentNode = new SoGroup(plantNode, getNextId(), _equipment.getTagName());
		plantNode.addNode(equipmentNode);
		
		SoGroup group = createSoGroup(equipment, equipmentNode, fromCatalog);
		handlePlantItem(_equipment, equipment, group);

		if (equipmentPositionEnabled == false) {
			group.getPosition().setEnabled(false);
		}
		
		// get all nozzles and subequipments
		for (Object object : _equipment
				.getDisciplineOrMinimumDesignPressureOrMaximumDesignPressure()) {
			if (object instanceof Nozzle) {
				Nozzle _nozzle = (Nozzle) object;
				handleNozzle(_nozzle, equipment, equipmentNode, false);
			}
			if (object instanceof Equipment) {
				handleEquipment((Equipment) object, equipment, equipmentNode, false);
			}
		}

		plant.getItems().add(equipment);
	}

	protected void handlePlantItem(PlantItem _plantItem,
			com.basf.xpview.model.PlantItem plantItem, SoGroup group) {

		PropertyData propData = plantItem.getPropertyData();
		handleAttributes(_plantItem, propData.addPropertyList("Default"));
		handleGenericAttributes(_plantItem, propData);

		for (Object object : _plantItem.getPresentationOrExtentOrPersistentID()) {
			if (object instanceof Position) {
				handlePosition((Position) object, group);
			}
			if (object instanceof Scale) {
				handleScale((Scale) object, group);
			}
			
			if (object instanceof JAXBElement) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) object;
				Object obj = jaxbElement.getValue();
				handleCurves(obj, group);
			}
		}
	}

	protected void handleGenericAttributes(GenericAttributes genAttrs,
			PropertyData propertyData) {
		PropertyList propList = propertyData.addPropertyList(genAttrs.getSet());
		for (Object genAttrObj : genAttrs.getContent()) {
			if (genAttrObj instanceof GenericAttribute) {
				GenericAttribute genAttr = (GenericAttribute) genAttrObj;
				propList.addProperty(PropertyType.GENERIC, genAttr.getName(),
						genAttr.getValue(), genAttr.getFormat(),
						genAttr.getURI());
			}
		}
	}

	/**
	 * Reads all generic attribute sets and generic attributes from the given
	 * PlantItem. For each generic attribute set a new property list is created.
	 * 
	 * @param _plantItem
	 *            the XMpLant PlantItem object
	 * @param propertyData
	 *            the property data container to fill
	 */
	protected void handleGenericAttributes(PlantItem _plantItem,
			PropertyData propertyData) {
		for (Object object : _plantItem.getPresentationOrExtentOrPersistentID()) {
			if (object instanceof GenericAttributes) {
				handleGenericAttributes((GenericAttributes) object,
						propertyData);
			}
		}
	}

	protected void handleGenericAttributes(Drawing _drawing,
			PropertyData propertyData) {
		for (Object object : _drawing.getComponentOrCurveOrText()) {
			if (object instanceof GenericAttributes) {
				handleGenericAttributes((GenericAttributes) object,
						propertyData);
			}
		}
	}

	/**
	 * Calls handleAttributesInternal to read all attributes (XmlAttribute) from
	 * the given object. The retrieved attributes are classified as
	 * PropertyType.BASE.
	 * 
	 * @param object
	 *            the XMpLant object from which all attributes should be
	 *            retrieved.
	 * @param propertyList
	 *            the property list to fill
	 */
	protected void handleAttributes(Object object, PropertyList propertyList) {
		handleAttributesInternal(object, object.getClass(), propertyList);
	}

	/**
	 * Reads all attributes (XmlAttribute) from the given object and stores them
	 * in the given property list. This methods works through all classes in the
	 * class hierarchy! The retrieved attributes are classified as
	 * PropertyType.BASE.
	 * 
	 * @param object
	 *            the XMpLant object from which all attributes should be
	 *            retrieved.
	 * @param clazz
	 *            the Class object, if null the recursion stops.
	 * @param propertyList
	 *            the property list to fill
	 */
	protected void handleAttributesInternal(Object object, Class<?> clazz,
			PropertyList propertyList) {
		if (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				XmlAttribute attribute = (XmlAttribute) field
						.getAnnotation(XmlAttribute.class);
				if (attribute != null) {
					try {
						Object value = field.get(object);
						if (value != null) {
							java.lang.String valueStr = value.toString();
							propertyList.addProperty(PropertyType.BASE,
									attribute.name(), valueStr);
						}
					} catch (IllegalAccessException ex) {
						System.out.println(ex.getMessage());
					}
				}
			}
			handleAttributesInternal(object, clazz.getSuperclass(),
					propertyList);
		}
	}

	protected void handlePosition(Position _position, SoNode node) {

		node.getPosition().origin.x = _position.location.x;
		node.getPosition().origin.y = _position.location.y;
		if (_position.location.z != null) {
			node.getPosition().origin.z = _position.location.z;
		}

		node.getPosition().axis.x = _position.axis.x;
		node.getPosition().axis.y = _position.axis.y;
		node.getPosition().axis.z = _position.axis.z;

		node.getPosition().direction.x = _position.reference.x;
		node.getPosition().direction.y = _position.reference.y;
		node.getPosition().direction.z = _position.reference.z;
		
		node.getPosition().normalize();
		node.getPosition().calculateRotationAngle();
	}
	
	protected void handleScale(Scale _scale, SoNode node) {

		node.getScale().x = _scale.x;
		node.getScale().y = _scale.y;
	}

	protected void handleCurve(Curve curve, SoGroup group) {

		List<Coordinate> coords = null;
		if (curve instanceof PolyLine) {
			PolyLine _polyLine = (PolyLine) curve;
			coords = _polyLine.coordinate;
		} else if (curve instanceof Line) {
			Line _line = (Line) curve;
			coords = _line.coordinate;
		} else {
			System.out.println("Unsupported curve type: "
					+ curve.getClass().getName());
			return;
		}

		SoPolyLine polyLine = new SoPolyLine(group, getNextId(), "");
		ArrayList<Point3d> points = new ArrayList<Point3d>();
		for (Coordinate coord : coords) {
			Point3d point = new Point3d(coord.x, coord.y, 0);
			points.add(point);
		}
		polyLine.init(points.toArray(new Point3d[points.size()]));

		group.addNode(polyLine);
	}

	protected void handleShape(Shape _shape, SoGroup group) {

		List<Coordinate> coords = _shape.coordinate;

		SoShape shape = new SoShape(group, getNextId(), "");
		ArrayList<Point3d> points = new ArrayList<Point3d>();
		for (Coordinate coord : coords) {
			Point3d point = new Point3d(coord.x, coord.y, 0);
			points.add(point);
		}

		shape.init(points.toArray(new Point3d[points.size()]));
		shape.setFilled(false); // TODO

		group.addNode(shape);
	}

	protected void handleCircle(Circle _circle, SoGroup group) {

		SoCircle circle = new SoCircle(group, repManager.getFreeId(), "Circle");
		circle.init(_circle.radius, false); // TODO
		handlePosition(_circle.getPosition(), circle);
		group.addNode(circle);
	}

	private int getNextId() {
		return repManager.getFreeId();
	}
}
