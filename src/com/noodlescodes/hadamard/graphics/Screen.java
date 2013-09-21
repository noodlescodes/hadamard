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

	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if(fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for(int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp - yOffset;
			for(int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp - xOffset;
				if(xa < 0 || xa >= width || ya < 0 || ya >= height) {
					continue;
				}
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if(col != 0xFF00FF) {
					pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
				}
			}
		}
	}

	// Not in use anymore
	public void renderSpriteOLD(int xp, int yp, Sprite sprite, boolean fixed) {
		if(fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
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

	// Not in use anymore
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
