package com.nusyn.license.util;

import java.util.Random;

public class ColorUtil {

	private static Random randomGenerator = new Random();
	
	public static String getRandomColorRGB()
	{
		String color = "";
		int r = randInt(150, 255);
		int g = randInt(150, 255);
		int b = randInt(150, 255);
		color = "rgb(" + r + "," + g + "," + b + ")";
		return color;
	}
	
	private static int randInt(int min, int max) {

	    int randomNum = randomGenerator.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
}

