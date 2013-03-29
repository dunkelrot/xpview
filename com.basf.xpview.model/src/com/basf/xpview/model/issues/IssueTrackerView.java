package com.basf.xpview.model.issues;

import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import com.basf.xpview.core.Event;
import com.basf.xpview.core.EventListener;
import com.basf.xpview.core.EventManager;
import com.basf.xpview.model.events.EventTypes;
import com.basf.xpview.model.issues.adapter.IssueGroup;
import com.basf.xpview.model.issues.adapter.IssueGroupAdapter;
import com.basf.xpview.model.issues.adapter.IssueListAdapter;
import com.basf.xpview.model.issues.adapter.IssueTrackerAdapter;
import com.basf.xpview.model.issues.adapter.IssueWrapper;
import com.basf.xpview.model.issues.adapter.IssueWrapperAdapter;
import com.basf.xpview.utils.AdapterFactory;
import com.basf.xpview.utils.WorkspaceContentProvider;
import com.basf.xpview.utils.WorkspaceLabelProvider;

public class IssueTrackerView extends ViewPart implements EventListener, ISelectionChangedListener, ISelectionListener {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.basf.xpview.model.issues.IssueTrackerView";

	protected TreeViewer viewer;
	protected AdapterFactory factory;
	
	@Override
	public void onEvent(Event event) {
		if (event.getType().is(EventTypes.IssueListCreated)) {
			viewer.refresh(IssueTracker.getInstance());
		}
		if (event.getType().is(EventTypes.IssueListRemoved)) {
			viewer.refresh(IssueTracker.getInstance());
		}
		if (event.getType().is(EventTypes.IssueListRemovedAll)) {
			viewer.refresh(IssueTracker.getInstance());
		}
		if (event.getType().is(EventTypes.IssueReported)) {
			viewer.refresh(event.getSource());
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
		
		factory.registerAdapter(new IssueWrapperAdapter(), IssueWrapper.class);
		factory.registerAdapter(new IssueListAdapter(), IssueList.class);
		factory.registerAdapter(new IssueTrackerAdapter(), IssueTracker.class);
		factory.registerAdapter(new IssueGroupAdapter(), IssueGroup.class);
		
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
		
		viewer.setContentProvider(new WorkspaceContentProvider(factory));
		viewer.setLabelProvider(new WorkspaceLabelProvider(factory));
		
		Tree tree = viewer.getTree();
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		
		TableLayout layout = new TableLayout();

		createTreeColumn(tree, "Description", 300, layout);
		createTreeColumn(tree, "Source", 100, layout);
		createTreeColumn(tree, "Type", 100, layout);
		
		tree.setLayout(layout); 
		
		getSite().getPage().addSelectionListener(this);
		
		EventManager.getInstance().registerForEvent(EventTypes.IssueListCreated, this);
		EventManager.getInstance().registerForEvent(EventTypes.IssueListRemoved, this);
		EventManager.getInstance().registerForEvent(EventTypes.IssueListRemovedAll, this);
		EventManager.getInstance().registerForEvent(EventTypes.IssueReported, this);
		
		hookDoubleClickAction();
		
		viewer.setInput(IssueTracker.getInstance());
	}

	@Override
	public void setFocus() {
		viewer.getTree().setFocus();
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {

	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {

	}

	private void hookDoubleClickAction() {
		
	}
	
}
