package edu.utd.cc.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import edu.utd.cc.set.Miner;

public class InputGenerator {
	public static void main(String[] args) {
		InputGenerator generator = new InputGenerator();
		String input = generator.generate();
		Utils.write(Miner.INPUT_FILE, input.trim());
	}

	private String generate() {
		String result = "";
		String tran = null;
		for (int i = 0; i < Miner.NUM_OF_TRANS; i++) {
			tran = genTrans();
			result += (tran+"\n");
		}
		return result;
	}
	Set<Integer> itemset;
	private String genTrans() {
		itemset = new HashSet<>();
		String result = "";
		int numOfItems = genNumOfItemsInBag();
		for (int i = 0; i < numOfItems; i++) {
			if(result.isEmpty())
				result=genItem();
			else
				result+=(","+genItem());
		}
		return result;
	}

	private String genItem() {
		Random rand = new Random();
		int randomItem = rand.nextInt(Miner.NUM_OF_ITEMS) + 1;
		while(itemset.contains(randomItem)){
			randomItem = rand.nextInt(Miner.NUM_OF_ITEMS) + 1;
		}
		itemset.add(randomItem);
		int quanOfItem = rand.nextInt(Miner.MAX_QUAN_OF_ITEMS)+1;
		return ""+randomItem+":"+quanOfItem;
	}

	public int genNumOfItemsInBag() {
		int result = 0;
		Random rand = new Random();
		result = rand.nextInt(Miner.NUM_OF_ITEMS) + 1;
		return result;
	}

}
