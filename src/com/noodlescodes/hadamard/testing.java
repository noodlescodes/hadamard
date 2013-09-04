package com.noodlescodes.hadamard;

import com.noodlescodes.hadamard.helpers.Controller;


public class testing {
	
	public static void main(String[] args) {
		
		Controller<String> c = new Controller<String>("test");
		
		c.constructTree(5);
		
		System.out.println(c.getRoot().getData().toString());
	}
}
