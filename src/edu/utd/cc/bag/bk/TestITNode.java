package edu.utd.cc.bag.bk;

import java.util.HashSet;
import java.util.Set;

public class TestITNode {

	public static void main(String[] args) {
		Set<Transaction> trans = Transaction.parse(Miner.INPUT_FILE);
		Set<Item> allItems = new HashSet<>();
		for (Transaction tran : trans) {
			System.out.println(tran);
			allItems.addAll(tran.getItemSet());
		}
		
	}

}
