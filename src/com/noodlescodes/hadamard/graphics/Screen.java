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

	public void renderLine_old(int x0, int y0, int x1, int y1) {
		x0 -= xOffset;
		x1 -= xOffset;
		y0 -= yOffset;
		y1 -= yOffset;
		if(x1 - x0 == 0) {
			int yMax = Math.max(y1, y0);
			for(int y = Math.min(y1, y0); y < yMax; y++) {
				if(y < 0 || y >= height) {
					continue;
				}
				try {
					pixels[x0 + y * width] = 0x5E2D79;
				}
				catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("x0: " + x0 + "; y0: " + y0 + "; x1: " + x1 + "; y1" + y1);
				}
			}
			return;
		}
	}

	// uses Bresenham's algorithm for drawing lines.
	// Only uses integer functions and no divisions
	public void renderLine(int x0, int y0, int x1, int y1) {
		x0 -= xOffset;
		x1 -= xOffset;
		y0 -= yOffset;
		y1 -= yOffset;
		int w = x1 - x0;
		int h = y1 - y0;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		if(w < 0) {
			dx1 = -1;
			dx2 = -1;
		}
		else if(w > 0) {
			dx1 = 1;
			dx2 = 1;
		}
		if(h < 0) {
			dy1 = -1;
		}
		else if(h > 0) {
			dy1 = 1;
		}

		int longest = Math.abs(w);
		int shortest = Math.abs(h);
		if(!(longest > shortest)) {
			longest = Math.abs(h);
			shortest = Math.abs(w);
			if(h < 0) {
				dy2 = -1;
			}
			else if(h > 0) {
				dy2 = 1;
			}
			dx2 = 0;
		}
		int numerator = longest >> 1;
		int xTemp = x0;
		int yTemp = y0;
		for(int i = 0; i < longest; i++) {
			try {
				pixels[xTemp + yTemp * width] = 0x5E2D79;
			}
			catch(ArrayIndexOutOfBoundsException e) {
			}
			numerator += shortest;
			if(!(numerator < longest)) {
				numerator -= longest;
				xTemp += dx1;
				yTemp += dy1;
			}
			else {
				xTemp += dx2;
				yTemp += dy2;
			}
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

	public int getYOffset() {
		return yOffset;
	}
}
