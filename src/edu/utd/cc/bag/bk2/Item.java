package edu.utd.cc.bag.bk2;

import java.util.Set;
import java.util.TreeSet;

public class Item {
	protected Integer id;
	protected Integer max;
	protected Integer min;
	protected Set<Integer> quantities;
	public Item(Integer id) {
		this.id=id;
		quantities = new TreeSet<>();
	}
	@Override
	public String toString() {
		return id+"";
	}
	@Override
	public boolean equals(Object obj) {
		Item other = (Item) obj;
		return id == other.id;
	}
	@Override
	public int hashCode() {
		return id;
	}
}
