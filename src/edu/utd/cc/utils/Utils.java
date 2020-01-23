package edu.utd.cc.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import edu.utd.cc.bag.Item;
import edu.utd.cc.bag.Transaction;

public class Utils {

	public static String read(String filePath) {
		String result = "";
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null)
				result += (sCurrentLine + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	public static void write(String filePath, String content) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
