package org.dexpi.xpview.model.graphics.j2d;

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Creates Font objects and stores them in a Map.
 * In case the same Font is created a second time the already existing Font objects 
 * is returned by the createFont method.
 * 
 * This class creates a default font (Dialog, 12) which is returned in case
 * something fails during font creation.
 * 
 * @author Arndt Teinert
 *
 */
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
	
	/**
	 * Returns a {@link String} which identifies a Font.
	 * 
	 * @param fontName
	 * @param height
	 * @param slantAngle
	 * @param textAngle
	 * @return a string composed from the given values
	 */
	protected String getFontDesc(String fontName, double height, double slantAngle, double textAngle) {
		return fontName + ":" + (int) height + ":" + (int) slantAngle + ":" + (int) textAngle;
	}
	
	/**
	 *  In case the same Font is created a second time the already existing Font objects 
     * is returned by the createFont method.
	 * @param fontName
	 * @param height
	 * @param slantAngle
	 * @param textAngle
	 * @return a valid {@link Font}, in case the creation fails the default font is returned
	 */
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
