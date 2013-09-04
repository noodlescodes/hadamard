package com.noodlescodes.hadamard;

import com.noodlescodes.hadamard.structures.TNode;
import com.noodlescodes.hadamard.structures.Tree;

public class testing {
	
	public static void main(String[] args) {
		
		Tree<String> t = new Tree<String>(new TNode<String>("test"));
		
		System.out.println(t.getRoot().getData().toString());
		
		t.getRoot().setData("testing");
		
		System.out.println(t.getRoot().getData().toString());
	}
}
