package com.basf.xpview.plantviewer.views;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.basf.xpview.core.Event;
import com.basf.xpview.core.EventListener;
import com.basf.xpview.core.EventManager;
import com.basf.xpview.model.Property;
import com.basf.xpview.model.PropertyData;
import com.basf.xpview.model.PropertyItem;
import com.basf.xpview.model.PropertyList;
import com.basf.xpview.model.events.EventTypes;
import com.basf.xpview.plantviewer.adapter.PropertyAdapter;
import com.basf.xpview.plantviewer.adapter.PropertyDataAdapter;
import com.basf.xpview.plantviewer.adapter.PropertyListAdapter;
import com.basf.xpview.plantviewer.editors.URIEditor;
import com.basf.xpview.plantviewer.editors.URIEditorInput;
import com.basf.xpview.utils.AdapterFactory;
import com.basf.xpview.utils.ExceptionDialog;
import com.basf.xpview.utils.WorkspaceContentProvider;
import com.basf.xpview.utils.WorkspaceLabelProvider;

public class PropertyView extends ViewPart implements EventListener, ISelectionChangedListener, ISelectionListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.basf.xpview.plantviewer.views.PropertyView";

	protected TreeViewer viewer;
	protected AdapterFactory factory;
	
	@Override
	public void onEvent(Event event) {
		if (event.getType().is(EventTypes.FileImported)) {
			viewer.setInput(null);
		}
	}

	protected void createTreeColumn(Tree tree, String name, int size, TableLayout layout) {
		TreeColumn treeColumn = new TreeColumn(tree, SWT.LEFT);
		treeColumn.setText(name);
		layout.addColumnData(new ColumnWeightData(size));
	}
	
	@Override
	public void createPartControl(Composite parent) {
		factory = new AdapterFactory();
		
		factory.registerAdapter(new PropertyAdapter(), Property.class);
		factory.registerAdapter(new PropertyDataAdapter(), PropertyData.class);
		factory.registerAdapter(new PropertyListAdapter(), PropertyList.class);
		
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		
		viewer.setContentProvider(new WorkspaceContentProvider(factory));
		viewer.setLabelProvider(new WorkspaceLabelProvider(factory));
		
		Tree tree = viewer.getTree();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		
		TableLayout layout = new TableLayout();

		createTreeColumn(tree, "Name", 100, layout);
		createTreeColumn(tree, "Value", 50, layout);
		createTreeColumn(tree, "Format", 30, layout);
		createTreeColumn(tree, "URI", 80, layout);
		
		tree.setLayout(layout); 
		
		getSite().getPage().addSelectionListener(this);
		
		EventManager.getInstance().registerForEvent(EventTypes.FileImported, this);
		
		hookDoubleClickAction();
		
		selectionChanged(this, getSite().getPage().getSelection());
	}

	@Override
	public void setFocus() {
		
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {

	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		Object input = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structSel = (IStructuredSelection) selection;
			Object obj = structSel.getFirstElement();
			if (obj instanceof PropertyItem) {
				input = ((PropertyItem) obj).getPropertyData();
			}
		}
		viewer.setInput(input);
		viewer.expandToLevel(2);
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = viewer.getSelection();
				if (selection != null && selection instanceof IStructuredSelection) {
					Object obj = ((IStructuredSelection) selection).getFirstElement();
					// If we had a selection with a URI lets open the editor
					if (obj != null && obj instanceof Property) {
						if (((Property)obj).hasURI()) {
							IEditorInput input = new URIEditorInput((Property) obj);
							try {
								IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
								page.openEditor(input, URIEditor.ID);
							} catch (PartInitException ex) {
								ExceptionDialog.openException(ex);
							}
						}
					}
				}
			}
		});
	}
}
