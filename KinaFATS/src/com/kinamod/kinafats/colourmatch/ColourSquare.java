package com.kinamod.kinafats.colourmatch;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.kinamod.cheerapp.util.CustomisedLogging;

public class ColourSquare {
	CustomisedLogging logger = new CustomisedLogging(getClass().getName());

	private final int colour;
	private boolean inZone = false;
	private int squareRadius = 20;
	private Bitmap myBitmap;
	private PointF position = new PointF();
	private PointF speed = new PointF();

	private ColourSquare(int colourIn, PointF speedIn) {
		colour = colourIn;
		speed = speedIn;
	}

	public void drawSquare(Canvas canvas, Paint mPaint) {
		// final String TAG = "drawSquare";
		mPaint.setColor(Colour.getColour(colour));

		canvas.drawRect(position.x, position.y, position.x + 20,
				position.y + 20, mPaint);
	}

	public void updatePosition(float dTime) {
		// final String TAG = "updatePosition";
		// logger.localDebugLog(1, TAG, "Position:" + position.x + " - "
		// + position.y);
		// logger.localDebugLog(1, TAG, "PositionBefore:\n    " + position.x
		// + " : " + position.y + " - speed: " + speed.x + " : " + speed.y);
		//
		position.set(position.x + speed.x * dTime / 100, position.y + speed.y
				* dTime / 100);

		//
		// logger.localDebugLog(1, TAG, "PositionAfter: " + position.x + " : "
		// + position.y + " - dTime: " + dTime + "\n");
	}

	public static ColourSquare newInstance() {
		int col = (int) (Math.random() * 3) + 1;
		float x = (float) (Math.random() * 3) + 1;
		PointF speed = new PointF(x, 100);
		ColourSquare colSquare = new ColourSquare(col, speed);
		return colSquare;
	}

	public boolean isInZone() {
		return inZone;
	}
}
