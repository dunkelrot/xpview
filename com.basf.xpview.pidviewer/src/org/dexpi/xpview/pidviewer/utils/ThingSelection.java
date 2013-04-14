package com.basf.xpview.pidviewer.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;

import com.basf.xpview.model.graphics.RepresentationManager;
import com.basf.xpview.model.graphics.SoNode;
import com.basf.xpview.model.graphics.SoSelectionManager;

public class ThingSelection implements IStructuredSelection {
	
	protected ArrayList<Object> selectedThings;
	
	public ThingSelection(SoSelectionManager selectionManager, RepresentationManager repManager) {
		this.selectedThings = new ArrayList<Object>();
		for (SoNode node : selectionManager.getSelectedNodes()) {
			Object obj = repManager.getObject(node);
			selectedThings.add(obj);
		}
	}
	
	@Override
	public boolean isEmpty() {
		return selectedThings.isEmpty();
	}

	@Override
	public Object getFirstElement() {
		return selectedThings.get(0);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Iterator iterator() {
		return selectedThings.iterator();
	}

	@Override
	public int size() {
		return selectedThings.size();
	}

	@Override
	public Object[] toArray() {
		return selectedThings.toArray();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List toList() {
		return selectedThings;
	}

}
