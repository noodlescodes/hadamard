package com.noodlescodes.hadamard;

import com.noodlescodes.hadamard.helpers.Controller;
import com.noodlescodes.hadamard.structures.EquationSystemMatrix;


public class testing {
	
	public static void main(String[] args) {
		
		Controller<EquationSystemMatrix> c = new Controller<EquationSystemMatrix>(new EquationSystemMatrix(1));
		
		c.constructTree(3);
		
		//System.out.println(c.toString());
		
		/*EquationSystemMatrix m = new EquationSystemMatrix(10);
		
		System.out.println(m);*/
	}
}
