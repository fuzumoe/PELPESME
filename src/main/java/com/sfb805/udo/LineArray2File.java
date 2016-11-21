package com.sfb805.udo;

import java.io.*;
import java.util.*;

public class LineArray2File {

	/**
	 * @param args
	 *            the command line arguments
	 */
	int numberOfArguments = 13;
	double[][] myArray = new double[0][numberOfArguments];
	// double [][] myArray2 = new double [3][4];

	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		// Variables
		String filepath = "C:/Users/max/desktop/PELOPS/LineArrayExchange.txt";

		LineArray2File array2file = new LineArray2File();

		// create file
		// array2file.createFile(filepath);

		// instantiate array
		// array2file.instantiateArray(array2file.myArray);

		// write myArray into file
		array2file.writeArray2File(array2file.myArray, filepath);
		// read the file into an array
		// double [][] myArray2 = array2file.readFile2Array(filepath);

		// print the myArray
		// array2file.printArray(filepath, myArray);

		// delete the first line of the file
		// array2file.removeFirstLineFromFile(filepath);

		// delete file
		// array2file.deleteFile(filepath);
	}

	public void createFile(String filepath) {
		// ToDo: create File from filepath
		File file = new File(filepath);

		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("File " + filepath + " could not be created");
			e.printStackTrace();
		}
	}

	public void deleteFile(String filepath) {
		File file = new File(filepath);

		file.delete();
	}

	public void instantiateArray(double[][] myArray) {

		for (int i = 0; i < myArray.length; i++) {

			// myArray[i][0]= hash
			// myArray[i][1]= expectationX
			// myArray[i][2]= expectationY
			// myArray[i][3]= expectationZ
			// myArray[i][4]= varianceX
			// myArray[i][5]= varianceY
			// myArray[i][6]= varianceZ

			myArray[i][0] = 1;
			myArray[i][1] = 2;
			myArray[i][2] = 3;
			myArray[i][3] = 4;
			myArray[i][4] = 5;
			myArray[i][5] = 6;
			myArray[i][6] = 7;
			myArray[i][7] = 8;
			myArray[i][8] = 9;
			myArray[i][9] = 10;
			myArray[i][10] = 11;
			myArray[i][11] = 12;
			myArray[i][12] = 13;

			// System.out.println(String.valueOf(myArray[i][0]));
		}

	}

	public void writeArray2File(double[][] myArray, String filepath) {

		// check if the file and the directory exists
		File f = new File(filepath);
		if (!f.exists()) {
			// if the file or the directory do not exist: create it
			this.createFile(filepath);
		}

		// else: write myArray into the file

		Writer writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			for (int i = 0; i < myArray.length; i++) {
				// System.out.println(String.valueOf(myArray[i][0]));

				// writer.write("String");
				writer.write(String.valueOf(myArray[i][0]) + " " + String.valueOf(myArray[i][1]) + " "
						+ String.valueOf(myArray[i][2]) + " " + String.valueOf(myArray[i][3]) + " "
						+ String.valueOf(myArray[i][4]) + " " + String.valueOf(myArray[i][5]) + " "
						+ String.valueOf(myArray[i][6]) + " "
						+ String.valueOf(myArray[i][7] + " "
								+ String.valueOf(myArray[i][8] + " " + String.valueOf(myArray[i][9]) + " "
										+ String.valueOf(myArray[i][10]) + " " + String.valueOf(myArray[i][11]) + " "
										+ String.valueOf(myArray[i][12]))));
				// separate only lines 1...n-1. no separator behind the last
				// line
				if (i < myArray.length - 1) {
					writer.append(System.getProperty("line.separator"));
				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// Read File
	public double[][] readFile2Array(String filepath) {
		String line = "";
		BufferedReader bf = null;
		double[][] ArrayFromFile = null;
		try {
			FileInputStream inputStream = new FileInputStream(filepath);
			Scanner scanner = new Scanner(inputStream);
			DataInputStream in = new DataInputStream(inputStream);
			bf = new BufferedReader(new InputStreamReader(in));
			// BufferedReader bf = new BufferedReader(new
			// InputStreamReader(filepath));
			// BufferedWriter writer = new BufferedWriter(new
			// FileWriter(filepath));

			int lineNumber = 0;
			LineNumberReader lnr = new LineNumberReader(new FileReader(new File(filepath)));
			lnr.skip(Long.MAX_VALUE);
			lineNumber = lnr.getLineNumber() + 1;
			int lineIndex = 0;
			ArrayFromFile = new double[lineNumber][numberOfArguments];
			System.out.println("lineNumber: " + lineNumber);
			while ((line = bf.readLine()) != null) // Read a line
			{

				String[] numbers = line.split(" "); // 'space' as delimiter
				// System.out.println(numbers);
				for (int i = 0; i < numberOfArguments; i++) {
					System.out.println("i: " + String.valueOf(i));
					System.out.println("lineIndex: " + lineIndex);
					ArrayFromFile[lineIndex][i] = Double.parseDouble(numbers[i]);
					System.out.println(String.valueOf(ArrayFromFile[0][i]));
				}

				lineIndex++;
				System.out.println("lineIndex:" + String.valueOf(lineIndex));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			bf.close();
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ArrayFromFile;

	}

	public void writeArrayToFirstLineInFile(double[] firstline, String filepath) {
		double[][] oldArray = null;
		oldArray = this.readFile2Array(filepath);
		double[][] newArray = new double[oldArray.length + 1][numberOfArguments];

		// write first line into new array
		newArray[0][0] = firstline[0];
		newArray[0][1] = firstline[1];
		newArray[0][2] = firstline[2];
		newArray[0][3] = firstline[3];
		newArray[0][4] = firstline[4];
		newArray[0][5] = firstline[5];
		newArray[0][6] = firstline[6];
		newArray[0][7] = firstline[7];
		newArray[0][8] = firstline[8];
		newArray[0][9] = firstline[9];
		newArray[0][10] = firstline[10];
		newArray[0][11] = firstline[11];
		newArray[0][12] = firstline[12];

		// write the rest of the lines into the new array
		for (int i = 0; i < oldArray.length; i++) {

			newArray[i + 1][0] = oldArray[i][0];
			newArray[i + 1][1] = oldArray[i][1];
			newArray[i + 1][2] = oldArray[i][2];
			newArray[i + 1][3] = oldArray[i][3];
			newArray[i + 1][4] = oldArray[i][4];
			newArray[i + 1][5] = oldArray[i][5];
			newArray[i + 1][6] = oldArray[i][6];
			newArray[i + 1][7] = oldArray[i][7];
			newArray[i + 1][8] = oldArray[i][8];
			newArray[i + 1][9] = oldArray[i][9];
			newArray[i + 1][10] = oldArray[i][10];
			newArray[i + 1][11] = oldArray[i][11];
			newArray[i + 1][12] = oldArray[i][12];
			// System.out.println(String.valueOf(myArray[i][0]));

		}
		this.writeArray2File(newArray, filepath);

	}

	public void removeFirstLineFromFile(String filepath) {
		// removes the first line by copying line n+1 into line n and simply
		// overwriting the existing file
		double[][] oldArray = null;
		oldArray = this.readFile2Array(filepath);
		double[][] newArray = new double[oldArray.length - 1][numberOfArguments];
		// replace all entries of line n by line n+1
		for (int i = 0; i < oldArray.length - 1; i++) {
			newArray[i][0] = oldArray[i + 1][0];
			newArray[i][1] = oldArray[i + 1][1];
			newArray[i][2] = oldArray[i + 1][2];
			newArray[i][3] = oldArray[i + 1][3];
			newArray[i][4] = oldArray[i + 1][4];
			newArray[i][5] = oldArray[i + 1][5];
			newArray[i][6] = oldArray[i + 1][6];
			newArray[i][7] = oldArray[i + 1][7];
			newArray[i][8] = oldArray[i + 1][8];
			newArray[i][9] = oldArray[i + 1][9];
			newArray[i][10] = oldArray[i + 1][10];
			newArray[i][11] = oldArray[i + 1][11];
			newArray[i][12] = oldArray[i + 1][12];

			// System.out.println(String.valueOf(myArray[i][0]));
		}
		this.writeArray2File(newArray, filepath);
	}

} // end of class