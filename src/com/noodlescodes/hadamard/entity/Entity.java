package com.noodlescodes.hadamard.entity;

import com.noodlescodes.hadamard.graphics.Drawer;
import com.noodlescodes.hadamard.graphics.Screen;

public class Entity {
	public int x, y;
	protected Drawer drawer;
	
	public void update() {
	}
	
	public void render(Screen screen) {
	}
	
	public void init(Drawer drawer) {
		this.drawer = drawer;
	}
}
