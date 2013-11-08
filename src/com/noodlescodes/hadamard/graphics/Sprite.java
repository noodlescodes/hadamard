package com.noodlescodes.hadamard.graphics;

public class Sprite {
	public final int SIZE;
	private final int width, height;
	public int[] pixels;
	private TYPE type;

	public static Sprite dot = new Sprite(1, 0xFFFFFF, Sprite.TYPE.SQUARE);

	// Need to add more shapes later.
	// If circle is required, needs an odd diameter
	public enum TYPE {
		SQUARE, CIRCLE;
	}

	public Sprite(int size, int x, int y) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
	}

	public Sprite(int width, int height, int x, int y, int color) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);
	}

	public Sprite(int size, int color, TYPE t) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.type = t;
		setColor(color);
		setShape();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void setShape() {
		switch (type) {
		case SQUARE:
			break;
		case CIRCLE:
			for (int y = 0; y < SIZE; y++) {
				for (int x = 0; x < SIZE; x++) {
					if (Math.sqrt((x - (SIZE >> 1)) * (x - (SIZE >> 1))
							+ (y - (SIZE >> 1)) * (y - (SIZE >> 1))) > (double) (SIZE >> 1)) {
						pixels[x + y * SIZE] = 0xFF00FF;
					}
				}
			}
			break;
		}
	}

	public void setColor(int color) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}

	public void setSprite(TYPE t) {
		switch (t) {
		case SQUARE:
			break;
		case CIRCLE:
			break;
		}
	}
}
