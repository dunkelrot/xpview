package org.dexpi.xpview.model.io;

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
import org.dexpi.xpview.model.Activator;
import org.dexpi.xpview.model.AnnotationContainer;
import org.dexpi.xpview.model.Catalog;
import org.dexpi.xpview.model.Connectable;
import org.dexpi.xpview.model.DrawingSizeType;
import org.dexpi.xpview.model.Node.Flow;
import org.dexpi.xpview.model.Node.Function;
import org.dexpi.xpview.model.Node.Type;
import org.dexpi.xpview.model.PipeConnector;
import org.dexpi.xpview.model.PipingNetwork;
import org.dexpi.xpview.model.PipingSegment;
import org.dexpi.xpview.model.Plant;
import org.dexpi.xpview.model.PlantItemContainer;
import org.dexpi.xpview.model.PlantSection;
import org.dexpi.xpview.model.PropertyData;
import org.dexpi.xpview.model.PropertyList;
import org.dexpi.xpview.model.PropertyType;
import org.dexpi.xpview.model.TextBag;
import org.dexpi.xpview.model.Thing;
import org.dexpi.xpview.model.Workspace;
import org.dexpi.xpview.model.graphics.NodeUtils;
import org.dexpi.xpview.model.graphics.RepresentationManager;
import org.dexpi.xpview.model.graphics.SoCircle;
import org.dexpi.xpview.model.graphics.SoGroup;
import org.dexpi.xpview.model.graphics.SoMaterial;
import org.dexpi.xpview.model.graphics.SoPoint;
import org.dexpi.xpview.model.graphics.SoPolyLine;
import org.dexpi.xpview.model.graphics.SoPositionNode;
import org.dexpi.xpview.model.graphics.SoShape;
import org.dexpi.xpview.model.graphics.SoText;
import org.dexpi.xpview.model.graphics.SoTransformation;
import org.dexpi.xpview.model.graphics.SoTrimmedCircle;
import org.dexpi.xpview.model.io.xmplant.AnnotationItem;
import org.dexpi.xpview.model.io.xmplant.CenterLine;
import org.dexpi.xpview.model.io.xmplant.Circle;
import org.dexpi.xpview.model.io.xmplant.Component;
import org.dexpi.xpview.model.io.xmplant.ConnectionPoints;
import org.dexpi.xpview.model.io.xmplant.Coordinate;
import org.dexpi.xpview.model.io.xmplant.Curve;
import org.dexpi.xpview.model.io.xmplant.Drawing;
import org.dexpi.xpview.model.io.xmplant.DrawingBorder;
import org.dexpi.xpview.model.io.xmplant.Equipment;
import org.dexpi.xpview.model.io.xmplant.GenericAttribute;
import org.dexpi.xpview.model.io.xmplant.GenericAttributes;
import org.dexpi.xpview.model.io.xmplant.InstrumentComponent;
import org.dexpi.xpview.model.io.xmplant.Label;
import org.dexpi.xpview.model.io.xmplant.Line;
import org.dexpi.xpview.model.io.xmplant.Node;
import org.dexpi.xpview.model.io.xmplant.Nozzle;
import org.dexpi.xpview.model.io.xmplant.PipeConnectorSymbol;
import org.dexpi.xpview.model.io.xmplant.PipingComponent;
import org.dexpi.xpview.model.io.xmplant.PipingNetworkSegment;
import org.dexpi.xpview.model.io.xmplant.PipingNetworkSystem;
import org.dexpi.xpview.model.io.xmplant.PlantInformation;
import org.dexpi.xpview.model.io.xmplant.PlantItem;
import org.dexpi.xpview.model.io.xmplant.PlantModel;
import org.dexpi.xpview.model.io.xmplant.PolyLine;
import org.dexpi.xpview.model.io.xmplant.Position;
import org.dexpi.xpview.model.io.xmplant.ProcessInstrument;
import org.dexpi.xpview.model.io.xmplant.Scale;
import org.dexpi.xpview.model.io.xmplant.ScopeBubble;
import org.dexpi.xpview.model.io.xmplant.Shape;
import org.dexpi.xpview.model.io.xmplant.ShapeCatalogue;
import org.dexpi.xpview.model.io.xmplant.Text;
import org.dexpi.xpview.model.io.xmplant.TrimmedCurve;
import org.dexpi.xpview.model.io.xmplant.UnitsOfMeasure;
import org.dexpi.xpview.model.issues.IssueList;
import org.dexpi.xpview.model.issues.IssueTracker;
import org.dexpi.xpview.model.issues.IssueType;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * XMpLant importer. Note that the importer creates the internal data model AND
 * creates the graphics scene graph. Might be worth to separate the data and
 * graphics processing in the future.
 * 
 * @author Arndt Teinert
 * 
 */
public class XMpLantImport extends Import {

	private static Logger log = Logger.getLogger(XMpLantImport.class);

	private Plant plant;
	private SoGroup plantNode;
	private RepresentationManager repManager;
	private double scaleFactor = 1000.0;
	private IssueList issueList;

	private Deque<Object> issueContext;

	/**
	 * Tries to call a given method by name for the given object and appends the
	 * returned value (which must be a String) to the given StringBuffer. Note
	 * that all {@link Exception}s are ignored, check the return value.
	 * 
	 * @param methodName
	 *            the method to call
	 * @param prefix
	 *            string to append before the retrieved value
	 * @param obj
	 *            the object
	 * @param buffer
	 *            the {@link StringBuffer} to fill
	 * 
	 * @return true in case the method returned a String value
	 */
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

	/**
	 * Utility method to get the name for a given XMpLant object This method
	 * tries to get the name in the following order: 1. ID (XMpLant) 2.
	 * ComponentName (XMpLant) 3. Name (XMpLant)
	 * 
	 * The class name (Java JAXB) is always added!
	 * 
	 * @param obj
	 * @return at least the JAXB class name
	 */
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
		while (itor.hasNext()) {
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
			setEquipmentPositionEnabled(Activator.getDefault().getPreferenceStore()
					.getBoolean(Activator.AVEVA_APPLY_EQUIPMENT_POSITION));
			setLabelPositionEnabled(Activator.getDefault().getPreferenceStore()
					.getBoolean(Activator.AVEVA_APPLY_LABEL_POSITION));
			setTextPositionEnabled(Activator.getDefault().getPreferenceStore()
					.getBoolean(Activator.AVEVA_APPLY_TEXT_POSITION));
			setUseCatalog(Activator.getDefault().getPreferenceStore().getBoolean(Activator.AVEVA_USE_CATALOG));
		}
		if (systemId.equals("Autodesk AutoCAD P&ID")) {
			setEquipmentPositionEnabled(Activator.getDefault().getPreferenceStore()
					.getBoolean(Activator.AUTODESK_APPLY_EQUIPMENT_POSITION));
			setLabelPositionEnabled(Activator.getDefault().getPreferenceStore()
					.getBoolean(Activator.AUTODESK_APPLY_LABEL_POSITION));
			setTextPositionEnabled(Activator.getDefault().getPreferenceStore()
					.getBoolean(Activator.AUTODESK_APPLY_TEXT_POSITION));
			setUseCatalog(Activator.getDefault().getPreferenceStore().getBoolean(Activator.AUTODESK_USE_CATALOG));
		}
		if (systemId.equals("Comos")) {
			setEquipmentPositionEnabled(Activator.getDefault().getPreferenceStore()
					.getBoolean(Activator.SIEMENS_APPLY_EQUIPMENT_POSITION));
			setLabelPositionEnabled(Activator.getDefault().getPreferenceStore()
					.getBoolean(Activator.SIEMENS_APPLY_LABEL_POSITION));
			setTextPositionEnabled(Activator.getDefault().getPreferenceStore()
					.getBoolean(Activator.SIEMENS_APPLY_TEXT_POSITION));
			setUseCatalog(Activator.getDefault().getPreferenceStore().getBoolean(Activator.SIEMENS_USE_CATALOG));
		}
		if (systemId.equals("SPPID")) {
			setEquipmentPositionEnabled(Activator.getDefault().getPreferenceStore()
					.getBoolean(Activator.INTERGRAPH_APPLY_EQUIPMENT_POSITION));
			setLabelPositionEnabled(Activator.getDefault().getPreferenceStore()
					.getBoolean(Activator.INTERGRAPH_APPLY_LABEL_POSITION));
			setTextPositionEnabled(Activator.getDefault().getPreferenceStore()
					.getBoolean(Activator.INTERGRAPH_APPLY_TEXT_POSITION));
			setUseCatalog(Activator.getDefault().getPreferenceStore().getBoolean(Activator.INTERGRAPH_USE_CATALOG));
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

	/**
	 * Sets the internal scaling factor. Internally the import works with
	 * MILLIMETRE values.
	 * 
	 * @param plantInfo
	 */
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
			log.info("Scaling is: " + uom.getDistance().toString() + " -> " + scaleFactor);
		}
	}

	@Override
	public Plant read(File file, RepresentationManager repManager, IProgressMonitor progressMonitor) throws Exception {
		this.repManager = repManager;
		try {
			Date time = new Date();
			issueContext = new ArrayDeque<Object>();
			IssueTracker.getInstance().clear();
			progressMonitor.worked(1);

			JAXBContext context = JAXBContext.newInstance(PlantModel.class);
			Unmarshaller um = context.createUnmarshaller();
			PlantModel plantModel = (PlantModel) um.unmarshal(file);

			progressMonitor.worked(1);

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
			plantNode.setSelectable(false);
			repManager.addNode(plantNode, plant);

			handlePlantModel(plantModel);

			progressMonitor.worked(1);

			issueContext.removeFirst();

			NodeUtils.updateBoundingBoxShapes(plantNode);

			long timeDiff = new Date().getTime() - time.getTime();
			log.info("Import finished after " + timeDiff + "ms");
		} catch (JAXBException ex) {
			throw new Exception(ex);
		}
		return plant;
	}

	protected void handlePlantModel(PlantModel plantModel) {
		for (Object object : plantModel.getPresentationOrShapeCatalogueOrDrawing()) {
			if (object instanceof Equipment) {
				handleEquipment((Equipment) object, plant.getEquipmentList(), plantNode, 
						useCatalog ? false : true, false);
			}
			if (object instanceof Drawing) {
				handleDrawing((Drawing) object, plant);
			}
			if (object instanceof InstrumentComponent) {
				handleInstrumentComponent((InstrumentComponent) object, plant.getInstrumentList(), plantNode, 
						useCatalog ? false : true, false);
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
		pipingNetworkNode.setSelectable(false);
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

	protected void handlePipingNetworkSegment(PipingNetworkSegment _pipingNetworkSegment, PipingNetwork pipingNetwork,
			SoGroup pipingNetworkNode) {
		issueContext.addFirst(_pipingNetworkSegment);

		PipingSegment pipingSegment = new PipingSegment("Segment", pipingNetwork);
		pipingNetwork.getSegments().add(pipingSegment);

		SoGroup pipingSegmentNode = new SoGroup(pipingNetworkNode, getNextId(), "PipingNetwork");
		pipingSegmentNode.setSelectable(false);
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

	protected void handlePipeConnectorSymbol(PipeConnectorSymbol _symbol, AnnotationContainer container,
			SoGroup parentNode) {
		issueContext.addFirst(_symbol);

		PipeConnector pipeConnector = new PipeConnector(_symbol.getComponentName(), container);
		container.addAnnotation(pipeConnector);

		SoTransformation group = createSoGroup(pipeConnector, parentNode, false);
		handleAnnotationItemGraphics(_symbol, group);

		handleAttributes(_symbol, pipeConnector.getPropertyData().addPropertyList("Default"));
		handleGenericAttributes(_symbol, pipeConnector.getPropertyData());

		issueContext.removeFirst();
	}

	protected void handleProcessInstrument(ProcessInstrument _processInstrument,
			PlantItemContainer processInstrumentContainer, SoGroup parentNode) {
		issueContext.addFirst(_processInstrument);

		org.dexpi.xpview.model.ProcessInstrument processInstrument = new org.dexpi.xpview.model.ProcessInstrument(
				_processInstrument.getTagName(), processInstrumentContainer);
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

	protected void handlePipingComponent(PipingComponent _pipingComponent, PlantItemContainer pipingComponentContainer,
			SoGroup parentNode) {
		issueContext.addFirst(_pipingComponent);

		org.dexpi.xpview.model.PipingComponent pipingComponent = new org.dexpi.xpview.model.PipingComponent(
				_pipingComponent.getTagName(), pipingComponentContainer);
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

		org.dexpi.xpview.model.Component component = new org.dexpi.xpview.model.Component(_component.getTagName(),
				container);
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

		for (Component _componentChild : _component.getComponent()) {
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
		Catalog catalog = plant.getCatalogList().addCatalog(catalogName, _shapeCatalog.getUnits());

		for (Object object : _shapeCatalog.getEquipmentOrNozzleOrPipingComponent()) {
			if (object instanceof Equipment) {
				handleEquipment((Equipment) object, catalog, null, true, true);
			}
			if (object instanceof Component) {
				handleComponent((Component) object, catalog, null, true, true);
			}
			if (object instanceof Nozzle) {
				handleNozzle((Nozzle) object, catalog, null, true, true);
			}
			if (object instanceof Label) {
				handleLabel((Label) object, catalog, null, true, true);
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

		org.dexpi.xpview.model.Drawing drawing = plantSection.getDrawings().addDrawing(_drawing.getName());

		String size = getStringValue(_drawing, "Drawing size", _drawing.getSize());
		drawing.setSize(getSizeType(size));

		PropertyData propData = drawing.getPropertyData();
		handleAttributes(_drawing, propData.addPropertyList("Default"));
		handleGenericAttributes(_drawing, propData);

		SoTransformation drawingNode = new SoTransformation(plantNode, getNextId(), _drawing.getName());
		drawingNode.setSelectable(false);

		plantNode.addNode(drawingNode);

		repManager.addNode(drawingNode, drawing);
		for (Object object : _drawing.getComponentOrCurveOrText()) {
			if (object instanceof JAXBElement) {
				JAXBElement<?> jaxbElement = (JAXBElement<?>) object;
				Object obj = jaxbElement.getValue();
				handleCurves(obj, drawingNode);
			}
			if (object instanceof Text) {
				handleText((Text) object, drawingNode, drawing);
			}
			if (object instanceof DrawingBorder) {
				handleDrawingBorder((DrawingBorder) object, drawing.getBorder(), drawingNode);
			}
			if (object instanceof Label) {
				handleLabel((Label) object, drawing, drawingNode, true, false);
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

	protected void handleLabel(Label _label, AnnotationContainer parent, SoGroup parentNode, boolean readGraphics,
			boolean fromCatalog) {
		issueContext.addFirst(_label);

		org.dexpi.xpview.model.Label label = new org.dexpi.xpview.model.Label(_label.getComponentName(), parent);
		parent.addAnnotation(label);

		PropertyData propData = label.getPropertyData();
		handleAttributes(_label, propData.addPropertyList("Default"));
		handleGenericAttributes(_label, propData);

		// graphics
		SoTransformation group = createSoGroup(label, parentNode, fromCatalog);
		if (readGraphics) {
			handleLabelGraphics(_label, label, group);
			handleLabelText(_label, label, group);
		}
		handleLabelPosition(_label, group);
		group.getPosition().setEnabled(labelPositionEnabled);

		issueContext.removeFirst();
	}

	protected void handleText(Text _text, SoGroup parentNode, TextBag textBag) {
		issueContext.addFirst(_text);

		org.dexpi.xpview.model.Text text = new org.dexpi.xpview.model.Text(textBag.getTextList());
		textBag.getTextList().addText(text);

		PropertyData propData = text.getPropertyData();
		handleAttributes(_text, propData.addPropertyList("Default"));
		handleGenericAttributes(_text, propData);

		// graphics
		SoTransformation group = new SoTransformation(parentNode, getNextId(), "Text");
		parentNode.addNode(group);

		for (Object object : _text.getPresentationOrExtentOrPosition()) {
			if (object instanceof Position) {
				handlePosition((Position) object, group);
			}
			if (useCatalog) {
				if (object instanceof Scale) {
					handleScale((Scale) object, group);
				}
			}
		}

		SoText textNode = new SoText(group, getNextId());
		textNode.setHeight(_text.getHeight() * scaleFactor);
		textNode.setFontName(_text.getFont());
		textNode.setValue(_text.getString());

		text.setValue(_text.getString());
		text.setDependanteAttribute(_text.getDependantAttribute());

		group.getPosition().setEnabled(textPositionEnabled);
		group.addNode(textNode);

		issueContext.removeFirst();
	}

	protected void handleDrawingBorder(DrawingBorder _border, org.dexpi.xpview.model.DrawingBorder border,
			SoGroup parentNode) {
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
				handleGenericAttributes((GenericAttributes) object, propData);
			}
		}
	}

	protected SoTransformation createSoGroup(Thing thing, SoGroup parentNode, boolean createCatalogElement) {
		SoTransformation plantItemNode = null;
		if (createCatalogElement == false) {
			if (useCatalog) {
				org.dexpi.xpview.model.PlantItem plantItemCatalog = plant.getCatalogList().findPlantItem(
						thing.getName());
				if (plantItemCatalog != null) {
					plantItemNode = (SoTransformation) repManager.getNode(plantItemCatalog).clone(parentNode,
							repManager);
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

	protected void handleNozzle(Nozzle _nozzle, PlantItemContainer parent, SoGroup parentNode, boolean readGraphics,
			boolean fromCatalog) {

		issueContext.addFirst(_nozzle);

		// engineering data
		org.dexpi.xpview.model.Nozzle nozzle = new org.dexpi.xpview.model.Nozzle(_nozzle.getComponentName(),
				_nozzle.getTagName(), parent);

		// graphics
		SoTransformation group = createSoGroup(nozzle, parentNode, fromCatalog);

		handlePlantItem(_nozzle, nozzle, fromCatalog, readGraphics, group);
		parent.addPlantItem(nozzle);
		
		group.getPosition().setEnabled(equipmentPositionEnabled);

		issueContext.removeFirst();
	}

	protected void handleComponent(Component _component, PlantItemContainer parent, SoTransformation parentNode,
			boolean readGraphics, boolean fromCatalog) {

		issueContext.addFirst(_component);

		// engineering data
		org.dexpi.xpview.model.Component component = new org.dexpi.xpview.model.Component(
				_component.getComponentName(), parent);

		// graphics
		SoTransformation group = createSoGroup(component, parentNode, fromCatalog);

		handlePlantItem(_component, component, fromCatalog, readGraphics, group);
		parent.addPlantItem(component);

		group.getPosition().setEnabled(equipmentPositionEnabled);

		issueContext.removeFirst();
	}

	protected void handleEquipment(Equipment _equipment, PlantItemContainer parent, SoGroup parentNode,
			boolean readGraphics, boolean fromCatalog) {

		issueContext.addFirst(_equipment);

		// graphics
		SoTransformation equipmentNode = new SoTransformation(plantNode, getNextId(), _equipment.getTagName());
		equipmentNode.setSelectable(false);
		plantNode.addNode(equipmentNode);
		
		// engineering data
		org.dexpi.xpview.model.Equipment equipment = new org.dexpi.xpview.model.Equipment(
				_equipment.getComponentName(), _equipment.getTagName(), parent);
		
		SoTransformation group = createSoGroup(equipment, equipmentNode, fromCatalog);

		handlePlantItem(_equipment, equipment, fromCatalog, readGraphics, group);
		parent.addPlantItem(equipment);
		group.getPosition().setEnabled(equipmentPositionEnabled);

		// get all nozzles and subequipments
		for (Object object : _equipment.getDisciplineOrMinimumDesignPressureOrMaximumDesignPressure()) {
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
	
	protected void handleInstrumentComponent(InstrumentComponent _instrumentComponent, PlantItemContainer parent, SoGroup parentNode,
			boolean readGraphics, boolean fromCatalog) {

		issueContext.addFirst(_instrumentComponent);

		// graphics
		SoTransformation instrumentNode = new SoTransformation(plantNode, getNextId(), _instrumentComponent.getTagName());
		instrumentNode.setSelectable(false);
		plantNode.addNode(instrumentNode);
		
		// engineering data
		org.dexpi.xpview.model.Instrument instrument = new org.dexpi.xpview.model.Instrument(
				_instrumentComponent.getComponentName(), _instrumentComponent.getTagName(), parent);
		
		SoTransformation group = createSoGroup(instrument, instrumentNode, fromCatalog);

		handlePlantItem(_instrumentComponent, instrument, fromCatalog, readGraphics, group);
		parent.addPlantItem(instrument);
		group.getPosition().setEnabled(equipmentPositionEnabled);

		// get sub instruments
		for (Object object : _instrumentComponent.getInstrumentComponentOrComponentOrNominalDiameter()) {
			if (object instanceof Equipment) {
				handleInstrumentComponent((InstrumentComponent) object, instrument, instrumentNode, readGraphics, fromCatalog);
			}
		}

		plant.getItems().add(instrument);

		issueContext.removeFirst();
	}

	protected void handlePosition(List<Object> elements, SoTransformation group) {
		for (Object object : elements) {
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

	protected void handlePlantItemPosition(PlantItem _plantItem, SoTransformation group) {
		handlePosition(_plantItem.getPresentationOrExtentOrPersistentID(), group);
	}

	protected void handleLabelPosition(Label _label, SoTransformation group) {
		handlePosition(_label.getPresentationOrExtentOrPersistentID(), group);
	}

	protected void handlePlantItem(PlantItem _plantItem, org.dexpi.xpview.model.PlantItem plantItem,
			boolean fromCatalog, boolean readGraphics, SoTransformation plantItemNode) {

		PropertyData propData = plantItem.getPropertyData();

		if (fromCatalog == false) {
			plantItem.setClassName(getStringValue(_plantItem, "ComponentClass", _plantItem.getComponentClass()));
			plantItem.setTagName(getStringValue(_plantItem, "TagName", _plantItem.getTagName()));
		}

		if (readGraphics) {
			handlePlantItemGraphics(_plantItem, plantItem, plantItemNode);
			handlePlantItemText(_plantItem, plantItem, plantItemNode);
		}
		handlePlantItemPosition(_plantItem, plantItemNode);		

		for (Object object : _plantItem.getPresentationOrExtentOrPersistentID()) {
			if (object instanceof ConnectionPoints) {
				handleConnectionPoints((ConnectionPoints) object, plantItem, plantItemNode);
			}
		}
		
		handleAttributes(_plantItem, propData.addPropertyList("Default"));
		handleGenericAttributes(_plantItem, propData);
	}

	protected void handleText(List<Object> elements, TextBag textBag, SoTransformation group) {
		for (Object object : elements) {
			if (object instanceof Text) {
				handleText((Text) object, group, textBag);
			}
		}
	}

	protected void handleLabelText(Label _label, org.dexpi.xpview.model.Label label, SoTransformation group) {
		handleText(_label.getPresentationOrExtentOrPersistentID(), label, group);
	}

	protected void handlePlantItemText(PlantItem _plantItem, org.dexpi.xpview.model.PlantItem plantItem,
			SoTransformation group) {
		handleText(_plantItem.getPresentationOrExtentOrPersistentID(), plantItem, group);
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

	protected void handleLabelGraphics(Label _label, org.dexpi.xpview.model.Label label, SoTransformation group) {
		handleCurves(_label.getPresentationOrExtentOrPersistentID(), group);
	}

	protected void handlePlantItemGraphics(PlantItem _plantItem, org.dexpi.xpview.model.PlantItem plantItem,
			SoTransformation group) {
		handleCurves(_plantItem.getPresentationOrExtentOrPersistentID(), group);
	}

	protected void handleAnnotationItemGraphics(AnnotationItem _annotationItem, SoTransformation group) {
		handleCurves(_annotationItem.getPresentationOrExtentOrPersistentID(), group);
	}

	protected void handleGenericAttributes(GenericAttributes genAttrs, PropertyData propertyData) {

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
				propList.addProperty(PropertyType.GENERIC, genAttr.getName(), genAttr.getValue(), genAttr.getFormat(),
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
	protected void handleGenericAttributes(PlantItem _plantItem, PropertyData propertyData) {
		for (Object object : _plantItem.getPresentationOrExtentOrPersistentID()) {
			if (object instanceof GenericAttributes) {
				handleGenericAttributes((GenericAttributes) object, propertyData);
			}
		}
	}

	protected void handleGenericAttributes(AnnotationItem _annotationItem, PropertyData propertyData) {
		for (Object object : _annotationItem.getPresentationOrExtentOrPersistentID()) {
			if (object instanceof GenericAttributes) {
				handleGenericAttributes((GenericAttributes) object, propertyData);
			}
		}
	}

	protected void handleGenericAttributes(Drawing _drawing, PropertyData propertyData) {
		for (Object object : _drawing.getComponentOrCurveOrText()) {
			if (object instanceof GenericAttributes) {
				handleGenericAttributes((GenericAttributes) object, propertyData);
			}
		}
	}

	protected void handleGenericAttributes(Text _text, PropertyData propertyData) {
		for (Object object : _text.getPresentationOrExtentOrPosition()) {
			if (object instanceof GenericAttributes) {
				handleGenericAttributes((GenericAttributes) object, propertyData);
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
	protected void handleAttributesInternal(Object object, Class<?> clazz, PropertyList propertyList) {
		if (clazz != null) {
			for (Field field : clazz.getDeclaredFields()) {
				XmlAttribute attribute = (XmlAttribute) field.getAnnotation(XmlAttribute.class);
				if (attribute != null) {
					try {
						field.setAccessible(true);
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
			System.out.println("Unsupported curve type: " + curve.getClass().getName());
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
			circle.init(_trimmedCurve.getCircle().getRadius() * scaleFactor, _trimmedCurve.getStartAngle(),
					_trimmedCurve.getEndAngle());
			handlePosition(_trimmedCurve.getCircle().getPosition(), circle);
			group.addNode(circle);
		}
		if (_trimmedCurve.getEllipse() != null) {
			log.info("Trimmed Ellipse is not implemented!");
		}
	}

	protected void handleConnectionPoints(ConnectionPoints _connectionPoints, Connectable parent, SoGroup group) {
		org.dexpi.xpview.model.ConnectionPoints connections = new org.dexpi.xpview.model.ConnectionPoints(parent);
		for (GenericAttributes genAttrs : _connectionPoints.getGenericAttributes()) {
			handleGenericAttributes(genAttrs, connections.getPropertyData());
		}
		connections.setFlowIn(_connectionPoints.getFlowIn().intValue());
		connections.setFlowOut(_connectionPoints.getFlowOut().intValue());

		handleNodes(_connectionPoints.getNode(), connections, group);
	}

	protected void handleNodes(List<Node> nodes, org.dexpi.xpview.model.ConnectionPoints connections, SoGroup parent) {
		for (Node _node : nodes) {
			org.dexpi.xpview.model.Node node = new org.dexpi.xpview.model.Node(_node.getName());
			node.setFlow(getNodeFlow(_node.getFlow()));
			node.setType(getNodeType(_node.getType()));
			node.setFunction(getNodeFunction(_node.getFunction()));

			connections.getNodes().add(node);

			SoTransformation transform = new SoTransformation(parent, getNextId(), "Node");
			SoMaterial material = new SoMaterial(transform, getNextId(), "NodeColor");
			material.init(255,0,0,255);
			transform.setMaterial(material);
			
			handlePosition(_node.getPositionOrNominalDiameterOrEndPreparation(), transform);
			SoPoint point = new SoPoint(transform, getNextId());
			point.init();
			transform.addNode(point);

			RepresentationManager.getInstance().addNode(transform, node);

			parent.addNode(transform);
		}
	}

	protected org.dexpi.xpview.model.Node.Flow getNodeFlow(String flow) {
		org.dexpi.xpview.model.Node.Flow result = org.dexpi.xpview.model.Node.Flow.Unspecified;
		if (flow != null) {
			if (flow.toLowerCase().equals("in")) {
				result = Flow.In;
			} else if (flow.toLowerCase().equals("out")) {
				result = Flow.Out;
			} else if (flow.toLowerCase().equals("both")) {
				result = Flow.Both;
			}
		}
		return result;
	}

	protected org.dexpi.xpview.model.Node.Type getNodeType(String type) {
		org.dexpi.xpview.model.Node.Type result = org.dexpi.xpview.model.Node.Type.Unspecified;
		if (type != null) {
			if (type.toLowerCase().equals("process")) {
				result = Type.Process;
			} else if (type.toLowerCase().equals("signal")) {
				result = Type.Signal;
			}
		}
		return result;
	}

	protected org.dexpi.xpview.model.Node.Function getNodeFunction(String function) {
		org.dexpi.xpview.model.Node.Function result = org.dexpi.xpview.model.Node.Function.Unspecified;
		if (function != null) {
			if (function.toLowerCase().equals("primary")) {
				result = Function.Primary;
			} else if (function.toLowerCase().equals("secondary")) {
				result = Function.Secondary;
			} else if (function.toLowerCase().equals("tap")) {
				result = Function.Tap;
			}
		}
		return result;
	}

	private int getNextId() {
		return repManager.getFreeId();
	}
}
