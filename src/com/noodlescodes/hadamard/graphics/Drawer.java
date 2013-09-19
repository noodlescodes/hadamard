package com.noodlescodes.hadamard.graphics;

import java.util.ArrayList;

import com.noodlescodes.hadamard.structures.EquationSystemMatrix;
import com.noodlescodes.hadamard.structures.TNode;

public class Drawer {
	public int width, height;
	public int[] pixels;

	private ArrayList<Node> nodes;

	public Drawer(int width, int height, Node n) {
		this.width = width;
		this.height = height;
		nodes = new ArrayList<Node>();
		nodes.add(n);
	}

	public void update(int x, int y, boolean t) {
		for(int i = 0; i < nodes.size(); i++) {
			if(nodes.get(i).x == x && nodes.get(i).y == y && t) {
				ArrayList<EquationSystemMatrix> children = nodes.get(i).getData().generateChildren();
				for(int j = 0; j < children.size(); j++) {
					Node n = new Node(0, 0, nodes.get(i).level, new TNode<EquationSystemMatrix>(children.get(j)));
					addNode(n);
				}
				// set complete here
			}
			nodes.get(i).update();
		}
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		for(int i = 0; i < nodes.size(); i++) {
			nodes.get(i).render(xScroll , xScroll + screen.width, yScroll, xScroll + screen.height, screen);
		}
	}

	public void addNode(Node e) {
		nodes.add(e);
	}
}
