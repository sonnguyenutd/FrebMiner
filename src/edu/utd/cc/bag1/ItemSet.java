package edu.utd.cc.bag1;

import java.util.Set;

public class ItemSet {
	protected Set<Item> itemset;

	public boolean contains(Item i) {
		return itemset.contains(i);
	}

	public void addAll(ItemSet itset) {
		itemset.addAll(itset.itemset);
	}
}
