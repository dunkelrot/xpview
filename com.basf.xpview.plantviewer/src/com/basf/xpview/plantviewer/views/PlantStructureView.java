package com.basf.xpview.plantviewer.views;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.basf.xpview.core.Event;
import com.basf.xpview.core.EventListener;
import com.basf.xpview.core.EventManager;
import com.basf.xpview.core.EventType;
import com.basf.xpview.model.Equipment;
import com.basf.xpview.model.EquipmentList;
import com.basf.xpview.model.EventTypes;
import com.basf.xpview.model.Nozzle;
import com.basf.xpview.model.Plant;
import com.basf.xpview.model.PlantSection;
import com.basf.xpview.plantviewer.adapter.EquipmentAdapter;
import com.basf.xpview.plantviewer.adapter.EquipmentListAdapter;
import com.basf.xpview.plantviewer.adapter.NozzleAdapter;
import com.basf.xpview.plantviewer.adapter.PlantAdapter;
import com.basf.xpview.plantviewer.adapter.PlantSectionAdapter;
import com.basf.xpview.utils.AdapterFactory;
import com.basf.xpview.utils.WorkspaceContentProvider;
import com.basf.xpview.utils.WorkspaceLabelProvider;

public class PlantStructureView extends ViewPart implements EventListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.basf.xpview.plantviewer.views.PlantStructureView";

	protected AdapterFactory factory;
	
	private TreeViewer viewer;
	
	/**
	 * The constructor.
	 */
	public PlantStructureView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		factory = new AdapterFactory();
		
		factory.registerAdapter(new PlantAdapter(), Plant.class);
		factory.registerAdapter(new PlantSectionAdapter(), PlantSection.class);
		factory.registerAdapter(new EquipmentListAdapter(), EquipmentList.class);
		factory.registerAdapter(new EquipmentAdapter(), Equipment.class);
		factory.registerAdapter(new NozzleAdapter(), Nozzle.class);
		
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		// drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new WorkspaceContentProvider(factory));
		viewer.setLabelProvider(new WorkspaceLabelProvider(factory));
		// viewer.setInput(getViewSite());

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "com.basf.xpview.plantviewer.viewer");
		makeActions();
		
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		
		getSite().setSelectionProvider(viewer);
		// getSite().getPage().addSelectionListener(this);
		
		EventManager.getInstance().registerForEvent(new EventType(EventTypes.FileImported), this);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				PlantStructureView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
	}

	private void fillContextMenu(IMenuManager manager) {
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
	}

	private void makeActions() {

	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				// doubleClickAction.run();
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	@Override
	public void onEvent(Event event) {
		if (event.getType().is(EventTypes.FileImported)) {
			viewer.setInput(event.getData());
		}
	}
}