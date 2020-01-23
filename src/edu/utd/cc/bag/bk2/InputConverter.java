package edu.utd.cc.bag.bk2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputConverter {
	public static void main(String[] args){
		 String raw = "/Users/sonnguyen/Desktop/change bags/repos-junit-bags/junit-team/junit/transaction-bags.txt";
			try (BufferedReader br = new BufferedReader(new FileReader(raw))) {
				String line;
				while ((line = br.readLine()) != null){
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
