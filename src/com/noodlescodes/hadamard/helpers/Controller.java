package com.noodlescodes.hadamard.helpers;

import java.util.ArrayList;

import com.noodlescodes.hadamard.structures.EquationSystemMatrix;
import com.noodlescodes.hadamard.structures.TNode;
import com.noodlescodes.hadamard.structures.Tree;

public class Controller<Type> {

	private Tree<Type> tree;
	private int level;
	private String[] output;
	private int maxLevel;
	private int[] numberLevel;

	public Controller(Type data) {
		tree = new Tree<Type>(new TNode<Type>(data));
		level = 0;
		maxLevel = 0;
	}

	public TNode<Type> getRoot() {
		return tree.getRoot();
	}

	public void constructTree(int toLevel) {
		generateChildren(toLevel, tree.getRoot());
	}

	// Method recursively generates the tree. Will have to change to iterative when matrix sizes get big.
	@SuppressWarnings("unchecked")
	// this will be removed later.
	private void generateChildren(int toLevel, TNode<Type> node) {
		ArrayList<EquationSystemMatrix> children = ((EquationSystemMatrix) node.getData()).generateChildren();
		for(int i = 0; i < children.size(); i++) {
			node.addChild((Type) children.get(i));
		}
		level++;
		if(level > maxLevel) {
			maxLevel = level;
		}
		if(level < toLevel) {
			//generates all children of the node
			for(int i = 0; i < node.getNumberChildren(); i++) {
				generateChildren(toLevel, node.getChildren().get(i));
			}
		}
		level--;
	}

	//need to rewrite output of the tree.
	private void getStrings(int level, TNode<Type> node) {
		output = new String[maxLevel];
		numberLevel = new int[maxLevel];
		for(int i = 0; i < maxLevel; i++) {
			output[i] = "";
			numberLevel[i] = 0;
		}
		for(int i = 0; i < node.getNumberChildren(); i++) {
			output[level] += node.getChildren().get(i).getData().toString() + ", ";
			if(level < maxLevel - 1) {
				getStrings(level + 1, node.getChildren().get(i));
			}
		}
		output[level] += ": ";
		numberLevel[level]++;
	}

	public String toString() {
		getStrings(0, tree.getRoot());

		String str = "";

		for(int i = 0; i < maxLevel; i++) {
			str += "Level " + Integer.toString(i) + ": " + Integer.toString(numberLevel[i]) + "\n" + output[i] + "\n";
		}

		return str;
	}
}
