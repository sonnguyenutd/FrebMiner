package edu.utd.cc.bag.bk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A bag of items contains a set of items and their quantitative
 * The set of item is considered as the basic bag of a family of bags containing the set.
 * In the family of bags, there might be a bag (X) that is a child of another bag (Y). 
 * In other words, bag Y is contained by bag X. For example, X = (A:5, B:3) is a child of Y = (A:2, B:2).
 * In this case, I might denote X >= Y (X.item.quan >= Y.item.quan). 
 * The special bag (smallest quantities), (A:1, B:1) is the basic bag of the family of bag (A:x, B:y).
 * In the family of bags F, X is a directed child of Y if there is no a bag Z in F, satisfied (X > Z and Z >= Y).
 * A particular bag has its own frequency, 
 * that is the number of transactions containing EXACTLY the bag. 
 * For instance, t1 = (A:5, B:3, C:9) contains X (do not contain Y).
 * This frequency is named "specific frequency" (fs). 
 * The overall frequency (FO) of a bag is summed by its fs and fs of all of its children.
 * In the standard item set mining problem FO is sup of an itembag (itemset). 
 * 
 * @author sonnguyen
 *
 */
public class ItemBag extends ItemSet{
	/** Directed parent */
	protected Set<ItemBag> directedParent;
	/** An item respects to its quantitative */
	protected Map<Item, Integer> itemquan;
	
	public ItemBag() {
		itemset = new HashSet<Item>();
		itemquan = new HashMap<Item, Integer>();
		directedChildren = new HashSet<>();
		directedParent = new HashSet<>();
	}

	public ItemBag(ItemBag other) {
		itemset = new HashSet<Item>(other.itemset);
		itemquan = new HashMap<Item, Integer>(other.itemquan);
		directedChildren = new HashSet<>();
		directedParent = new HashSet<>();
	}

	public ItemBag(Set<Item> currentItemset) {
		itemset = currentItemset;
		itemquan = new HashMap<Item, Integer>();
	}

	public void add(Item i, Integer quan) {
		itemset.add(i);
		itemquan.put(i, quan);
	}

	@Override
	public String toString() {
		return itemquan.toString();
	}

	public Set<Item> getItemset() {
		return itemset;
	}

	public void setItemset(Set<Item> itemset) {
		this.itemset = itemset;
	}

	@Override
	public int hashCode() {
		return itemquan.hashCode();
	}

	public void addAChild(){
		
	}
	
	public void addAParent(){
		
	}
	
	public Set<ItemBag> getAllChildren(){
		Set<ItemBag> children = new HashSet<>(directedChildren);
		for (ItemBag itemBag : directedChildren) {
			children.addAll(itemBag.getAllChildren());
		}
		return children;
	}
	
//	public Integer getOverallF(){
//		int f = specificF;
//		Set<ItemBag> children = getAllChildren();
//		for (ItemBag itemBag : children) {
//			f+=itemBag.specificF;
//		}
//		return f;
//	}
	
	public boolean isAChildOf(ItemBag other) {
		if(!other.itemset.equals(itemset))
			return false;
		for (Item item : itemset) {
			if(itemquan.get(item) < other.itemquan.get(item))
				return false;
		}
		return true;
	}

	public boolean isAParentOf(ItemBag other) {
		if(!other.itemset.equals(itemset))
			return false;
		for (Item item : itemset) {
			if(itemquan.get(item) > other.itemquan.get(item))
				return false;
		}
		return true;
	}
}
