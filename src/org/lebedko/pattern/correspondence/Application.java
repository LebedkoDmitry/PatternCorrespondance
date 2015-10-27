package org.lebedko.pattern.correspondence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
	
	private static final String INPUT_FILE_NAME = "input.txt";
	private static final String OUTPUT_FILE_NAME = "output.txt";

	public static void main(String[] args) {
		
		Scanner input = null;
		PrintWriter output = null;
		try {
			input = new Scanner(new File(INPUT_FILE_NAME));
			String pattern = input.nextLine();
			String str = input.nextLine();
			int n = str.length() + 1;
			int m = pattern.length() + 1;
			int a = 0, b = 0;
			String s = "";
			boolean [][] mat = new boolean[n][m];
			List<String> strings = new ArrayList<String>();
			boolean vertical = false;
			int i = 0;
			int j = 0;
			while (j < m) {
				while (i < n) {
					// 0 символов в шаблоне, 0 в строке, распознаётся
					if (i == 0 && j == 0) {
						mat[i][j] = true;
					}
					// 0 символов в шаблоне, больше 0 в строке, не распознаётся
					if (i > 0 && j == 0) {
						mat[i][j] = false;
					}
					else if (j > 0) {
					// шаблон не пустой, строка не пустая
					//if (i > 0 && j > 0) {
						// если символы шаблона и строки совпадают или шаблон описывает любой символ
						// и предыдущий символ был распознан
						if (i == 0 && (pattern.charAt(j - 1) == '*' ||
								pattern.charAt(j - 1) == '?') && mat[i][j - 1]) {
							mat[i][j] = true;
						}
						if (i > 0) {
							if ((pattern.charAt(j - 1) == str.charAt(i - 1) || pattern.charAt(j - 1) == '?')   
									&& mat[i - 1][j - 1]) {
								mat[i][j] = true;
							}
							// если шаблон допускает любой символ и предыдыдущие символы были распознаны
							else if (pattern.charAt(j - 1) == '*' && (mat[i - 1][j] || mat[i][j - 1] || mat[i - 1][j - 1])){
								mat[i][j] = true;
							}
							else if (pattern.charAt(j - 1) == '?' && mat[i - 1][j - 1]) {
								mat[i][j] = true;
							}
							else {
								mat[i][j] = false;
							}
						}
					//}
					}
					// следующий символ строки
					i++;
				}
				i = 0;
				// следующий символ шаблона
				j++;
			}

			output = new PrintWriter(new File(OUTPUT_FILE_NAME));
			if (mat[n - 1][m - 1]) {
				output.println("YES");
				i = n - 1;
				j = m - 1;
				while (i >= 0 || j >= 0) {
					if ((i - 1) < 0) {
						a = 0;
					}
					else {
						a = i - 1;
					}
					if ((j - 1) < 0) {
						b = 0;
					}
					else {
						b = j - 1;
					}
					if (mat[a][b]) {
						if (vertical) {
							// если движение было в вертикальном направлении
							s = s + str.charAt(a);
							vertical = false;
							strings.add(s);
							s = "";
						} else {
							// если ранее движение было в диагональном
							// направлении
							if (i != 0 && j != 0) {
								strings.add(String.valueOf(str.charAt(a)));
							}
						}
						i--;
						j--;
					} else {
						if (mat[a][j]) {
							s = s + str.charAt(a);
							i--;
							vertical = true;
						} else {
							if (mat[i][b]) {
								j--;
								strings.add(new String(""));
							}
						}
					}
				}
				if (strings.size() == 1) {
					String temp = strings.get(0);
					temp = new StringBuilder(temp).reverse().toString();
					strings.set(0, temp);
				} else {
					for (int k = 0; k < strings.size() / 2; k++)
					{
					    String temp = strings.get(k);
					    temp = new StringBuilder(temp).reverse().toString();
					    strings.set(k, strings.get(strings.size() - k - 1));
					    strings.set((strings.size() - k - 1), temp);
					}
				}
				for (String string : strings) {
					output.println(string);
				}
			}
			else {
				output.println("NOT");
			}
		}
		catch (FileNotFoundException exception) {
			System.out.println(exception);
		}
		finally {
			input.close();
			output.close();
		}

	}

}
