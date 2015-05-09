package com.oracle.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class TestReadWriteFile {
	public static void main(String[] args) throws Exception {
		File file = new File("d:\\1.txt");
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter writer = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(writer);
		bw.write("first line");
		bw.write("second line");
		bw.write("third line");
		bw.flush();
		bw.close();
		writer.close();

		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		String str;
		while ((str = br.readLine()) != null) {
			System.out.println(str);
		}
		br.close();
		reader.close();
	}
}
