package org.dexpi.xpview.model;

public class Node implements PropertyProvider {

	public enum Flow {
		Unspecified,
		Both,
		In,
		Out
	}
	
	public enum Type {
		Unspecified,
		Process,
		Signal
	}
	
	public enum Function {
		Unspecified,
		Primary,
		Secondary,
		Tap
	}
	
	protected String name;
	
	protected Flow flow;
	protected Type type;
	protected Function function;
	
	protected PropertyData propertyData;
	protected PropertyList defaultProps;
	protected Property flowProp;
	protected Property typeProp;
	protected Property functionProp;
	
	public Node(String name) {
		this.name = name;
		this.flow = Flow.Unspecified;
		this.type = Type.Unspecified;
		this.function = Function.Unspecified;
		
		this.propertyData = new PropertyData(this);
		defaultProps = propertyData.createPropertyList("Default");
		flowProp = new Property(defaultProps, PropertyType.BASE, "Flow");
		typeProp = new Property(defaultProps, PropertyType.BASE, "Type");
		functionProp = new Property(defaultProps, PropertyType.BASE, "Function");
		
		defaultProps.addProperty(flowProp);
		defaultProps.addProperty(typeProp);
		defaultProps.addProperty(functionProp);
	}

	public Flow getFlow() {
		return flow;
	}
	
	public Function getFunction() {
		return function;
	}
	
	public String getName() {
		return name;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setFlow(Flow flow) {
		this.flow = flow;
		flowProp.setValue(flow.toString());
	}
	
	public void setFunction(Function function) {
		this.function = function;
		functionProp.setValue(function.toString());
	}
	
	public void setType(Type type) {
		this.type = type;
		typeProp.setValue(type.toString());
	}
	
	@Override
	public PropertyData getPropertyData() {
		return propertyData;
	}
	
}
