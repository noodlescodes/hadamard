package com.noodlescodes.hadamard.graphics;

import com.noodlescodes.hadamard.structures.EquationSystemMatrix;
import com.noodlescodes.hadamard.structures.TNode;

public class Node {

	public int x, y;
	private TNode<EquationSystemMatrix> data;
	private Sprite sprite; // may change this to manually drawing it, it'll help with resolution.
	private boolean childrenGenerated;
	public int level;

	public Node(int x, int y, int level, TNode<EquationSystemMatrix> data) {
		this.x = x;
		this.y = y;
		this.data = data;
		this.level = level;
		sprite = Sprite.square;
	}

	public void update() {
		// make sure the sprite is correct here
	}

	public void render(int lowX, int highX, int lowY, int highY, Screen screen) {
		if(lowX > x || highX < x || lowY > y || highY < y) {
			return;
		}
		screen.renderShape(x, y, sprite);
	}
	
	public EquationSystemMatrix getData() {
		return data.getData();
	}

	public boolean getComplete() {
		return childrenGenerated;
	}

	public void childrenGenerated() {
		childrenGenerated = true;
	}
}
