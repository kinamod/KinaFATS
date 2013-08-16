package com.kinamod.kinafats.colourmatch;

import java.util.ArrayList;

import android.graphics.Color;

public class Colour {
	static ArrayList<Integer> colours = new ArrayList<Integer>();
	static {
		colours.add(Color.RED);
		colours.add(Color.GREEN);
		colours.add(Color.BLUE);
	}

	protected static int getColour(int in) {
		return colours.get(in);
	}

}
