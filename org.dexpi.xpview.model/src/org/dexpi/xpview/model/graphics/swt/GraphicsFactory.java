package org.dexpi.xpview.model.graphics.swt;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Path;

public class GraphicsFactory {

	static public Path createCircle(Device device, float radius) {
		Path path = new Path(device);
		path.addArc(-radius, -radius, radius * 2, radius * 2, 0, 360);
		return path;
	}
	
	static public Path createArc(Device device, float radiusX, float radiusY, float startAngle, float endAngle) {
		Path path = new Path(device);
		path.addArc(-radiusX, -radiusY, radiusX * 2, radiusY * 2, startAngle, endAngle);
		return path;
	}
	
}
