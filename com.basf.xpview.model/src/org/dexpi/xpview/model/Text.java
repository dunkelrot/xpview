package com.basf.xpview.model;

public class Text extends Thing {

	protected String value;
	protected String set;
	
	protected String itemReferenceID;
	
	public Text() {
		super(Text.class.getSimpleName());
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public String getItemReferenceID() {
		return itemReferenceID;
	}

	public void setItemReferenceID(String itemReferenceID) {
		this.itemReferenceID = itemReferenceID;
	}

}
