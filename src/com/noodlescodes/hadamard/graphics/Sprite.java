package com.noodlescodes.hadamard.graphics;

public class Sprite {
	public final int SIZE;
	private final int width, height;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	private TYPE type;

	public static Sprite voidSprite = new Sprite(16, 0, Sprite.TYPE.SQUARE);
	public static Sprite voidSpritePink = new Sprite(16, 0xFF00FF, Sprite.TYPE.SQUARE);
	public static Sprite dot = new Sprite(1, 0xFFFFFF, Sprite.TYPE.SQUARE);

	public static Sprite square = new Sprite(16, 0, 0, SpriteSheet.shapes);

	// Need to add more shapes later.
	// If circle is required, needs an odd diameter
	public enum TYPE {
		SQUARE, CIRCLE;
	}

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.x = x * SIZE;
		this.y = y * SIZE;
		this.width = size;
		this.height = size;
		this.sheet = sheet;
		pixels = new int[SIZE * SIZE];
		load();
	}

	public Sprite(int width, int height, int color) {
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
		switch(type) {
			case SQUARE:
				break;
			case CIRCLE:
				for(int y = 0; y < SIZE; y++) {
					for(int x = 0; x < SIZE; x++) {
						if(Math.sqrt((x - (SIZE >> 1)) * (x - (SIZE >> 1)) + (y - (SIZE >> 1)) * (y - (SIZE >> 1))) > (double)(SIZE >> 1)) {
							pixels[x + y * SIZE] = 0xFF00FF;
						}
					}
				}
				break;
		}
	}

	public void setColor(int color) {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}

	public void setSprite(TYPE t) {
		switch(t) {
			case SQUARE:
				break;
			case CIRCLE:
				break;
		}
	}

	private void load() {
		for(int y = 0; y < SIZE; y++) {
			for(int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(this.x + x) + (this.y + y) * sheet.SIZE];
			}
		}
	}
}
