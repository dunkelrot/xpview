package org.dexpi.xpview.model.graphics.j2d;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


public class FontFactory {

	private static Logger log = Logger.getLogger(FontFactory.class);
	
	protected Map<String, Font> fontMap;
	protected Font defaultFont;
	protected static final String DEFAULT_FONT = "DEFAULT";
	
	public FontFactory() {
		this.fontMap = new HashMap<String, Font>();
		this.defaultFont = new Font("Dialog", Font.PLAIN, 12);
		fontMap.put(DEFAULT_FONT, defaultFont);
	}
	
	protected String getFontDesc(String fontName, double height, double slantAngle, double textAngle) {
		return fontName + ":" + (int) height + ":" + (int) slantAngle + ":" + (int) textAngle;
	}
	
	public Font createFont(String fontName, double height, double slantAngle, double textAngle) {
		
		Font font = fontMap.get(getFontDesc(fontName, height, slantAngle, textAngle));
		if (font == null) {
			font = fontMap.get(DEFAULT_FONT);
			try {
				font = new Font(fontName, Font.PLAIN, (int) height);
				fontMap.put(getFontDesc(fontName, height, slantAngle, textAngle), font);
			} catch (Exception ex) {
				log.warn("Cannot create font: " + fontName);
			}
		}
		return font;
	}
	
	void clear() {
		fontMap.clear();
		fontMap.put(DEFAULT_FONT, defaultFont);
	}
	
	
}
