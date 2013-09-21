package com.noodlescodes.hadamard;

import com.noodlescodes.hadamard.helpers.Controller;
import com.noodlescodes.hadamard.structures.EquationSystemMatrix;

public class testing {

	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();

		Controller<EquationSystemMatrix> c = new Controller<EquationSystemMatrix>(new EquationSystemMatrix("test8.dat"));
		c.constructTree(8);
		
		System.out.println("Time: " + (System.currentTimeMillis() - startTime));
	}
}
