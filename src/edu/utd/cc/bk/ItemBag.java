package edu.utd.cc.bk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ItemBag {
	Set<Item> itemset;
	// tid-->fk()
	/**
	 * In horizontal data format:
	 * tid:itembag ([item:quatitative]+)
	 * However, in vertical data format:
	 * item
	 */
	protected Map<Integer, Integer> tidset;

	public ItemBag() {
		itemset = new HashSet<Item>();
		tidset = new HashMap<Integer, Integer>();
	}
	public ItemBag(ItemBag other) {
		itemset = new HashSet<Item>(other.itemset);
		tidset = new HashMap<Integer, Integer>(other.tidset);
	}

	public ItemBag(Set<Item> currentItemset) {
		itemset = currentItemset;
		tidset = new HashMap<Integer, Integer>();
	}

	public void add(Item e) {
		Set<Integer> mutalKeys = tidset.keySet();
		mutalKeys.retainAll(e.tidset.keySet());
		Map<Integer, Integer> ttidset = new HashMap<Integer, Integer>();
		for (Integer tid : mutalKeys) {
			int s = tidset.get(tid);
			int t = e.tidset.get(tid);
			ttidset.put(tid, (t < s) ? t : s);
		}
		tidset = ttidset;
		itemset.add(e);
	}

	public int getF() {
		int result = 0;
		for (Integer tid : tidset.keySet())
			result += tidset.get(tid);
		return result;
	}
}
