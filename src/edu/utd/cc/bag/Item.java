package edu.utd.cc.bag;

import java.util.Set;
import java.util.TreeSet;

public class Item {
	protected String info;
	protected Integer id;
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
