package org.dexpi.xpview.model;

import java.util.ArrayList;
import java.util.List;

public class TextList {

	protected ArrayList<Text> texts;
	protected TextBag textBag;
	
	public TextList(TextBag textBag) {
		this.texts = new ArrayList<Text>();
		this.textBag = textBag;
	}
	
	public List<Text> getTexts() {
		return texts;
	}
	
	public TextBag getTextBag() {
		return textBag;
	}
	
	public void addText(Text text) {
		texts.add(text);
	}
	
}
