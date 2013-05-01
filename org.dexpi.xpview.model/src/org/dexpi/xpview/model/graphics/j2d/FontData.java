package org.dexpi.xpview.model.graphics.j2d;

import java.awt.Font;

public class FontData implements org.dexpi.xpview.model.graphics.FontData {

	private Font font;
	
	public FontData(Font font) {
		this.font = font;
	}
	
	@Override
	public Object getFont() {
		return font;
	}
	
	public void setFont(Object font) {
		this.font = (Font) font;
	}
	
}
