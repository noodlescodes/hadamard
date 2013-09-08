package com.noodlescodes.hadamard;

import com.noodlescodes.hadamard.helpers.Controller;
import com.noodlescodes.hadamard.structures.EquationSystemMatrix;

public class testing {

	public static void main(String[] args) {

//		int[] r = {3, 1, 1, 1};
//		int[] b = {1, 1, 1, 1, 1};
//		int[][] m = new int[r.length][b.length];
//		for(int i = 0; i < m.length; i++) {
//			for(int j = 0; j < m[i].length; j++) {
//				m[i][j] = 1;
//			}
//		}
//		
//		m[1][3] = -1;
//		m[1][4] = -1;
//		m[2][2] = -1;
//		m[2][4] = -1;
//		m[3][1] = -1;
//		m[3][4] = -1;
		
		/*
		 * int[] r = {3, 1}; int[] b = {3, 2}; int[][] m = new int[r.length][b.length]; for(int i = 0; i < m.length; i++) { for(int j = 0; j < m[i].length; j++) { m[i][j] = 1; } } m[1][2] = -1; m[1][3] = -1; m[2][1] = -1; m[2][3] = -1;
		 */

		Controller<EquationSystemMatrix> c = new Controller<EquationSystemMatrix>(new EquationSystemMatrix("test.dat"));
		c.constructTree(3);

		// System.out.println(c.toString());

		// EquationSystemMatrix m = new EquationSystemMatrix(10); System.out.println(m);

		/*
		 * Random rand = new Random(); int[] r = {3}; int[] b = {5}; int[][] m = new int[r.length][b.length]; /*for(int i = 0; i < m.length; i++) { for(int j = 0; j < m[i].length; j++) { m[i][j] = rand.nextInt() % 5; } }
		 */

		/*
		 * m[0][0] = 1; EquationSystemMatrix mat = new EquationSystemMatrix(r.length, b.length, r, b, m); mat.multiply(mat.mat, b);
		 */
	}
}
