package edu.utd.cc.bag1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author sonnguyen
 *
 */
public class ItemBag extends ItemSet{
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
	
	public ItemBag addOneMoreItem(OneItemBag oneBag){
		ItemBag newBag = new ItemBag(this);
		newBag.itemset.add(oneBag.item);
		newBag.itemquan.put(oneBag.item, oneBag.quan);
		return newBag;
	}
}
