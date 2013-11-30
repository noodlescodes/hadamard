package com.noodlescodes.hadamard.graphics;

import java.util.ArrayList;

import com.noodlescodes.hadamard.structures.EquationSystemMatrix;
import com.noodlescodes.hadamard.structures.TNode;

public class Drawer {
	public int width, height;

	private final static int levelHeight = 50;

	private ArrayList<Node> nodes;

	public Drawer(int width, int height, Node n) {
		this.width = width;
		this.height = height;
		nodes = new ArrayList<Node>();
		nodes.add(n);
	}

	// don't try to understand what's going here. Magic happens.
	// public void update(int x, int y, boolean enterPressed) {
	// for(int i = 0; i < nodes.size(); i++) {
	// // change the hard coded 17 here to the size of the node we are looking at by the formula for the level.
	// if(nodes.get(i).x <= x && nodes.get(i).x + 17 >= x && nodes.get(i).y <= y && nodes.get(i).y + 17 >= y && enterPressed && !nodes.get(i).getComplete()) {
	// ArrayList<EquationSystemMatrix> children = nodes.get(i).getData().generateChildren();
	// try {
	// int xpInitial = nodes.get(i).x / children.size();
	// for(int j = 0; j < children.size(); j++) {
	// // have to change x-positioning
	// // Probably going to need two variables in each Node to specify the upper and lower bounds for the children's placement.
	// Node n = new Node(xpInitial * (j + 1), (nodes.get(i).level + 1) * levelHeight, nodes.get(i).level + 1, new TNode<EquationSystemMatrix>(children.get(j)), Sprite.TYPE.SQUARE, nodes.get(i));
	// if(n.level == n.getData().getGramOrder() - 1) {
	// n.hasSolution();
	// n.setType(Sprite.TYPE.CIRCLE);
	// n.childrenGenerated();
	// }
	// addNode(n);
	// }
	// nodes.get(i).hasChildren();
	// }
	// catch(ArithmeticException e) {
	// // this had better only activate with a division by 0 error
	// System.out.println("No children");
	// }
	// nodes.get(i).childrenGenerated();
	// }
	// nodes.get(i).update();
	// }
	// }

	public void update(int x, int y, boolean enterPressed) {
		boolean updated = false;
		for(int i = 0; i < nodes.size(); i++) {
			if(nodes.get(i).x <= x && nodes.get(i).x + 17 >= x && nodes.get(i).y <= y && nodes.get(i).y + 17 >= y && enterPressed && !nodes.get(i).getComplete()) {
				ArrayList<EquationSystemMatrix> children = nodes.get(i).getData().generateChildren();
				updated = true;
				for(int j = 0; j < children.size(); j++) {
					Node n = new Node(-8, (nodes.get(i).level) * levelHeight, nodes.get(i).level + 1, new TNode<EquationSystemMatrix>(children.get(j)), Sprite.TYPE.SQUARE, nodes.get(i));
					if(n.level == n.getData().getGramOrder() - 1) {
						n.hasSolution();
						n.setType(Sprite.TYPE.CIRCLE);
						n.childrenGenerated();
					}
					addNode(n);
				}
				if(children.size() > 0) {
					nodes.get(i).hasChildren();
				}
				nodes.get(i).childrenGenerated();
			}
			nodes.get(i).update();
		}
		if(updated) {
			updatePosition();
		}
	}

	private void updatePosition() {
		int numberOfLeaf = 0;
		int X;

		// count the number of leaf nodes
		for(int i = 0; i < nodes.size(); i++) {
			if(!nodes.get(i).getHasChildren()) {
				numberOfLeaf++;
			}
		}

		if(numberOfLeaf % 2 == 0) {
			X = 50 - numberOfLeaf * 100 / 2 - 8;
		}
		else {
			X = -(numberOfLeaf - 1) * 100 / 2 - 8;
		}
		int leafNodeEncounter = 0;
		// equidistant spread of leaf nodes
		for(int i = 0; i < nodes.size(); i++) {
			if(!nodes.get(i).getHasChildren()) {
				nodes.get(i).x = X + leafNodeEncounter * 100;
				X += 100;
			}
		}
	}

	public String hover(int x, int y) {
		String str = null;
		for(int i = 0; i < nodes.size(); i++) {
			// change the hardcoded 17 here to the size of the node we are looking at by the formula for the level.
			if(nodes.get(i).x <= x && nodes.get(i).x + 17 >= x && nodes.get(i).y <= y && nodes.get(i).y + 17 >= y) {
				str = nodes.get(i).getData().toString();
				if(nodes.get(i).getHasSolution()) {
					str += "\nCompleted!";
				}
			}
		}
		return str;
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		// Think I'm putting the rendering of edges in the rendering of nodes.
		for(int i = 0; i < nodes.size(); i++) {
			nodes.get(i).renderEdges(xScroll, xScroll + screen.width, yScroll, yScroll + screen.height, screen);
		}
		for(int i = 0; i < nodes.size(); i++) {
			nodes.get(i).render(xScroll, xScroll + screen.width, yScroll, yScroll + screen.height, screen);
		}
	}

	public void addNode(Node e) {
		nodes.add(e);
	}
}
