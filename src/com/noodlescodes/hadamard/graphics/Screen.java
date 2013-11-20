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

	// how could I not have committed the diagonal rendering somewhere #idiot
	public void renderLine(int x0, int y0, int x1, int y1) {
		x0 -= xOffset;
		x1 -= xOffset;
		y0 -= yOffset;
		y1 -= yOffset;
		if(x1 - x0 == 0) {
			for(int y = Math.min(y1, y0); y < Math.max(y1, y0); y++) {
				if(y < 0 || y >= height) {
					continue;
				}
				pixels[x0 + y * width] = 0x5E2D79;
			}
			return;
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
				if(xa < -sprite.getWidth() || xa >= width || ya < 0 || ya >= height) {
					break;
				}
				if(xa < 0) {
					xa = 0;
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
