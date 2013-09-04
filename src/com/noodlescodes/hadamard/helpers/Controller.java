package com.noodlescodes.hadamard.helpers;

import com.noodlescodes.hadamard.structures.TNode;
import com.noodlescodes.hadamard.structures.Tree;

public class Controller<Type> {
	
	Tree<Type> t;
	
	public Controller(Type data) {
		t = new Tree<Type>(new TNode<Type>(data));
	}
	
	
}
