package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class PipingSegment extends PlantItem implements PlantItemContainer, AnnotationContainer {

	protected ArrayList<CenterLine> centerLines;
	protected ArrayList<PipingComponent> components;
	protected ArrayList<ProcessInstrument> instruments;
	protected ArrayList<PipeConnector> opcs;
	
	public PipingSegment(String name, PipingNetwork pipingNetwork) {
		super(name, pipingNetwork);
		this.centerLines = new ArrayList<CenterLine>();
		this.components = new ArrayList<PipingComponent>();
		this.instruments = new ArrayList<ProcessInstrument>();
		this.opcs = new ArrayList<PipeConnector>();
	}
	
	public List<CenterLine> getCenterLines() {
		return centerLines;
	}

	@Override
	public void addPlantItem(PlantItem plantItem) {
		if (plantItem instanceof PipingComponent) {
			components.add((PipingComponent) plantItem);
		}
		if (plantItem instanceof ProcessInstrument) {
			instruments.add((ProcessInstrument) plantItem);
		}
	}
	
	public List<PipingComponent> getComponents() {
		return components;
	}
	
	@Override
	public List<PlantItem> getPlantItems() {
		ArrayList<PlantItem> plantItems = new ArrayList<PlantItem>();
		plantItems.addAll(components);
		plantItems.addAll(instruments);
		return plantItems;
	}
	
	public List<ProcessInstrument> getInstruments() {
		return instruments;
	}

	@Override
	public void addAnnotation(AnnotationItem annotationItem) {
		if (annotationItem instanceof PipeConnector) {
			opcs.add((PipeConnector) annotationItem);
		}
	}

	@Override
	public List<? extends AnnotationItem> getAnnotations() {
		return opcs;
	}
	
}
