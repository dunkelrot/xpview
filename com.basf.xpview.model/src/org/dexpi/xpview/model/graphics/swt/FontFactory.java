package com.basf.xpview.model.graphics.swt;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Font;

import com.basf.xpview.model.graphics.SoText;


public class FontFactory {

	private static Logger log = Logger.getLogger(FontFactory.class);
	
	protected Map<String, Font> fontMap;
	protected Font defaultFont;
	protected static final String DEFAULT_FONT = "DEFAULT";
	
	public FontFactory() {
		this.fontMap = new HashMap<String, Font>();
		// defaultFont = new Font("Dialog", Font.PLAIN, 12);
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
				// font = Font.createFont(Font.TRUETYPE_FONT, new File(fontName + ".ttf"));
				// font = font.deriveFont((float) height);
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
	
	public void createFont(SoText node) {
		
	}
}
