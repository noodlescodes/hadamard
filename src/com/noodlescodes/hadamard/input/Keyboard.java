package com.noodlescodes.hadamard.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	private boolean[] keys = new boolean[256];
	public boolean up, down, left, right, enter;

	public boolean debug;

	public void update() {
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		enter = keys[KeyEvent.VK_ENTER];
		debug = keys[KeyEvent.VK_F3];
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_F3:
				keys[e.getKeyCode()] = !keys[e.getKeyCode()];
				break;
			default:
				keys[e.getKeyCode()] = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_F3:
				break;
			default:
				keys[e.getKeyCode()] = false;
		}
	}
}
