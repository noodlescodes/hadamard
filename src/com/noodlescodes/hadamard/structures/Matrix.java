package com.noodlescodes.hadamard.structures;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Matrix {

	protected int[][] mat;
	public int rows, columns; // fine being public
	
	public Matrix() {
	}

	public Matrix(int size) {
		mat = new int[size][size];
		rows = size;
		columns = size;
		initialise();
	}

	public Matrix(int rows, int columns) {
		mat = new int[rows][columns];
		this.rows = rows;
		this.columns = columns;
		initialise();
	}

	public Matrix(int rows, int columns, int[] r, int[] b) {
		mat = new int[rows][columns];
		this.rows = rows;
		this.columns = columns;
	}

	public Matrix(int rows, int columns, int[] r, int[] b, int[][] mat) {
		this.mat = mat;
		this.rows = rows;
		this.columns = columns;
	}

	public Matrix(String file) {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br =  new BufferedReader(new FileReader(file));
			
			line = br.readLine();
			String[] data = line.split(cvsSplitBy); //gets row and column data
			rows = Integer.parseInt(data[0]);
			columns = Integer.parseInt(data[1]);
			
			mat = new int[rows][columns];
			
			int rowReadin = 0;
			// while loop reads in matrix
			for(line = br.readLine(); rowReadin < rows; line = br.readLine()) {
				data = line.split(cvsSplitBy);
				for(int i = 0; i < mat[rowReadin].length; i++) {
					mat[rowReadin][i] = Integer.parseInt(data[i]);
				}
				rowReadin++;
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}
		catch(IOException e) {
			System.out.println("IO error.");
			e.printStackTrace();
		}
		finally {
			if(br != null) {
				try {
					br.close();
				}
				catch(IOException e) {
					System.out.println("Couldn't close BufferedReader.");
					e.printStackTrace();
				}
			}
		}
	}

	protected void initialise() {
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				mat[i][j] = 0;
			}
		}
	}

	public void setIJ(int i, int j, int v) {
		mat[i][j] = v;
	}

	public String toString() {
		String str = "";

		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns - 1; j++) {
				str += mat[i][j] + ", ";
			}
			str += mat[i][columns - 1] + "\n";
		}

		return str;
	}

	public int rowSum(int i) {
		int sum = 0;

		for(int j = 0; j < columns; j++) {
			sum += mat[i][j];
		}

		return sum;
	}

	public int columnSum(int j) {
		int sum = 0;

		for(int i = 0; i < rows; i++) {
			sum += mat[i][j];
		}

		return sum;
	}
}