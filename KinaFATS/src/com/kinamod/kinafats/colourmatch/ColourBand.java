package com.kinamod.kinafats.colourmatch;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.kinamod.kinafats.KinaFATS;

public class ColourBand {
	private final int colour;
	/** The left and right boundary of the band */
	private float left, right;

	public ColourBand(int colourIn, float leftIn, float thickness) {
		colour = colourIn;
		left = leftIn;
		right = left + thickness;
	}

	public float getLeft() {
		return left;
	}

	public float getRight() {
		return right;
	}

	public void drawBand(Canvas canvas, Paint mPaint) {
		mPaint.setColor(Colour.getColour(colour));
		canvas.drawRect(left, 0, right, KinaFATS.getInstance().getSize().y,
				mPaint);
	}
}
