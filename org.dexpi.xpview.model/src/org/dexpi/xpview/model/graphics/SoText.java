package com.basf.xpview.model.graphics;

import com.basf.xpview.model.io.xmplant.Text;

public class SoText extends SoNodeT {

	protected String fontName;
	
	protected double height;
	protected double width;
	
	protected double slantAngle;
	protected double textAngle;
	
	protected String value;
	protected FontData fontData;
	
	public SoText(SoNode parent, int id) {
		super(parent, id, Text.class.getSimpleName());
	}
	
	public SoText(SoText other, SoNode parent, IDProvider idProvider) {
		super(other, parent, idProvider);
		this.fontName = other.fontName;
		this.height = other.height;
		this.width = other.width;
		this.slantAngle = other.slantAngle;
		this.textAngle = other.textAngle;
	}
	
	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getSlantAngle() {
		return slantAngle;
	}

	public void setSlantAngle(double slantAngle) {
		this.slantAngle = slantAngle;
	}

	public double getTextAngle() {
		return textAngle;
	}

	public void setTextAngle(double textAngle) {
		this.textAngle = textAngle;
	}

	@Override
	public SoNode clone(SoNode parent, IDProvider idProvider) {
		return new SoText(this, parent, idProvider);
	}
	
	@Override
	public BoundingBox getBoundingBox() {
		return super.getBoundingBox();
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public FontData getFontData() {
		return fontData;
	}
	
	public void setFontData(FontData fontData) {
		this.fontData = fontData;
	}
	
	@Override
	public void scale(double x, double y) {
		// ignored
	}
}
