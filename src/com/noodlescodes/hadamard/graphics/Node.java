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
	private int upperX, lowerX;

	public Node(int x, int y, int level, TNode<EquationSystemMatrix> data, Sprite.TYPE type, Node parent, int lowerX, int upperX) {
		this.x = x;
		this.y = y;
		this.data = data;
		this.level = level;
		this.type = type;
		this.parent = parent;
		this.lowerX = lowerX;
		this.upperX = upperX;
	}

	public void update() {
		if(childrenGenerated) {
			type = Sprite.TYPE.CIRCLE;
		}
	}

	// This works, just need to render if parent is on screen or child is on screen
	// Turns out this doesn't work. I need to make it so the centre of the child or parent is on screen
	public void renderEdges(int lowX, int highX, int lowY, int highY, Screen screen) {
		if(parent == null) {
			return;
		}
		boolean childOnScreen = lowX - 17 > x || highX - 9 < x || lowY - 17 > y || highY < y;
		boolean parentOnScreen = lowX - 17 > parent.x || highX - 9 < parent.x || lowY - 17 > parent.y || highY < parent.y;
		if(childOnScreen && parentOnScreen) {
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
//		renderEdges(lowX, highX, lowY, highY, screen);
		screen.renderSprite(x, y, new Sprite(17, col, type), false);
//		renderEdges(lowX, highX, lowY, highY, screen);
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
	
	public boolean getHasChildren() {
		return hasChildren;
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
	
	public int getLowerX() {
		return lowerX;
	}
	
	public int getUpperX() {
		return upperX;
	}
}
