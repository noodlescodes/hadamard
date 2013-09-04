package com.noodlescodes.hadamard.structures;

public class Tree <Type> {
	
	public TNode<Type> root;
	
	public Tree(TNode<Type> root) {
		this.root = root;
	}
	
	public TNode<Type> getRoot() {
		return root;
	}
}
