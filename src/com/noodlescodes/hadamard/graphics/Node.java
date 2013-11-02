package com.noodlescodes.hadamard.graphics;

import com.noodlescodes.hadamard.structures.EquationSystemMatrix;
import com.noodlescodes.hadamard.structures.TNode;

public class Node {

	public int x, y;
	private TNode<EquationSystemMatrix> data;
	private Sprite.TYPE type;
	private boolean childrenGenerated = false;
	private boolean hasChildren = false;
	private boolean hasSolution = false;
	public int level;
	private Node parent;

	public Node(int x, int y, int level, TNode<EquationSystemMatrix> data, Sprite.TYPE type, Node parent) {
		this.x = x;
		this.y = y;
		this.data = data;
		this.level = level;
		this.type = type;
		this.parent = parent;
	}

	public void update() {
		if(childrenGenerated) {
			type = Sprite.TYPE.CIRCLE;
		}
	}

	// this still doesn't fucking work
	// public void renderEdges(int lowX, int highX, int lowY, int highY, Screen screen) {
	// boolean childOnScreen = lowX < this.x && highX + 17 > this.x && lowY < this.y && highY + 17 > this.y;
	// boolean parentOnScreen = false;
	// if(this.parent != null) {
	// parentOnScreen = lowX - 17 < this.parent.x && highX > this.parent.x && lowY - 17 < this.parent.y && highY > this.parent.y;
	// }
	// else {
	// parentOnScreen = false;
	// }
	// if(parent != null && (childOnScreen || parentOnScreen)) {
	// screen.renderLine(this.x + 8, this.y + 8, this.parent.x + 8, this.parent.y + 8);
	// }
	// }

	// This works, just need to render if parent is on screen or child is on screen
	public void renderEdges(int lowX, int highX, int lowY, int highY, Screen screen) {
		if(parent == null) {
			return;
		}
		if(lowX - 17 > x || highX - 9 < x || lowY - 17 > y || highY < y) {
			return;
		}
		screen.renderLine(this.x + 8, this.y + 8, this.parent.x + 8, this.parent.y + 8);
	}

	public void render(int lowX, int highX, int lowY, int highY, Screen screen) {
		// remove hard coded 17's from everywhere!
		if(lowX - 17 > x || highX < x || lowY - 17 > y || highY < y) {
			return;
		}
		int col;
		// 0x5E2D79 - purple
		if(hasSolution) {
			col = 0xFF00FE;
		}
		else if(!childrenGenerated) {
			col = 0xFF6600;
		}
		else if(hasChildren) {
			col = 0x00FF00;
		}
		else {
			col = 0xFF0000;
		}
		screen.renderSprite(x, y, new Sprite(17, col, type), false);
		renderEdges(lowX, highX, lowY, highY, screen);
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

	public boolean getHasSolution() {
		return hasSolution;
	}

	public void setType(Sprite.TYPE t) {
		type = t;
	}

	public Node getParent() {
		return parent;
	}

	public int numberOfChildren() {
		if(!childrenGenerated) {
			return 0;
		}
		else {
			return data.getNumberChildren();
		}
	}
}
