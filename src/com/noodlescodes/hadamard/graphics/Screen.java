package com.noodlescodes.hadamard.graphics;

public class Screen {

	public int width, height;
	public int[] pixels;

	private int xOffset, yOffset;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderShape(int xp, int yp, Sprite sprite) {
		for(int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp - yOffset;
			for(int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp - xOffset;
				if(xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) {
					break;
				}
				if(xa < 0) {
					xa = 0;
				}
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.SIZE];
			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
