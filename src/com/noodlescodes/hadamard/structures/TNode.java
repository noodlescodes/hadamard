package com.noodlescodes.hadamard.structures;

import java.util.ArrayList;

public class TNode<Type> {
	private Type data;
	private ArrayList<Type> children;
	private int weightToParent;

	public TNode(Type data) {
		this.data = data;
		children = new ArrayList<Type>();
		weightToParent = calculateWeight();
	}

	//Will hopefully calculate the weight function in the end.
	private int calculateWeight() {
		return 0;
	}

	public Type getData() {
		return data;
	}

	public int getWeightToParent() {
		return weightToParent;
	}
	
	public void setData(Type data) {
		this.data = data;
	}
	
	public void addChild(Type c) {
		children.add(c);
	}
}
