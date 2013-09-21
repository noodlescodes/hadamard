package com.noodlescodes.hadamard.graphics;

import com.noodlescodes.hadamard.structures.EquationSystemMatrix;
import com.noodlescodes.hadamard.structures.TNode;

public class Node {

	public int x, y;
	private TNode<EquationSystemMatrix> data;
	private Sprite.TYPE type; // may change this to manually drawing it, it'll help with resolution.
								// Definitely going to be changed.
	private boolean childrenGenerated = false;
	private boolean hasChildren = false;
	private boolean hasSolution = false;
	public int level;

	public Node(int x, int y, int level, TNode<EquationSystemMatrix> data, Sprite.TYPE type) {
		this.x = x;
		this.y = y;
		this.data = data;
		this.level = level;
		this.type = type;
	}

	public void update() {
		// make sure the sprite is correct here
		if(childrenGenerated) {
			type = Sprite.TYPE.CIRCLE;
		}
	}

	public void render(int lowX, int highX, int lowY, int highY, Screen screen) {
		if(lowX > x || highX < x || lowY > y || highY < y) {
			return;
		}
		int col;
		if(hasSolution) {
			col = 0xFF00FE;
		}
		else if(!childrenGenerated) {
			col = 0x0000FF;
		}
		else if(hasChildren) {
			col = 0x00FF00;
		}
		else {
			col = 0xFF0000;
		}
		screen.renderSprite(x, y, new Sprite(17, col, type), false);
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

	public void hasChildren() {
		hasChildren = true;
	}

	public void hasSolution() {
		hasSolution = true;
	}
	
	public void setType(Sprite.TYPE t) {
		type = t;
	}
}
