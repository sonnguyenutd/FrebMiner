package edu.utd.cc.bag1;

public class OneItemBag extends ItemBag{
	Item item;
	Integer quan;
	public OneItemBag() {
		super();
	}
	public OneItemBag(Item item, Integer quan) {
		super();
		this.item = item;
		this.quan = quan;
		this.itemset.add(item);
		this.itemquan.put(item, quan);
	}
	public void setItem(Item item) {
		this.item = item;
		this.itemset.add(item);
	}
	public void setQuan(Integer quan) {
		this.quan = quan;
		this.itemquan.put(item, quan);
	}
}
