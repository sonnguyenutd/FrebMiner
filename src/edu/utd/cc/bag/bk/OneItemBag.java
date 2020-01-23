package edu.utd.cc.bag.bk;

import java.util.HashSet;

/**
 * @author sonnguyen
 */
public class OneItemBag extends ItemBag{
	protected Item item;
	protected Integer quan;
	public OneItemBag() {
		this.itemquan = null;
		this.itemset = null;
		directedChildren = new HashSet<>();
		directedParent = new HashSet<>();
	}
}
