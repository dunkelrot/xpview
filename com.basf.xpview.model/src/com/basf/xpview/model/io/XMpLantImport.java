package com.basf.xpview.model.io;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import javax.vecmath.Point3d;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;

import org.apache.log4j.Logger;

import com.basf.xpview.model.Activator;
import com.basf.xpview.model.AnnotationContainer;
import com.basf.xpview.model.Catalog;
import com.basf.xpview.model.DrawingSizeType;
import com.basf.xpview.model.PipeConnector;
import com.basf.xpview.model.PipingNetwork;
import com.basf.xpview.model.PipingSegment;
import com.basf.xpview.model.Plant;
import com.basf.xpview.model.PlantItemContainer;
import com.basf.xpview.model.PlantSection;
import com.basf.xpview.model.PropertyData;
import com.basf.xpview.model.PropertyList;
import com.basf.xpview.model.PropertyType;
import com.basf.xpview.model.Thing;
import com.basf.xpview.model.Workspace;
import com.basf.xpview.model.graphics.RepresentationManager;
import com.basf.xpview.model.graphics.SoCircle;
import com.basf.xpview.model.graphics.SoGroup;
import com.basf.xpview.model.graphics.SoPolyLine;
import com.basf.xpview.model.graphics.SoPositionNode;
import com.basf.xpview.model.graphics.SoShape;
import com.basf.xpview.model.graphics.SoTransformation;
import com.basf.xpview.model.graphics.SoTrimmedCircle;
import com.basf.xpview.model.io.xmplant.AnnotationItem;
import com.basf.xpview.model.io.xmplant.CenterLine;
import com.basf.xpview.model.io.xmplant.Circle;
import com.basf.xpview.model.io.xmplant.Component;
import com.basf.xpview.model.io.xmplant.Coordinate;
import com.basf.xpview.model.io.xmplant.Curve;
import com.basf.xpview.model.io.xmplant.Drawing;
import com.basf.xpview.model.io.xmplant.DrawingBorder;
import com.basf.xpview.model.io.xmplant.Equipment;
import com.basf.xpview.model.io.xmplant.GenericAttribute;
import com.basf.xpview.model.io.xmplant.GenericAttributes;
import com.basf.xpview.model.io.xmplant.Label;
import com.basf.xpview.model.io.xmplant.Line;
import com.basf.xpview.model.io.xmplant.Nozzle;
import com.basf.xpview.model.io.xmplant.PipeConnectorSymbol;
import com.basf.xpview.model.io.xmplant.PipingComponent;
import com.basf.xpview.model.io.xmplant.PipingNetworkSegment;
import com.basf.xpview.model.io.xmplant.PipingNetworkSystem;
import com.basf.xpview.model.io.xmplant.PlantInformation;
import com.basf.xpview.model.io.xmplant.PlantItem;
import com.basf.xpview.model.io.xmplant.PlantModel;
import com.basf.xpview.model.io.xmplant.PolyLine;
import com.basf.xpview.model.io.xmplant.Position;
import com.basf.xpview.model.io.xmplant.ProcessInstrument;
import com.basf.xpview.model.io.xmplant.Scale;
import com.basf.xpview.model.io.xmplant.ScopeBubble;
import com.basf.xpview.model.io.xmplant.Shape;
import com.basf.xpview.model.io.xmplant.ShapeCatalogue;
import com.basf.xpview.model.io.xmplant.Text;
import com.basf.xpview.model.io.xmplant.TrimmedCurve;
import com.basf.xpview.model.io.xmplant.UnitsOfMeasure;
import com.basf.xpview.model.issues.IssueList;
import com.basf.xpview.model.issues.IssueTracker;
import com.basf.xpview.model.issues.IssueType;

public class XMpLantImport extends Import {

	private static Logger log = Logger.getLogger(XMpLantImport.class);
	
	private Plant plant;
	private SoGroup plantNode;
	private RepresentationManager repManager;
	private double scaleFactor = 1000.0;
	private IssueList issueList;
	
	private Deque<Object> issueContext; 
	
	protected boolean callMethod(java.lang.String methodName, java.lang.String prefix, Object obj, StringBuffer buffer) {
		boolean result = false;
		Class<?>[] parameterTypes = new Class[0]; 
		try {
			Method getID = obj.getClass().getMethod(methodName, parameterTypes);
			java.lang.String value = (java.lang.String) getID.invoke(obj, (Object[]) null);
			if (value != null) {
				buffer.append(prefix);
				buffer.append(value);
				result = true;
			}
		} catch (Exception ex) {
			// ignored
		}
		return result;
	}
	
	protected java.lang.String getObjectName(Object obj) {
		StringBuffer buffer = new StringBuffer();
		
		// call methods and stop as soon as one returns a value
		if (callMethod("getID", "ID:", obj, buffer)) {
		} else if (callMethod("getComponentName", "CNAME:", obj, buffer)) {
		} else if (callMethod("getName", "NAME:", obj, buffer)) {
		}
		
		buffer.append("[");
		buffer.append(obj.getClass().getSimpleName());
		buffer.append("]");
		return buffer.toString();
	}
	
	protected void reportIssue(java.lang.String message, IssueType type) {
		StringBuffer buffer = new StringBuffer();
		Iterator<Object> itor = issueContext.descendingIterator();
		while(itor.hasNext()) {
			Object obj = itor.next();
			buffer.append("/");
			buffer.append(getObjectName(obj));
		}
		issueList.addIssue(message, buffer.toString(), type);
	}
	
	@Override
	public boolean canRead(File file) {
		return true;
	}

	protected void tweak(java.lang.String systemId) {
		log.info("Adjusting import configuration for " + systemId);
		if (systemId.equals("AVEVAPID")) {
			setEquipmentPositionEnabled(Activator.getDefault().getPreferenceStore().getBoolean(Activator.AVEVA_APPLY_EQUIPMENT_POSITION));
			setUseCatalog(Activator.getDefault().getPreferenceStore().getBoolean(Activator.AVEVA_USE_CATALOG));
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
			reportIssue("Value for " + description + " is not defined! ", IssueType.WARNING);
			return "<UNDEFINED>";
		} else {
			return value;
		}
	}
	
	protected DrawingSizeType getSizeType(String sizeTypeDesc) {
		DrawingSizeType result = null;
		sizeTypeDesc = sizeTypeDesc.toUpperCase();
		for (DrawingSizeType type : DrawingSizeType.values()) {
			if (type.name().equals(sizeTypeDesc)) {
				result = type;
				log.warn("Drawing size: " + result.name());
			}
		}
		
		if (result == null) {
			log.warn("No Drawing size defined, assuming A1!");
			result = DrawingSizeType.A1;
		}
		
		return result;
	}
	
	protected void setScaleFactor(PlantInformation plantInfo) {
		UnitsOfMeasure uom = plantInfo.getUnitsOfMeasure();
		if (uom != null) {
			switch (uom.getDistance()) {
			case CENTIMETRE:
			case CM:
				scaleFactor = 10.0;
				break;
			case DECIMETRE:
				scaleFactor = 10.0;
				break;
			case FOOT:
			case FT:
				scaleFactor = 304.8;
				break;
			case IN:
			case INCH:
				scaleFactor = 25.4;
				break;
			case KILOMETRE:
			case KM:
				scaleFactor = 1000000.0;
				break;
			case M:
			case METRE:
				scaleFactor = 1000.0;
				break;
			case MICRON:
				scaleFactor = 0.001;
				break;
			case MILE:
				scaleFactor = 1609344.0;
				break;
			case YARD:
			case YD:
				scaleFactor = 914.4;
				break;
			case MILLIMETRE:
			case MM:
				scaleFactor = 1.0;
				break;
			}
			log.info("Scaling is: " + uom.getDistance().toString() + " -> " + scaleFactor );
		}
	}
	
	@Override
	public Plant read(File file, RepresentationManager repManager)
			throws Exception {
		this.repManager = repManager;
		try {
			Date time = new Date();
			issueContext = new ArrayDeque<Object>();
			IssueTracker.getInstance().clear();
			
			JAXBContext context = JAXBContext.newInstance(PlantModel.class);
			Unmarshaller um = context.createUnmarshaller();
			PlantModel plantModel = (PlantModel) um.unmarshal(file);

			plant = new Plant("<UNDEFINED>");
			issueList = IssueTracker.getInstance().addIssueList(plant);
			issueContext.addFirst(plantModel);
			
			PlantInformation plantInfo = plantModel.getPlantInformation();
			if (plantInfo != null) {
				java.lang.String projectName = getStringValue(plantInfo, "ProjectName", plantInfo.getProjectName());
				plant.setName(projectName);
				setScaleFactor(plantInfo);
			} else {
				reportIssue("Missing PlantInformation, assuming mm as units", IssueType.WARNING);
			}
			
			Workspace.getInstance().setPlant(plant);
			tweak(plantModel.getPlantInformation().getOriginatingSystem());
			
			plantNode = new SoGroup(null, repManager.getFreeId(), "Plant");
			repManager.addNode(plantNode, plant);

			handlePlantModel(plantModel);
			
			issueContext.removeFirst();
			
			long timeDiff = new Date().getTime() - time.getTime();
			log.info("Import finished after " + timeDiff + "ms");
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
			if (object instanceof PipingNetworkSystem) {
				handlePipingNetworkSystem((PipingNetworkSystem) object, plant);
			}
		}
	}

	protected void handlePipingNetworkSystem(PipingNetworkSystem _pipingNetworkSystem, Plant plant) {
		issueContext.addFirst(_pipingNetworkSystem);
		
		PipingNetwork pipingNetwork = new PipingNetwork(_pipingNetworkSystem.getTagName(), plant.getPipingNetworkList());
		
		SoGroup pipingNetworkNode = new SoGroup(plantNode, getNextId(), "PipingNetwork");
		plantNode.addNode(pipingNetworkNode);
		RepresentationManager.getInstance().addNode(pipingNetworkNode, pipingNetwork);
		
		handleAttributes(_pipingNetworkSystem, pipingNetwork.getPropertyData().addPropertyList("Default"));
		handleGenericAttributes(_pipingNetworkSystem, pipingNetwork.getPropertyData());
		
		plant.getPipingNetworkList().addPipingNetwork(pipingNetwork);
		
		for (Object object : _pipingNetworkSystem.getNominalDiameterOrInsideDiameterOrOutsideDiameter()) {
			if (object instanceof PipingNetworkSegment) {
				handlePipingNetworkSegment((PipingNetworkSegment) object, pipingNetwork, pipingNetworkNode);
			}
		}
		issueContext.removeFirst();
	}
	
	protected void handlePipingNetworkSegment(PipingNetworkSegment _pipingNetworkSegment, PipingNetwork pipingNetwork, SoGroup pipingNetworkNode) {
		issueContext.addFirst(_pipingNetworkSegment);
		
		PipingSegment pipingSegment = new PipingSegment("Segment", pipingNetwork);
		pipingNetwork.getSegments().add(pipingSegment);

		SoGroup pipingSegmentNode = new SoGroup(pipingNetworkNode, getNextId(), "PipingNetwork");
		pipingNetworkNode.addNode(pipingSegmentNode);

		RepresentationManager.getInstance().addNode(pipingSegmentNode, pipingSegment);
		
		handleAttributes(_pipingNetworkSegment, pipingSegment.getPropertyData().addPropertyList("Default"));
		handleGenericAttributes(_pipingNetworkSegment, pipingSegment.getPropertyData());
		
		for (Object object : _pipingNetworkSegment.getNominalDiameterOrInsideDiameterOrOutsideDiameter()) {
			if (object instanceof JAXBElement) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) object;
				Object obj = jaxbElement.getValue();
				if (obj instanceof CenterLine) {
					handleCenterLine((CenterLine) obj, pipingSegmentNode);
				}
				if (obj instanceof PipingComponent) {
					handlePipingComponent((PipingComponent) obj, pipingSegment, pipingSegmentNode);
				}
			}
			if (object instanceof ProcessInstrument) {
				handleProcessInstrument((ProcessInstrument) object, pipingSegment, pipingSegmentNode);
			}
			if (object instanceof PipeConnectorSymbol) {
				handlePipeConnectorSymbol((PipeConnectorSymbol) object, pipingSegment, pipingSegmentNode);
			}	
		}
		
		issueContext.removeFirst();
	}
	
	protected void handlePipeConnectorSymbol(PipeConnectorSymbol _symbol, AnnotationContainer container, SoGroup parentNode) {
		issueContext.addFirst(_symbol);
		
		PipeConnector pipeConnector = new PipeConnector(_symbol.getComponentName(), container);
		container.addAnnotation(pipeConnector);
		
		SoTransformation group = createSoGroup(pipeConnector, parentNode, false);
		handleAnnotationItemGraphics(_symbol, group);
		
		handleAttributes(_symbol, pipeConnector.getPropertyData().addPropertyList("Default"));
		handleGenericAttributes(_symbol, pipeConnector.getPropertyData());
		
		issueContext.removeFirst();
	}
	
	protected void handleProcessInstrument(ProcessInstrument _processInstrument, PlantItemContainer processInstrumentContainer, SoGroup parentNode) {
		issueContext.addFirst(_processInstrument);
		
		com.basf.xpview.model.ProcessInstrument processInstrument = new com.basf.xpview.model.ProcessInstrument(_processInstrument.getTagName(), processInstrumentContainer);
		processInstrumentContainer.addPlantItem(processInstrument);

		SoGroup processInstrumentNode = new SoGroup(parentNode, getNextId(), "ProcessInstrument");
		parentNode.addNode(processInstrumentNode);
		RepresentationManager.getInstance().addNode(processInstrumentNode, processInstrument);
		
		handleAttributes(_processInstrument, processInstrument.getPropertyData().addPropertyList("Default"));
		handleGenericAttributes(_processInstrument, processInstrument.getPropertyData());
		
		// graphics
		SoTransformation group = createSoGroup(processInstrument, parentNode, false);
		handlePlantItemGraphics(_processInstrument, processInstrument, group);
		
		for (Object object : _processInstrument.getProcessInstrumentOrComponentOrNominalDiameter()) {
			if (object instanceof JAXBElement) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) object;
				Object obj = jaxbElement.getValue();
				if (obj instanceof PipingComponent) {
					handleProcessInstrument((ProcessInstrument) obj, processInstrument, processInstrumentNode);
				}
			}
			if (object instanceof Component) {
				handleComponent((Component) object, processInstrument, processInstrumentNode);
			}
		}
		
		issueContext.removeFirst();
	}
	
	protected void handlePipingComponent(PipingComponent _pipingComponent, PlantItemContainer pipingComponentContainer, SoGroup parentNode) {
		issueContext.addFirst(_pipingComponent);
		
		com.basf.xpview.model.PipingComponent pipingComponent = new com.basf.xpview.model.PipingComponent(_pipingComponent.getTagName(), pipingComponentContainer);
		pipingComponentContainer.addPlantItem(pipingComponent);

		SoGroup pipingComponentNode = new SoGroup(parentNode, getNextId(), "PipingComponent");
		parentNode.addNode(pipingComponentNode);

		RepresentationManager.getInstance().addNode(pipingComponentNode, pipingComponent);
		
		handleAttributes(_pipingComponent, pipingComponent.getPropertyData().addPropertyList("Default"));
		handleGenericAttributes(_pipingComponent, pipingComponent.getPropertyData());
		
		// graphics
		SoTransformation group = createSoGroup(pipingComponent, parentNode, false);
		handlePlantItemGraphics(_pipingComponent, pipingComponent, group);
		// handlePlantItemPosition(_pipingComponent, group);
		
		for (Object object : _pipingComponent.getPipingComponentOrComponentOrConnectionType()) {
			if (object instanceof JAXBElement) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) object;
				Object obj = jaxbElement.getValue();
				if (obj instanceof PipingComponent) {
					handlePipingComponent((PipingComponent) obj, pipingComponent, pipingComponentNode);
				}
			}
		}
		
		issueContext.removeFirst();
	}
	
	protected void handleComponent(Component _component, PlantItemContainer container, SoGroup parentNode) {
		issueContext.addFirst(_component);
		
		com.basf.xpview.model.Component component = new com.basf.xpview.model.Component(_component.getTagName(), container);
		container.addPlantItem(component);

		SoGroup componentNode = new SoGroup(parentNode, getNextId(), "Component");
		parentNode.addNode(componentNode);

		RepresentationManager.getInstance().addNode(componentNode, component);
		
		handleAttributes(_component, component.getPropertyData().addPropertyList("Default"));
		handleGenericAttributes(_component, component.getPropertyData());
		
		// graphics
		SoTransformation group = createSoGroup(component, parentNode, false);
		handlePlantItemGraphics(_component, component, group);
		// handlePlantItemPosition(_pipingComponent, group);
		
		for (Component _componentChild: _component.getComponent()) {
			handleComponent(_componentChild, component, componentNode);
		}
		
		issueContext.removeFirst();
	}
	
	protected void handleCenterLine(CenterLine _centerLine, SoGroup parent) {

			List<Coordinate> coords = null;
			coords = _centerLine.getCoordinate();

			SoPolyLine polyLine = new SoPolyLine(parent, getNextId(), "");
			ArrayList<Point3d> points = new ArrayList<Point3d>();
			for (Coordinate coord : coords) {
				Point3d point = new Point3d(coord.getX() * scaleFactor, coord.getY() * scaleFactor, 0);
				points.add(point);
			}
			polyLine.init(points.toArray(new Point3d[points.size()]));

			parent.addNode(polyLine);
		
	}
	
	protected void handleShapeCatalog(ShapeCatalogue _shapeCatalog, Plant plant) {
		issueContext.addFirst(_shapeCatalog);
		
		String catalogName = getStringValue(_shapeCatalog, "Name", _shapeCatalog.getName());
		if (plant.getCatalogList().hasCatalog(catalogName)) {
			reportIssue("Catalog with name " + catalogName + " already exists.", IssueType.WARNING);
		}
		Catalog catalog = plant.getCatalogList().addCatalog(
				catalogName, _shapeCatalog.getUnits());
		
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
		
		issueContext.removeFirst();
	}

	protected void handleCurves(Object curve, SoTransformation group) {
		
		issueContext.addFirst(curve);
		
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
			SoTransformation transform = new SoTransformation(group, getNextId(), "Circle");
			group.addNode(transform);
			handleCircle((Circle) curve, transform);
		}
		if (curve instanceof TrimmedCurve) {
			SoTransformation transform = new SoTransformation(group, getNextId(), "TrimmedCurve");
			group.addNode(transform);
			handleTrimmedCurve((TrimmedCurve) curve, transform);
		}
		
		issueContext.removeFirst();
	}

	protected void handleDrawing(Drawing _drawing, PlantSection plantSection) {

		issueContext.addFirst(_drawing);
		
		com.basf.xpview.model.Drawing drawing = plantSection.getDrawings()
				.addDrawing(_drawing.getName());
		
		String size = getStringValue(_drawing, "Drawing size", _drawing.getSize());
		drawing.setSize(getSizeType(size));
		
		PropertyData propData = drawing.getPropertyData();
		handleAttributes(_drawing, propData.addPropertyList("Default"));
		handleGenericAttributes(_drawing, propData);

		SoTransformation drawingNode = new SoTransformation(plantNode, getNextId(), _drawing.getName());

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
				handleDrawingBorder((DrawingBorder) object, drawing.getBorder(), drawingNode);
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
	
	protected void handleDrawingBorder(DrawingBorder _border, com.basf.xpview.model.DrawingBorder border, SoGroup parentNode) {
		SoTransformation group = new SoTransformation(parentNode, getNextId(), "Border");
		group.getPosition().setEnabled(equipmentPositionEnabled);
		parentNode.addNode(group);
		
		PropertyData propData = border.getPropertyData();
		handleAttributes(_border, propData.addPropertyList("Default"));
		
		for (Object object : _border.getCurveOrTextOrGenericAttributes()) {
			if (object instanceof JAXBElement) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) object;
				Object obj = jaxbElement.getValue();
				handleCurves(obj, group);
			}
			if (object instanceof GenericAttributes) {
				handleGenericAttributes((GenericAttributes) object,
						propData);
			}
		}
	}
	
	protected SoTransformation createSoGroup(Thing thing, SoGroup parentNode, boolean createCatalogElement) {
		SoTransformation plantItemNode = null;
		if (createCatalogElement == false) {
			if (useCatalog) {
				com.basf.xpview.model.PlantItem plantItemCatalog = plant.getCatalogList().findPlantItem(thing.getName());
				if (plantItemCatalog != null) {
					plantItemNode = (SoTransformation) repManager.getNode(plantItemCatalog).clone(parentNode, repManager);
				}
			}
			if (plantItemNode == null) {
				plantItemNode = new SoTransformation(parentNode, getNextId(), thing.getName());
			}
			parentNode.addNode(plantItemNode);
		} else {
			plantItemNode = new SoTransformation(null, getNextId(), thing.getName());
		}
		repManager.addNode(plantItemNode, thing);
		return plantItemNode;
	}
	
	protected void handleNozzle(Nozzle _nozzle, PlantItemContainer parent, SoGroup parentNode, boolean readGraphics, boolean fromCatalog) {
		
		issueContext.addFirst(_nozzle);
		
		// engineering data
		com.basf.xpview.model.Nozzle nozzle = new com.basf.xpview.model.Nozzle(
				_nozzle.getComponentName(), _nozzle.getTagName(), parent);
		handlePlantItem(_nozzle, nozzle, fromCatalog);
		parent.addPlantItem(nozzle);
		
		// graphics
		SoTransformation group = createSoGroup(nozzle, parentNode, fromCatalog);
		if (readGraphics) {
			handlePlantItemGraphics(_nozzle, nozzle, group);
		}
		handlePlantItemPosition(_nozzle, group);
		
		group.getPosition().setEnabled(equipmentPositionEnabled);
		
		issueContext.removeFirst();
	}

	protected void handleComponent(Component _component, PlantItemContainer parent, SoTransformation parentNode, boolean readGraphics, boolean fromCatalog) {
		
		issueContext.addFirst(_component);
		
		// engineering data
		com.basf.xpview.model.Component component = new com.basf.xpview.model.Component(
				_component.getComponentName(), parent);
		handlePlantItem(_component, component, fromCatalog);
		parent.addPlantItem(component);
		
		// graphics
		SoTransformation group = createSoGroup(component, parentNode, fromCatalog);
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
		handlePlantItem(_equipment, equipment, fromCatalog);
		parent.addPlantItem(equipment);

		// graphics
		SoTransformation equipmentNode = new SoTransformation(plantNode, getNextId(), _equipment.getTagName());
		plantNode.addNode(equipmentNode);
		
		SoTransformation group = createSoGroup(equipment, equipmentNode, fromCatalog);
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

	protected void handlePlantItemPosition(PlantItem _plantItem, SoTransformation group) {
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
			com.basf.xpview.model.PlantItem plantItem, boolean fromCatalog) {

		PropertyData propData = plantItem.getPropertyData();
		
		if (fromCatalog == false) {
			plantItem.setClassName(getStringValue(_plantItem, "ComponentClass", _plantItem.getComponentClass()));
			plantItem.setTagName(getStringValue(_plantItem, "TagName", _plantItem.getTagName()));
		}
		
		handleAttributes(_plantItem, propData.addPropertyList("Default"));
		handleGenericAttributes(_plantItem, propData);
	}
	
	protected void handleCurves(List<Object> curves, SoTransformation group) {
		for (Object object : curves) {
			if (object instanceof JAXBElement) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) object;
				Object obj = jaxbElement.getValue();
				handleCurves(obj, group);
			}
		}
	}
	
	protected void handlePlantItemGraphics(PlantItem _plantItem,
			com.basf.xpview.model.PlantItem plantItem, SoTransformation group) {
		handleCurves(_plantItem.getPresentationOrExtentOrPersistentID(), group);
	}
	
	protected void handleAnnotationItemGraphics(AnnotationItem _annotationItem, SoTransformation group) {
		handleCurves(_annotationItem.getPresentationOrExtentOrPersistentID(), group);
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

	protected void handleGenericAttributes(AnnotationItem _annotationItem,
			PropertyData propertyData) {
		for (Object object : _annotationItem.getPresentationOrExtentOrPersistentID()) {
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
						field.setAccessible(true);
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

	protected void handlePosition(Position _position, SoPositionNode node) {

		node.getPosition().origin.x = _position.getLocation().getX() * scaleFactor;
		node.getPosition().origin.y = _position.getLocation().getY() * scaleFactor;
		if (_position.getLocation().getZ() != null) {
			node.getPosition().origin.z = _position.getLocation().getZ() * scaleFactor;
		}

		node.getPosition().axis.x = _position.getAxis().getX();
		node.getPosition().axis.y = _position.getAxis().getY();
		node.getPosition().axis.z = _position.getAxis().getZ();

		if (node.getPosition().axis.length() < 0.0001) {
			reportIssue("Axis vector too short.", IssueType.ERROR);
		}
		
		node.getPosition().direction.x = _position.getReference().getX();
		node.getPosition().direction.y = _position.getReference().getY();
		node.getPosition().direction.z = _position.getReference().getZ();
		
		if (node.getPosition().direction.length() < 0.000001) {
			reportIssue("Reference vector too short.", IssueType.ERROR);
		}
		
		node.getPosition().normalize();
		node.getPosition().calculateRotationAngle();
	}
	
	protected void handleScale(Scale _scale, SoTransformation node) {

		node.getScale().x = _scale.getX();
		node.getScale().y = _scale.getY();
		node.scale(_scale.getX(), _scale.getY());
	}

	protected void handleCurve(Curve curve, SoGroup group) {

		List<Coordinate> coords = null;
		if (curve instanceof PolyLine) {
			PolyLine _polyLine = (PolyLine) curve;
			coords = _polyLine.getCoordinate();
		} else if (curve instanceof Line) {
			Line _line = (Line) curve;
			coords = _line.getCoordinate();
		} else {
			System.out.println("Unsupported curve type: "
					+ curve.getClass().getName());
			return;
		}

		SoPolyLine polyLine = new SoPolyLine(group, getNextId(), "");
		ArrayList<Point3d> points = new ArrayList<Point3d>();
		for (Coordinate coord : coords) {
			Point3d point = new Point3d(coord.getX() * scaleFactor, coord.getY() * scaleFactor, 0);
			points.add(point);
		}
		polyLine.init(points.toArray(new Point3d[points.size()]));

		group.addNode(polyLine);
	}

	protected void handleShape(Shape _shape, SoGroup group) {

		List<Coordinate> coords = _shape.getCoordinate();

		SoShape shape = new SoShape(group, getNextId(), "");
		ArrayList<Point3d> points = new ArrayList<Point3d>();
		for (Coordinate coord : coords) {
			Point3d point = new Point3d(coord.getX() * scaleFactor, coord.getY() * scaleFactor, 0);
			points.add(point);
		}

		shape.init(points.toArray(new Point3d[points.size()]));
		shape.setFilled(false); // TODO

		group.addNode(shape);
	}

	protected void handleCircle(Circle _circle, SoTransformation group) {

		SoCircle circle = new SoCircle(group, repManager.getFreeId(), "Circle");
		circle.init(_circle.getRadius() * scaleFactor, false); // TODO
		handlePosition(_circle.getPosition(), group);
		group.addNode(circle);
	}
	
	protected void handleTrimmedCurve(TrimmedCurve _trimmedCurve, SoTransformation group) {

		if (_trimmedCurve.getCircle() != null) {
			SoTrimmedCircle circle = new SoTrimmedCircle(group, repManager.getFreeId(), "TrimmedCircle");
			circle.init(_trimmedCurve.getCircle().getRadius() * scaleFactor, _trimmedCurve.getStartAngle(), _trimmedCurve.getEndAngle());
			handlePosition(_trimmedCurve.getCircle().getPosition(), circle);
			group.addNode(circle);
		}
		if (_trimmedCurve.getEllipse() != null) {
			log.info("Trimmed Ellipse is not implemented!");
		}
	}

	private int getNextId() {
		return repManager.getFreeId();
	}
}
