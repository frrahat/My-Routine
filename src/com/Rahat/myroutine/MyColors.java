/*
 *               In the name of Allah
 * This file is part of The "Quran Teacher or Learn Arabic" Project. Use is subject to
 * license terms.
 *
 * @author:         Fazle Rabbi Rahat
 * 
 */
package com.Rahat.myroutine;

import android.graphics.Color;


public class MyColors {
	private static int[] colors={
			Color.WHITE,
			Color.CYAN,
			Color.GRAY,
			Color.GREEN,
			Color.MAGENTA,
			Color.YELLOW,
			Color.RED,
			Color.BLUE,
			};
	
	
	private static String[] colorNames={
			"WHITE",
			"CYAN",
			"GRAY",
			"GREEN",
			"MAGENTA",
			"YELLOW",
			"RED",
			"BLUE"};

	
	public static int[] getColors()
	{
		return colors;
	}
	
	public static String[] getColorNames()
	{
		return colorNames;
	}
	
	public static String getColorName(Color color)
	{
		return colorNames[getColorIndex(color)];
	}
	
	
	public static int getColorIndex(Color color)
	{
		for(int i=0;i<colors.length;i++)
			if(color.equals(colors[i]))
				return i;
		
		return 0;
	}
	
	public static int getColorIndex(String colorName)
	{
		for(int i=0;i<colorNames.length;i++)
			if(colorName.equals(colorNames[i]))
				return i;
		
		return 0;
	}
	
	public static int getColorConstant(String colorName){
		return getColorConstant(getColorIndex(colorName));
	}
	
	public static int getColorConstant(int index){
		if(index<colors.length)
			return colors[index];
		else return Color.GREEN;
	}
}
