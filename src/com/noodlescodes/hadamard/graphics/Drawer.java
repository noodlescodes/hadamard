package com.noodlescodes.hadamard.graphics;

import java.util.ArrayList;

import com.noodlescodes.hadamard.structures.EquationSystemMatrix;
import com.noodlescodes.hadamard.structures.TNode;

public class Drawer {
	public int width, height;
	public int[] pixels;

	private final static int levelHeight = 75;

	private ArrayList<Node> nodes;

	public Drawer(int width, int height, Node n) {
		this.width = width;
		this.height = height;
		nodes = new ArrayList<Node>();
		nodes.add(n);
	}

	// don't try to understand what's going here. Magic happens.
	public void update(int x, int y, boolean enterPressed) {
		for(int i = 0; i < nodes.size(); i++) {
			// change the hardcoded 17 here to the size of the node we are looking at by the formula for the level.
			if(nodes.get(i).x <= x && nodes.get(i).x + 17 >= x && nodes.get(i).y <= y && nodes.get(i).y + 17 >= y && enterPressed && !nodes.get(i).getComplete()) {
				ArrayList<EquationSystemMatrix> children = nodes.get(i).getData().generateChildren();
				try {
					int xpInitial = nodes.get(i).x / children.size();
					for(int j = 0; j < children.size(); j++) {
						// got to change x-positioning
						Node n = new Node(xpInitial * (j + 1), (nodes.get(i).level + 1) * levelHeight, nodes.get(i).level + 1, new TNode<EquationSystemMatrix>(children.get(j)), Sprite.TYPE.SQUARE);
						if(n.level == n.getData().getGramOrder() - 1) {
							n.hasSolution();
							n.setType(Sprite.TYPE.CIRCLE);
						}
						addNode(n);
					}
					nodes.get(i).hasChildren();
				}
				catch(ArithmeticException e) {
					// this had better only activate with a division by 0 error
					System.out.println("No children #foreveralone");
				}
				nodes.get(i).childrenGenerated();
			}
			nodes.get(i).update();
		}
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		for(int i = 0; i < nodes.size(); i++) {
			nodes.get(i).render(xScroll, xScroll + screen.width, yScroll, xScroll + screen.height, screen);
		}
	}

	public void addNode(Node e) {
		nodes.add(e);
	}
}
