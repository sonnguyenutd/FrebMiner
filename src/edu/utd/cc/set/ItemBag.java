package edu.utd.cc.set;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/**
 * A bag of items contains a set of items and their quantitative
 * @author sonnguyen
 *
 */
public class ItemBag {
	/** A set of items */
	Set<Item> itemset;
	/** An item respects to its quantitative */
	protected Map<Item, Integer> itemquan;

	public ItemBag() {
		itemset = new HashSet<Item>();
		itemquan = new HashMap<Item, Integer>();
	}
	public ItemBag(ItemBag other) {
		itemset = new HashSet<Item>(other.itemset);
		itemquan = new HashMap<Item, Integer>(other.itemquan);
	}

	public ItemBag(Set<Item> currentItemset) {
		itemset = currentItemset;
		itemquan = new HashMap<Item, Integer>();
	}
	/**
	 * TODO: Might be changed to be fit with Hoan's input
	 * @param i
	 * @param quan
	 */
	public void add(Item i, Integer quan){
		itemset.add(i);
		itemquan.put(i, quan);
	}

	public int getF() {
		int result = 0;
		for (Item it : itemquan.keySet())
			result += itemquan.get(it);
		return result;
	}
	@Override
	public String toString() {
		return itemquan.toString();
	}
}
