package com.noodlescodes.hadamard.helpers;

import com.noodlescodes.hadamard.structures.TNode;
import com.noodlescodes.hadamard.structures.Tree;

public class Controller<Type> {
	
	private Tree<Type> tree;
	private static int level;
	
	public Controller(Type data) {
		tree = new Tree<Type>(new TNode<Type>(data));
		level = 0;
	}
	
	public TNode<Type> getRoot() {
		return tree.getRoot();
	}
	
	public void constructTree(int toLevel) {
		generateChildren(toLevel, tree.getRoot());
	}
	
	private void generateChildren(int toLevel, TNode<Type> node) {
		for(int i = 0; i < 3; i++) {
			node.addChild((Type) Integer.toString(i));
		}
		level++;
		if(level < toLevel) {
			for(int i = 0; i < node.getNumberChildren(); i++) {
				generateChildren(toLevel, node.getChildren().get(i));
			}
		}
		level--;
	}
	
}
