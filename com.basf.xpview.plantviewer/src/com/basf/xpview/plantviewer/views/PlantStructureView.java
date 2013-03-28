package com.basf.xpview.plantviewer.views;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.basf.xpview.core.Event;
import com.basf.xpview.core.EventListener;
import com.basf.xpview.core.EventManager;
import com.basf.xpview.model.CatalogList;
import com.basf.xpview.model.Drawing;
import com.basf.xpview.model.DrawingList;
import com.basf.xpview.model.Equipment;
import com.basf.xpview.model.EquipmentList;
import com.basf.xpview.model.Nozzle;
import com.basf.xpview.model.Plant;
import com.basf.xpview.model.PlantSection;
import com.basf.xpview.model.PlantUtils;
import com.basf.xpview.model.events.EventTypes;
import com.basf.xpview.model.graphics.RepresentationManager;
import com.basf.xpview.model.graphics.SoNode;
import com.basf.xpview.pidviewer.editors.PIDEditor;
import com.basf.xpview.pidviewer.editors.PIDEditorInput;
import com.basf.xpview.plantviewer.adapter.CatalogListAdapter;
import com.basf.xpview.plantviewer.adapter.DrawingAdapter;
import com.basf.xpview.plantviewer.adapter.DrawingListAdapter;
import com.basf.xpview.plantviewer.adapter.EquipmentAdapter;
import com.basf.xpview.plantviewer.adapter.EquipmentListAdapter;
import com.basf.xpview.plantviewer.adapter.NozzleAdapter;
import com.basf.xpview.plantviewer.adapter.PlantAdapter;
import com.basf.xpview.plantviewer.adapter.PlantSectionAdapter;
import com.basf.xpview.utils.AdapterFactory;
import com.basf.xpview.utils.ExceptionDialog;
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
		factory.registerAdapter(new DrawingAdapter(), Drawing.class);
		factory.registerAdapter(new DrawingListAdapter(), DrawingList.class);
		factory.registerAdapter(new CatalogListAdapter(), CatalogList.class);
		
		
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
		
		EventManager.getInstance().registerForEvent(EventTypes.FileImported, this);
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
				ISelection selection = viewer.getSelection();
				if (selection != null && selection instanceof IStructuredSelection) {
					Object obj = ((IStructuredSelection) selection).getFirstElement();
					if (obj != null && obj instanceof Drawing) {
						Plant plant = PlantUtils.getPlant((Drawing) obj);
						SoNode node = RepresentationManager.getInstance().getNode(plant);
						IEditorInput input = new PIDEditorInput(node);
						try {
							IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
							page.openEditor(input, PIDEditor.ID);
						} catch (PartInitException ex) {
							ExceptionDialog.openException(ex);
						}
					}
				}
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