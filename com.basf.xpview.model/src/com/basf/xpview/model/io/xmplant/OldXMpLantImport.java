package com.basf.xpview.model.io.xmplant;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.vecmath.Point3d;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;

import com.basf.xpview.model.Activator;
import com.basf.xpview.model.Catalog;
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
import com.basf.xpview.model.issues.IssueList;
import com.basf.xpview.model.issues.IssueTracker;
import com.basf.xpview.model.issues.IssueType;

public class OldXMpLantImport extends Import {

	private Plant plant;
	private SoGroup plantNode;
	private RepresentationManager repManager;
	private double scaleFactor = 1000.0;
	private IssueList issueList;
	
	private Deque<Object> issueContext; 
	
	protected java.lang.String callMethod(java.lang.String methodName, java.lang.String prefix, Object obj) {
		java.lang.String result = null;
		Class<?>[] parameterTypes = new Class[0]; 
		try {
			Method getID = obj.getClass().getMethod("getID", parameterTypes);
			java.lang.String value = "ID:" + (java.lang.String) getID.invoke(obj, (Object[]) null);
			if (value != null) {
				result = prefix + value;
			}
		} catch (Exception ex) {
			// ignored
		}
		return result;
	}
	
	protected java.lang.String getObjectName(Object obj) {
		java.lang.String name = null;
		name = callMethod("getID", "ID:", obj);
		if (name == null) {
			name = callMethod("getComponentName", "CNAME:", obj);
		}
		if (name == null) {
			name = callMethod("getName", "NAME:", obj);
		}
		
		return name;
	}
	
	protected void reportIssue(java.lang.String message, IssueType type) {
		StringBuffer buffer = new StringBuffer();
		Iterator<Object> itor = issueContext.descendingIterator();
		while(itor.hasNext()) {
			Object obj = itor.next();
			buffer.append("/");
			buffer.append(getObjectName(obj));
		}
	}
	
	@Override
	public boolean canRead(File file) {
		return true;
	}

	protected void tweak(java.lang.String systemId) {
		if (systemId.equals("AVEVAPID")) {
			setEquipmentPositionEnabled(Activator.getDefault().getPreferenceStore().getBoolean(Activator.AVEVA_APPLY_EQUIPMENT_POSITION));
			setUseCatalog(Activator.getDefault().getPreferenceStore().getBoolean(Activator.AUTODESK_USE_CATALOG));
		}
		if (systemId.equals("Autodesk AutoCAD P&ID")) {
			setEquipmentPositionEnabled(Activator.getDefault().getPreferenceStore().getBoolean(Activator.AUTODESK_APPLY_EQUIPMENT_POSITION));
			setUseCatalog(Activator.getDefault().getPreferenceStore().getBoolean(Activator.AUTODESK_USE_CATALOG));
		}
		if (systemId.equals("Comos")) {
			setEquipmentPositionEnabled(Activator.getDefault().getPreferenceStore().getBoolean(Activator.SIEMENS_APPLY_EQUIPMENT_POSITION));
			setUseCatalog(Activator.getDefault().getPreferenceStore().getBoolean(Activator.SIEMENS_USE_CATALOG));
		}
		if (systemId.equals("SPPID")) {
			setEquipmentPositionEnabled(false);
			setUseCatalog(false);
		}
		
	}
	
	protected java.lang.String getStringValue(Object source, java.lang.String description, java.lang.String value) {
		if (value == null) {
			reportIssue("Value for " + description + " is not defined!", IssueType.WARNING);
			return "<UNDEFINED>";
		} else {
			return value;
		}
	}
	
	@Override
	public Plant read(File file, RepresentationManager repManager)
			throws Exception {
		this.repManager = repManager;
		try {
			issueContext = new ArrayDeque<Object>();
			
			JAXBContext context = JAXBContext.newInstance(PlantModel.class);
			Unmarshaller um = context.createUnmarshaller();
			PlantModel plantModel = (PlantModel) um.unmarshal(file);

			plant = new Plant("");
			issueList = IssueTracker.getInstance().addIssueList(plant);
			issueContext.addFirst(plantModel);
			
			java.lang.String projectName = getStringValue(plant, "ProjectName", plantModel.getPlantInformation().getProjectName());
			plant.setName(projectName);
			
			Workspace.getInstance().setPlant(plant);
			tweak(plantModel.getPlantInformation().getOriginatingSystem());
			
			plantNode = new SoGroup(null, repManager.getFreeId(), "Plant");
			repManager.addNode(plantNode, plant);

			handlePlantModel(plantModel);
			
			issueContext.removeFirst();
		} catch (JAXBException ex) {
			throw new Exception(ex);
		}
		return plant;
	}

	protected void handlePlantModel(PlantModel plantModel) {
		for (Object object : plantModel
				.getPresentationOrShapeCatalogueOrDrawing()) {
			if (object instanceof Equipment) {
				handleEquipment((Equipment) object, plant.getEquipmentList(), plantNode, useCatalog ? false : true, false);
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
				handleEquipment((Equipment) object, catalog, null, true, true);
			}
			if (object instanceof Component) {
				handleComponent((Component) object, catalog, null, true, true);
			}
			if (object instanceof Nozzle) {
				handleNozzle((Nozzle) object, catalog, null, true, true);
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

		issueContext.addFirst(_drawing);
		
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
		
		issueContext.removeFirst();
	}
	
	protected SoGroup createSoGroup(com.basf.xpview.model.PlantItem plantItem, SoGroup parentNode, boolean createCatalogElement) {
		SoGroup plantItemNode = null;
		if (createCatalogElement == false) {
			if (useCatalog) {
				com.basf.xpview.model.PlantItem plantItemCatalog = plant.getCatalogList().findPlantItem(plantItem.getName());
				if (plantItemCatalog != null) {
					plantItemNode = (SoGroup) repManager.getNode(plantItemCatalog).clone(parentNode, repManager);
				}
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
	
	protected void handleNozzle(Nozzle _nozzle, PlantItemContainer parent, SoGroup parentNode, boolean readGraphics, boolean fromCatalog) {
		
		issueContext.addFirst(_nozzle);
		
		// engineering data
		com.basf.xpview.model.Nozzle nozzle = new com.basf.xpview.model.Nozzle(
				_nozzle.getComponentName(), _nozzle.getTagName(), parent);
		handlePlantItem(_nozzle, nozzle);
		parent.addPlantItem(nozzle);
		
		// graphics
		SoGroup group = createSoGroup(nozzle, parentNode, fromCatalog);
		if (readGraphics) {
			handlePlantItemGraphics(_nozzle, nozzle, group);
		}
		handlePlantItemPosition(_nozzle, group);
		
		group.getPosition().setEnabled(equipmentPositionEnabled);
		
		issueContext.removeFirst();
	}

	protected void handleComponent(Component _component, PlantItemContainer parent, SoGroup parentNode, boolean readGraphics, boolean fromCatalog) {
		
		issueContext.addFirst(_component);
		
		// engineering data
		com.basf.xpview.model.Component component = new com.basf.xpview.model.Component(
				_component.getComponentName(), _component.getTagName(), parent);
		handlePlantItem(_component, component);
		parent.addPlantItem(component);
		
		// graphics
		SoGroup group = createSoGroup(component, parentNode, fromCatalog);
		if (readGraphics) {
			handlePlantItemGraphics(_component, component, group);
		}
		handlePlantItemPosition(_component, group);
		group.getPosition().setEnabled(equipmentPositionEnabled);
		
		issueContext.removeFirst();
	}
	
	protected void handleEquipment(Equipment _equipment, PlantItemContainer parent, SoGroup parentNode, boolean readGraphics, boolean fromCatalog) {
		
		issueContext.addFirst(_equipment);
		
		// engineering data
		com.basf.xpview.model.Equipment equipment = new com.basf.xpview.model.Equipment(
				_equipment.getComponentName(), _equipment.getTagName(), parent);
		handlePlantItem(_equipment, equipment);
		parent.addPlantItem(equipment);

		// graphics
		SoGroup equipmentNode = new SoGroup(plantNode, getNextId(), _equipment.getTagName());
		plantNode.addNode(equipmentNode);
		
		SoGroup group = createSoGroup(equipment, equipmentNode, fromCatalog);
		if (readGraphics) {
			handlePlantItemGraphics(_equipment, equipment, group);
		}
		handlePlantItemPosition(_equipment, group);
		group.getPosition().setEnabled(equipmentPositionEnabled);
		
		// get all nozzles and subequipments
		for (Object object : _equipment
				.getDisciplineOrMinimumDesignPressureOrMaximumDesignPressure()) {
			if (object instanceof Nozzle) {
				Nozzle _nozzle = (Nozzle) object;
				handleNozzle(_nozzle, equipment, equipmentNode, readGraphics, fromCatalog);
			}
			if (object instanceof Equipment) {
				handleEquipment((Equipment) object, equipment, equipmentNode, readGraphics, fromCatalog);
			}
		}

		plant.getItems().add(equipment);
		
		issueContext.removeFirst();
	}

	protected void handlePlantItemPosition(PlantItem _plantItem, SoGroup group) {
		for (Object object : _plantItem.getPresentationOrExtentOrPersistentID()) {
			if (object instanceof Position) {
				handlePosition((Position) object, group);
			}
			if (useCatalog) {
				if (object instanceof Scale) {
					handleScale((Scale) object, group);
				}
			}
		}
	}
	
	protected void handlePlantItem(PlantItem _plantItem,
			com.basf.xpview.model.PlantItem plantItem) {

		PropertyData propData = plantItem.getPropertyData();
		handleAttributes(_plantItem, propData.addPropertyList("Default"));
		handleGenericAttributes(_plantItem, propData);
	}
	
	protected void handlePlantItemGraphics(PlantItem _plantItem,
			com.basf.xpview.model.PlantItem plantItem, SoGroup group) {
		for (Object object : _plantItem.getPresentationOrExtentOrPersistentID()) {
			if (object instanceof JAXBElement) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) object;
				Object obj = jaxbElement.getValue();
				handleCurves(obj, group);
			}
		}
	}

	protected void handleGenericAttributes(GenericAttributes genAttrs,
			PropertyData propertyData) {
		
		issueContext.addFirst(genAttrs);
		java.lang.String setName = genAttrs.getSet();
		if (setName == null) {
			reportIssue("Undefined attribute 'Set'", IssueType.WARNING);
			setName = "<UNDEFINED>";
		}
		PropertyList propList = propertyData.addPropertyList(setName);
		for (Object genAttrObj : genAttrs.getContent()) {
			if (genAttrObj instanceof GenericAttribute) {
				GenericAttribute genAttr = (GenericAttribute) genAttrObj;
				propList.addProperty(PropertyType.GENERIC, genAttr.getName(),
						genAttr.getValue(), genAttr.getFormat(),
						genAttr.getURI());
			}
		}
		
		issueContext.removeFirst();
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

		node.getPosition().origin.x = _position.location.x * scaleFactor;
		node.getPosition().origin.y = _position.location.y * scaleFactor;
		if (_position.location.z != null) {
			node.getPosition().origin.z = _position.location.z * scaleFactor;
		}

		node.getPosition().axis.x = _position.axis.x;
		node.getPosition().axis.y = _position.axis.y;
		node.getPosition().axis.z = _position.axis.z;

		if (node.getPosition().axis.length() < 0.0001) {
			reportIssue("Axis vector too short.", IssueType.ERROR);
		}
		
		node.getPosition().direction.x = _position.reference.x;
		node.getPosition().direction.y = _position.reference.y;
		node.getPosition().direction.z = _position.reference.z;
		
		if (node.getPosition().direction.length() < 0.0001) {
			reportIssue("Reference vector too short.", IssueType.ERROR);
		}
		
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
			Point3d point = new Point3d(coord.x * scaleFactor, coord.y * scaleFactor, 0);
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
			Point3d point = new Point3d(coord.x * scaleFactor, coord.y * scaleFactor, 0);
			points.add(point);
		}

		shape.init(points.toArray(new Point3d[points.size()]));
		shape.setFilled(false); // TODO

		group.addNode(shape);
	}

	protected void handleCircle(Circle _circle, SoGroup group) {

		SoCircle circle = new SoCircle(group, repManager.getFreeId(), "Circle");
		circle.init(_circle.radius * scaleFactor, false); // TODO
		handlePosition(_circle.getPosition(), circle);
		group.addNode(circle);
	}

	private int getNextId() {
		return repManager.getFreeId();
	}
}
