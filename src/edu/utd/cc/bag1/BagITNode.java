package edu.utd.cc.bag1;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
/**
 * X and Y in a same family of bag node if X.bag.itemset = Y.bag.itemset.
 * X is a child of Y if X.bag.item.quan >= Y.bag.item.quan (X >= Y). 
 * If there exists items i, j that (X.bag.i.quan < Y.bag.i.quan) and (X.bag.j.quan >= Y.bag.j.quan).
 * The pair of X and Y is incomparable.
 * In a family of bag nodes, X is a directed child of Y if there is no Z (!=X) satisfied (X >= Z) and (Z >= Y).
 * The concept of directed parent is presented in the same way...
 * @author sonnguyen
 *
 */
public class BagITNode {
	protected ItemBag bag;
	protected Set<Transaction> transet;
	protected Set<BagITNode> directedChildren;
	protected Set<BagITNode> directedParents;
	public BagITNode() {
		transet = new HashSet<>();
		directedChildren = new HashSet<>();
		directedParents = new HashSet<>();
	}
	public BagITNode(BagITNode other) {
		transet = new HashSet<>(other.transet);
		directedChildren = new HashSet<>(other.directedChildren);
		directedParents = new HashSet<>(other.directedParents);
	}
	
	Set<Transaction> t(){
		return transet;
	}
	/**
	 * Specific frequency
	 * @return
	 */
	int f(){
		return transet.size();
	}
	/**
	 * Overall frequency
	 * @return
	 */
	int F(){
		int F = f();
		Set<BagITNode> descendants = getAllDescendants();
		for (BagITNode d : descendants) {
			F+=d.f();
		}
		return F;
	}
	Set<BagITNode> getAllDescendants(){
		Set<BagITNode> descendants = new HashSet<>(directedChildren);
		for (BagITNode child : directedChildren) 
			descendants.addAll(child.getAllDescendants());
		return descendants;
	}

	boolean isADescendantOf(BagITNode other){
		if(!other.bag.itemset.equals(bag.itemset))
			return false;
		for (Item i : bag.itemset) {
			if(bag.itemquan.get(i) < other.bag.itemquan.get(i))
				return false;
		}
		return true;
	}
	boolean isAnAncestorOf(BagITNode other){
		if(!other.bag.itemset.equals(bag.itemset))
			return false;
		for (Item i : bag.itemset) {
			if(bag.itemquan.get(i) > other.bag.itemquan.get(i))
				return false;
		}
		return true;
	}

	BagITNode addOneMoreItem(OneBagITNode oneBagNode){
		BagITNode node = new BagITNode();
		node.bag = new ItemBag(bag);
		node.bag.addOneMoreItem(oneBagNode.oneBag);
		node.transet.retainAll(oneBagNode.transet);
		
		return node;
	}
}
