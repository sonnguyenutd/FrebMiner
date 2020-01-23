package edu.utd.cc.bag.bk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Itemset-Transaction (IT) node in a IT tree representing transaction DB That
 * is the vertical data format.
 * 
 * @author sonnguyen
 *
 */
public class ITNode {
	public static final int FD_EQUIVALENT = 0;
	public static final int FD_MORE_POWERFUL = 1;
	Set<Item> itemset;
	Set<Transaction> transet;
	
	/** represents for fk in FD (Frequency Descriptor), there is no fk' */
	Map<Integer, Integer> Fk;
	int F;

	public ITNode() {
		itemset = new HashSet<>();
		transet = new HashSet<>();
		Fk = new HashMap<>();
	}

	public ITNode(Set<Item> itemset, Set<Transaction> transet) {
		this.itemset = itemset;
		this.transet = transet;
		computeF();
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

	/**
	 * Get the overall frequency of the itemset in m transactions. That is F =
	 * SUM{k = 1..m}(f_k) Page 4, Mining Continuous Code Changes to Detect
	 * Frequent Program Transformations
	 * 
	 * @return
	 */
	public void computeF() {
		F = 0;
		int value;
		for (Transaction t : transet) {
			value = getFItemset(t);
			F += value;
			Fk.put(t.tid, value);
		}
	}

	public int getF() {
		if (F == 0)
			computeF();
		return F;
	}

	/**
	 * Compare 2 Frequency Descriptors.
	 * 
	 * @param other
	 * @return
	 */
//	public int compare(ITNode other) {
//		int result = FD_EQUIVALENT;
//
//		if (other.transet.contains(transet) && !other.transet.equals(transet))
//			return -1;
//		if (other.transet.equals(transet) || transet.contains(other.transet)) {
//			if (getF() < other.getF())
//				return -1;
//			if (getF() > other.getF()) {
//				result = FD_MORE_POWERFUL;
//			}
//			for (Integer t : other.Fk.keySet()) {
//				if (Fk.get(t) < other.Fk.get(t))
//					return -1;
//				if (Fk.get(t) > other.Fk.get(t))
//					result = FD_MORE_POWERFUL;
//			}
//		}
//		return result;
//	}

	public int compareFD(ITNode other) {
		int result = FD_EQUIVALENT;

		if (getF() < other.getF())
			return -1;
		if (getF() > other.getF()) {
			result = FD_MORE_POWERFUL;
		}
		for (Integer t : Fk.keySet()) {
			if (Fk.get(t) < other.Fk.get(t))
				return -1;
			if (Fk.get(t) > other.Fk.get(t))
				result = FD_MORE_POWERFUL;
		}
		return result;
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
		node.removeUncontainTrans();
		node.computeF();
		return node;
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
				result += (t.tid + ":" + getFItemset(t));
			else
				result += (", " + t.tid + ":" + getFItemset(t));
		}
		return itemset.toString() + ":" + "[" + result + "]";
	}
}
