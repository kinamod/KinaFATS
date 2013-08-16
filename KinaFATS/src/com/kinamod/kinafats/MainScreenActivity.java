package com.kinamod.kinafats;

import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;

import com.kinamod.cheerapp.util.CustomisedLogging;
import com.kinamod.kinafats.canvases.SquareMatchCanvas;
import com.kinamod.kinafats.colourmatch.ColourSquare;

public class MainScreenActivity extends Activity {

	CustomisedLogging logger = new CustomisedLogging(getClass().getName());
	/** Is each "click" of the game, used for timing and animation */
	ScheduledExecutorService gameClicker;
	/** generate squares on a schedule */
	ScheduledExecutorService squareGenerator;
	private static MainScreenActivity MAIN_SCREEN_ACTIVITY;
	Thread clickGameThread, generateNewSquareThread;
	LinkedList<ColourSquare> colourSquares = new LinkedList<ColourSquare>();
	private long lastUpdate;
	SquareMatchCanvas squareMatchCanvas;
	private KinaFATS kinaFATS = KinaFATS.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MAIN_SCREEN_ACTIVITY = this;
		// RelativeLayout rLayout = (RelativeLayout)
		// findViewById(R.layout.activity_main_screen);
		squareMatchCanvas = new SquareMatchCanvas(MAIN_SCREEN_ACTIVITY);
		// rLayout.addView(squareMatchCanvas);
		//
		// setContentView(rLayout);
		setContentView(squareMatchCanvas);
		squareMatchCanvas.setZOrderOnTop(true); // necessary
	}

	@Override
	protected void onResume() {
		super.onResume();

		createGameThreads();
		createPoolExecutors();
		createGameObjects();
		// startGame(); now called from canvas callback OnCreated
	}

	private void createPoolExecutors() {
		gameClicker = new ScheduledThreadPoolExecutor(1);
		squareGenerator = new ScheduledThreadPoolExecutor(1);
	}

	private void createGameThreads() {
		generateNewSquareThread = new Thread("Square Generator") {
			public void run() {
				colourSquares.add(ColourSquare.newInstance());
			}
		};
		clickGameThread = new Thread("Game Clicker") {
			public void run() {
				updatePositions();
				squareMatchCanvas.requestDraw();
				logger.quickLog("clicker", "clicker called");
			}
		};

	}

	public void startGame() {
		lastUpdate = System.nanoTime();
		gameClicker.scheduleAtFixedRate(clickGameThread, 0, 1000 / 300,
				TimeUnit.MILLISECONDS);

	}

	private void createGameObjects() {
		calcDimensions();
		makeSquareGenerator();
		colourSquares.add(ColourSquare.newInstance());

	}

	@SuppressLint("NewApi")
	private void calcDimensions() {
		Point size = new Point();
		if (Build.VERSION.SDK_INT >= 13) {
			Display display = getWindowManager().getDefaultDisplay();
			display.getSize(size);

		} else {
			Display display = getWindowManager().getDefaultDisplay();
			size = new Point(display.getWidth(), display.getHeight()); // depreciated
																		// in
																		// 13
		}
		kinaFATS.setSize(new PointF(size));
	}

	private void makeSquareGenerator() {
		squareGenerator.scheduleAtFixedRate(generateNewSquareThread, 0, 2,
				TimeUnit.SECONDS);
	}

	private void updatePositions() {
		float dTime = getDeltaTime();
		for (ColourSquare thisSquare : colourSquares) {
			logger.quickLog("updatePos", "dTime: " + dTime);
			thisSquare.updatePosition(dTime);
		}

	}

	private float getDeltaTime() {
		final float last = lastUpdate;
		lastUpdate = System.nanoTime() / 1000000;
		return lastUpdate - last;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}

	public static MainScreenActivity getInstance() {
		return MAIN_SCREEN_ACTIVITY;
	}

	public void drawSquares(Canvas canvas, Paint mPaint) {
		for (ColourSquare thisSquare : colourSquares) {
			thisSquare.drawSquare(canvas, mPaint);
		}
	}

	protected void onPause() {
		super.onPause();
		gameClicker.shutdown();
		squareGenerator.shutdown();
	}

}
