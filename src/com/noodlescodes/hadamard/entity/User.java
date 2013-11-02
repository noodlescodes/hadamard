package com.noodlescodes.hadamard.entity;

import com.noodlescodes.hadamard.graphics.Screen;
import com.noodlescodes.hadamard.graphics.Sprite;
import com.noodlescodes.hadamard.input.Keyboard;

public class User extends Entity {

	private static Sprite s = Sprite.dot;
	private Keyboard input;

	public User(Keyboard input) {
		x = 0;
		y = 0;
		this.input = input;
	}

	public User(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
	}

	private void move(int xa, int ya) {
		x += xa;
		y += ya;
	}

	public void update() {
		moveAlgorithm();
	}

	public void render(Screen screen) {
		screen.renderSprite(x, y, s, false);
	}

	private void moveAlgorithm() {
		int xa = 0, ya = 0;
		if(input.up) {
			ya--;
		}
		if(input.down) {
			ya++;
		}
		if(input.left) {
			xa--;
		}
		if(input.right) {
			xa++;
		}

		if(xa != 0 || ya != 0) {
			move(xa, ya);
		}
	}
}
