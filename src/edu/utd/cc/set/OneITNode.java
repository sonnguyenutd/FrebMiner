package edu.utd.cc.set;

import java.util.HashSet;
import java.util.Set;

public class OneITNode extends ITNode implements Comparable<OneITNode>{
	Item item;
	
	/** used to sort not yet considered 1-itemsets */
	int tempCounter;

	public OneITNode() {
		this.itemset = new HashSet<>(1);
		this.transet = new HashSet<>();
	}

	public OneITNode(Item item) {
		this.item = item;
		this.itemset = new HashSet<>(1);
		this.itemset.add(item);
		this.transet = new HashSet<>();
	}

	public void extractTranset(Set<Transaction> trans) {
		for (Transaction t : trans)
			if (t.getF(item) > 0)
				this.transet.add(t);
	}

	public void setTempCounter(int tempCounter) {
		this.tempCounter = tempCounter;
	}

	public int getTempCounter() {
		return tempCounter;
	}

	@Override
	public int compareTo(OneITNode o) {
		return o.tempCounter-tempCounter;
	}
	@Override
	public boolean equals(Object obj) {
		OneITNode other = (OneITNode) obj;
		return item.equals(other.item);
	}
}
