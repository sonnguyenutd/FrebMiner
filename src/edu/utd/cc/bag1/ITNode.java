package edu.utd.cc.bag1;

import java.util.HashSet;
import java.util.Set;

/**
 * Itemset-Transaction (IT) node in a IT tree representing transaction DB That
 * is the vertical data format.
 * 
 * @author sonnguyen
 *
 */
public class ITNode {
	ItemSet itset;
	Set<BagITNode> bagNodes;
	Set<Transaction> transet;

	public ITNode() {
		itset = new ItemSet();
		transet = new HashSet<>();
		bagNodes = new HashSet<>();
	}

	public ITNode(ItemSet itemset, Set<Transaction> transet) {
		this.itset = itemset;
		this.transet = transet;
	}

	public Set<Transaction> t() {
		return transet;
	}

	public int sup() {
		return transet.size();
	}

	public ITNode combine(ITNode other) {
		//TODO: check combine
		ITNode node = new ITNode();
		node.itset.addAll(this.itset);
		node.itset.addAll(other.itset);

		node.transet.addAll(this.transet);
		node.transet.addAll(other.transet);
		return node;
	}

	@Override
	public boolean equals(Object obj) {
		ITNode node = (ITNode) obj;
		return itset.equals(node.itset);
	}

	@Override
	public int hashCode() {
		return itset.hashCode();
	}

	@Override
	public String toString() {
		String result = "";
		
		return result;
	}
}
