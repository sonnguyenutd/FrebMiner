package edu.utd.cc.bag.bk2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author sonnguyen
 *
 */
public class ITNode {
	Set<Item> itemset;
	Set<Transaction> transet;
	Map<Item, Integer> basedBag;
	Integer bF = -1;
	int F;

	Map<Item, Integer> getBasedBag() {
		if (basedBag == null || basedBag.isEmpty()) {
			Map<Item, Integer> bag = new HashMap<>();
			for (Item item : itemset) {
				int min = Integer.MAX_VALUE;
				int itq = 0;
				for (Transaction t : transet) {
					itq = t.bag.itemquan.get(item);
					if (itq < min)
						min = itq;
				}
				bag.put(item, min);
			}
			basedBag = bag;
		}
		return basedBag;
	}

	Integer getBaseF() {
		if (bF == -1) {
			int bf = 0;
			Map<Item, Integer> bag = getBasedBag();
			for (Transaction t : transet) {
				if (t.bag.gteq(bag))
					bf++;
			}
			bF = bf;
		}
		return bF;
	}
	
	public int getF() {
		if (F == 0)
			F = transet.size();
		return F;
	}

	public ITNode() {
		itemset = new HashSet<>();
		transet = new HashSet<>();
	}

	public ITNode(Set<Item> itemset, Set<Transaction> transet) {
		this.itemset = itemset;
		this.transet = transet;
	}

	public Set<Transaction> t() {
		return transet;
	}

	public int sup() {
		return transet.size();
	}

	public ITNode combine(ITNode other) {
		ITNode node = new ITNode();
		node.itemset.addAll(this.itemset);
		node.itemset.addAll(other.itemset);

		node.transet.addAll(this.transet);
		node.transet.addAll(other.transet);
		
		node.basedBag = combineBasedBag(node.itemset, getBasedBag(), other.getBasedBag());
		
		node.removeUncontainTrans();
		return node;
	}

	private Map<Item, Integer> combineBasedBag(Set<Item> is, Map<Item, Integer> bb1, Map<Item, Integer> bb2) {
		Map<Item, Integer> bb = new HashMap<>();
		int value = Integer.MAX_VALUE;
		for (Item item : is) {
			if(bb1.containsKey(item)){
				value = bb1.get(item);
			}
			if(bb2.containsKey(item)){
				if(value > bb2.get(item))
					value = bb2.get(item);
			}
			bb.put(item, value);
		}
		return bb;
	}

	public void removeUncontainTrans() {
		Iterator<Transaction> iter = transet.iterator();
		while (iter.hasNext()) {
			Transaction t = iter.next();
			int count = getFItemset(t);
			if (count == 0)
				iter.remove();
		}
	}

	/**
	 * Get the frequency of an item in the itemset in a transaction of the
	 * transet
	 * 
	 * @param i
	 * @param t
	 * @return
	 */
	public int getFit(Item i, Transaction t) {
		int f = 0;
		if (itemset.contains(i) && transet.contains(t)) {
			f = t.getF(i);
		}
		return f;
	}

	/**
	 * Get the frequency (f_k) of the itemset in a transaction (k) in the
	 * transet. That is f_k = min{i = 1..n}(Fit(i, k)) Page 4, Mining Continuous
	 * Code Changes to Detect Frequent Program Transformations
	 * 
	 * @param t
	 * @return
	 */
	public int getFItemset(Transaction t) {
		int fk = -1;
		if (transet.contains(t)) {
			for (Item item : itemset) {
				if (fk == -1) {
					fk = getFit(item, t);
				} else {
					int temp = getFit(item, t);
					if (fk > temp)
						fk = temp;
				}
			}
		}
		return fk;
	}

	@Override
	public boolean equals(Object obj) {
		ITNode node = (ITNode) obj;
		return itemset.equals(node.itemset);
	}

	@Override
	public int hashCode() {
		return itemset.hashCode();
	}

	@Override
	public String toString() {
		String result = "";
		for (Transaction t : transet) {
			if (result.isEmpty())
				result = (t.tid + "");
			else
				result += (", " + t.tid);
		}
		return itemset.toString() + ":" + "[" + result + "]";
	}
}
