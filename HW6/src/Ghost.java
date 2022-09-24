/*
 * Author: Tyler Gutowski, tgutowski2020@fit.edu or tygutowski@gmail.com
 * Course: CSE 2010, Section 01, Fall 2021
 * Project: Proj 06, hw6S12GroupHelp
 */
public class Ghost implements Comparable<Object> {
	// Private variables for icon, x and y values
	private char icon = ' ';
	private int x = 0;
	private int y = 0;

	// When instancing a new item, set the values
	public Ghost(char c, int i, int j) {
		icon = c;
		x = i;
		y = j;
	}

	// public method to return icon as a character
	public char getIcon() {
		return icon;
	}

	// public method to return x value
	public int getX() {
		return x;
	}

	// public method to return y value.
	public int getY() {
		return y;
	}

	// Public method to set the icon.
	public void setIcon(char c) {
		icon = c;
	}

	// We do not need setters for the X and Y values, because each
	// turn we are deleting the ghost list and creating a new one.
	@Override
	public int compareTo(Object ghost) {
		char compareChar = ((Ghost) ghost).getIcon();
		return this.icon - compareChar;
	}
}
