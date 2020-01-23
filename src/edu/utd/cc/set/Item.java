package edu.utd.cc.set;

public class Item {
	protected Integer id;
	
	public Item(Integer id) {
		this.id=id;
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
