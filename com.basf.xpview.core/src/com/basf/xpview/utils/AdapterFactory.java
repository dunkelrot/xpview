package com.basf.xpview.utils;

import java.util.HashMap;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * Utility class for handling IWorkbenchAdapter implementations.
 * 
 * @author Arndt Teinert
 *
 */
public class AdapterFactory implements IAdapterFactory {

	HashMap<Class<?>, IWorkbenchAdapter> adapters;

	public AdapterFactory() {
		this.adapters = new HashMap<Class<?>, IWorkbenchAdapter>();
	}

	public void registerAdapter(IWorkbenchAdapter adapter, Class<?> classOfObject) {
		registerAdapter(adapter, classOfObject, true);
	}

	public void registerAdapter(IWorkbenchAdapter adapter, Class<?> classOfObject, boolean withPlatform) {
		adapters.put(classOfObject, adapter);
		if (withPlatform) {
			Platform.getAdapterManager().registerAdapters(this, classOfObject);
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public Class[] getAdapterList() {
		return new Class[] { IWorkbenchAdapter.class };
	}

	@SuppressWarnings({ "rawtypes" })
	public Object getAdapter(Object adaptableObject, Class adapterType) {

		Object result = null;
		for (Class classOfObject : adapters.keySet()) {
			if (classOfObject.equals(adaptableObject.getClass())) {
				result = adapters.get(classOfObject);
				if (adapterType == IWorkbenchAdapter.class && classOfObject.isInstance(adaptableObject)) {
					break;
				}
			}
		}
		return result;
	}

}
