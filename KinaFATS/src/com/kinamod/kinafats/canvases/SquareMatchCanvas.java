package com.kinamod.kinafats.canvases;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.kinamod.cheerapp.util.CustomisedLogging;
import com.kinamod.kinafats.MainScreenActivity;

public class SquareMatchCanvas extends SurfaceView implements
		SurfaceHolder.Callback {
	CustomisedLogging logger = new CustomisedLogging(getClass().getName());
	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	Random random;
	int i = 0;

	public SquareMatchCanvas(Context context) {
		super(context);
		setZOrderOnTop(true);
		getHolder().addCallback(this);
		makePaint();
		random = new Random();
	}

	private void makePaint() {
		mPaint.setTextSize(40);
	}

	@Override
	public void onDraw(Canvas canvas) {

	}

	private void drawEverything() {
		final Canvas canvas = getHolder().lockCanvas();
		if (canvas == null) {
			return;// TODO
		}
		logger.quickLog("drawEverything", "Canvas Locked");
		final String TAG = "onDraw";
		canvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
		mPaint.setColor(Color.argb(255, 0, 0, 0));
		canvas.drawText("Hello", 150, 100, mPaint);
		mPaint.setColor(Color.MAGENTA);
		canvas.drawCircle(200, 200, 200, mPaint);

		// //SOME MORWE STUFF
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(3);

		int w = canvas.getWidth();
		int h = canvas.getHeight();
		int x = random.nextInt(w - 1);
		int y = random.nextInt(h - 1);
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);
		mPaint.setColor(0xff000000 + (r << 16) + (g << 8) + b);
		canvas.drawPoint(x, y, mPaint);
		// SOME MORE STUFF ENDS

		MainScreenActivity.getInstance().drawSquares(canvas, mPaint);
		// invalidate();
		getHolder().unlockCanvasAndPost(canvas);
	}

	public void requestDraw() {
		// onDraw(canvas);
		// invalidate();
		drawEverything();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// MainScreenActivity.getInstance().startGame();
		Thread hello = new Thread() {
			public void run() {
				// while (i < 200) {
				// drawEverything();
				// i++;
				// if (i % 100 == 0) {
				// logger.quickLog("i", "int: " + i);
				// }
				// }
				MainScreenActivity.getInstance().startGame();
			}
		};
		hello.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

}
