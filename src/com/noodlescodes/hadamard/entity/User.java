package com.noodlescodes.hadamard.entity;

import com.noodlescodes.hadamard.graphics.Screen;
import com.noodlescodes.hadamard.graphics.Sprite;
import com.noodlescodes.hadamard.input.Keyboard;

public class User extends Entity {

	private int dir = 0;
	private boolean[] directions = new boolean[4];
	public boolean moving = false;

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

	public void move(int xa, int ya) {
		if(xa > 0) {
			dir = 1;
			directions[1] = true;
		}
		else {
			directions[1] = false;
		}
		if(xa < 0) {
			dir = 3;
			directions[3] = true;
		}
		else {
			directions[3] = false;
		}
		if(ya > 0) {
			dir = 2;
			directions[2] = true;
		}
		else {
			directions[2] = false;
		}
		if(ya < 0) {
			dir = 0;
			directions[0] = true;
		}
		else {
			directions[0] = false;
		}
		x += xa;
		y += ya;
	}

	public void update() {
		moveAlgorithm();
	}

	public void render(Screen screen) {
		screen.renderShape(x, y, s);
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
			moving = true;
		}
		else {
			moving = false;
		}
	}
}
