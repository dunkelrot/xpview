package org.dexpi.xpview.plantviewer.views;


import org.dexpi.xpview.core.Event;
import org.dexpi.xpview.core.EventListener;
import org.dexpi.xpview.core.EventManager;
import org.dexpi.xpview.model.Catalog;
import org.dexpi.xpview.model.CatalogList;
import org.dexpi.xpview.model.Component;
import org.dexpi.xpview.model.Drawing;
import org.dexpi.xpview.model.DrawingBorder;
import org.dexpi.xpview.model.DrawingList;
import org.dexpi.xpview.model.Equipment;
import org.dexpi.xpview.model.EquipmentList;
import org.dexpi.xpview.model.Label;
import org.dexpi.xpview.model.Nozzle;
import org.dexpi.xpview.model.PipeConnector;
import org.dexpi.xpview.model.PipingComponent;
import org.dexpi.xpview.model.PipingNetwork;
import org.dexpi.xpview.model.PipingNetworkList;
import org.dexpi.xpview.model.PipingSegment;
import org.dexpi.xpview.model.Plant;
import org.dexpi.xpview.model.PlantItem;
import org.dexpi.xpview.model.PlantSection;
import org.dexpi.xpview.model.PlantUtils;
import org.dexpi.xpview.model.ProcessInstrument;
import org.dexpi.xpview.model.Text;
import org.dexpi.xpview.model.TextList;
import org.dexpi.xpview.model.events.EventTypes;
import org.dexpi.xpview.model.graphics.RepresentationManager;
import org.dexpi.xpview.model.graphics.SoNode;
import org.dexpi.xpview.pidviewer.editors.PIDEditor;
import org.dexpi.xpview.pidviewer.editors.PIDEditorInput;
import org.dexpi.xpview.plantviewer.adapter.AnnotationItemAdapter;
import org.dexpi.xpview.plantviewer.adapter.CatalogAdapter;
import org.dexpi.xpview.plantviewer.adapter.CatalogListAdapter;
import org.dexpi.xpview.plantviewer.adapter.ComponentAdapter;
import org.dexpi.xpview.plantviewer.adapter.DrawingAdapter;
import org.dexpi.xpview.plantviewer.adapter.DrawingBorderAdapter;
import org.dexpi.xpview.plantviewer.adapter.DrawingListAdapter;
import org.dexpi.xpview.plantviewer.adapter.EquipmentAdapter;
import org.dexpi.xpview.plantviewer.adapter.EquipmentListAdapter;
import org.dexpi.xpview.plantviewer.adapter.NozzleAdapter;
import org.dexpi.xpview.plantviewer.adapter.PipingComponentAdapter;
import org.dexpi.xpview.plantviewer.adapter.PipingNetworkAdapter;
import org.dexpi.xpview.plantviewer.adapter.PipingNetworkListAdapter;
import org.dexpi.xpview.plantviewer.adapter.PipingSegmentAdapter;
import org.dexpi.xpview.plantviewer.adapter.PlantAdapter;
import org.dexpi.xpview.plantviewer.adapter.PlantItemAdapter;
import org.dexpi.xpview.plantviewer.adapter.PlantSectionAdapter;
import org.dexpi.xpview.plantviewer.adapter.TextAdapter;
import org.dexpi.xpview.plantviewer.adapter.TextListAdapter;
import org.dexpi.xpview.utils.AdapterFactory;
import org.dexpi.xpview.utils.ExceptionDialog;
import org.dexpi.xpview.utils.WorkspaceContentProvider;
import org.dexpi.xpview.utils.WorkspaceLabelProvider;
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
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class PlantStructureView extends ViewPart implements EventListener, ISelectionListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.dexpi.xpview.plantviewer.views.PlantStructureView";

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
		factory.registerAdapter(new DrawingBorderAdapter(), DrawingBorder.class);
		factory.registerAdapter(new DrawingListAdapter(), DrawingList.class);
		factory.registerAdapter(new CatalogListAdapter(), CatalogList.class);
		factory.registerAdapter(new CatalogAdapter(), Catalog.class);
		factory.registerAdapter(new ComponentAdapter(), Component.class);
		factory.registerAdapter(new PipingNetworkAdapter(), PipingNetwork.class);
		factory.registerAdapter(new PipingNetworkListAdapter(), PipingNetworkList.class);
		factory.registerAdapter(new PipingSegmentAdapter(), PipingSegment.class);
		factory.registerAdapter(new PipingComponentAdapter(), PipingComponent.class);
		factory.registerAdapter(new PlantItemAdapter(), ProcessInstrument.class);
		factory.registerAdapter(new AnnotationItemAdapter(), PipeConnector.class);
		factory.registerAdapter(new AnnotationItemAdapter(), Label.class);
		factory.registerAdapter(new TextListAdapter(), TextList.class);
		factory.registerAdapter(new TextAdapter(), Text.class);
		
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		// drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new WorkspaceContentProvider(factory));
		viewer.setLabelProvider(new WorkspaceLabelProvider(factory));
		// viewer.setInput(getViewSite());

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "org.dexpi.xpview.plantviewer.viewer");
		makeActions();
		
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		
		getSite().setSelectionProvider(viewer);
		getSite().getPage().addSelectionListener(this);
		
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

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part instanceof PlantStructureView) {
			// ignored
		} else {
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structSel = (IStructuredSelection) selection;
				if (structSel.isEmpty() == false) {
					Object obj = structSel.getFirstElement();
					if (obj instanceof PlantItem) {
						viewer.reveal(obj);
						viewer.setSelection(selection);
					}
				}
			}
		}
	}
}