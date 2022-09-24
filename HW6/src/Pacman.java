/*
 * Author: Tyler Gutowski, tgutowski2020@fit.edu or tygutowski@gmail.com
 * Course: CSE 2010, Section 01, Fall 2021
 * Project: Proj 06, hw6S12GroupHelp
 */
public class Pacman {
	// I could make this a singleton, because I only want a single pacman.
	// But I simply wont instantiate more than one pacman.
	// Private variables for x, y and point values.
	private int y = 0;
	private int x = 0;
	private int points = 0;

	// Constructor to initialize values when instancing a new pacman.
	public Pacman(int i, int j, int p) {
		y = i;
		x = j;
		points = p;
	}

	// Public getter for the x value, returns an integer.
	public int getX() {
		return x;
	}

	// Public getter for the y value, returns an integer.
	public int getY() {
		return y;
	}

	// Public getter for the points value, returns an integer.
	public int getPoints() {
		return points;
	}

	// Public setter for the x value, parameter is an integer.
	public void setX(int j) {
		x = j;
	}

	// Public setter for the y value, parameter is an integer.
	public void setY(int i) {
		y = i;
	}

	// Public increment method for the points value.
	public void incrementPoints() {
		points += 1;
	}
}
