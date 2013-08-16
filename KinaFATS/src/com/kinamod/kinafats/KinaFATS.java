package com.kinamod.kinafats;

import android.app.Application;
import android.graphics.PointF;

public class KinaFATS extends Application {

	private static PointF size;
	private static KinaFATS singleton;

	public static KinaFATS getInstance() {
		if (singleton == null) {
			singleton = new KinaFATS();
		}
		return singleton;
	}

	public void setSize(PointF in) {
		size = in;
	}

	public PointF getSize() {
		return size;
	}
}
