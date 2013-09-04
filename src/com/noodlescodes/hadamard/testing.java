package com.noodlescodes.hadamard;

import com.noodlescodes.hadamard.helpers.Controller;
import com.noodlescodes.hadamard.structures.Matrix;


public class testing {
	
	public static void main(String[] args) {
		
		Controller<Matrix> c = new Controller<Matrix>(new Matrix(2));
		
		c.constructTree(3);
		
		System.out.println(c.toString());
	}
}
