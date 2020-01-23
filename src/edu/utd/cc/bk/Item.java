package edu.utd.cc.bk;

import java.util.HashMap;
import java.util.Map;

public class Item {
	protected Integer id;
	protected Map<Integer, Integer> tidset;
	//For sorting
	protected int tempCounter = 0;
	public Item() {
		tidset = new HashMap<Integer, Integer>();
	}
	public Item(Integer id) {
		tidset = new HashMap<Integer, Integer>();
		this.id = id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setTidset(Map<Integer, Integer> tidset) {
		this.tidset = tidset;
	}
	public void addTid(Integer tid, Integer f){
		this.tidset.put(tid, f);
	}
	@Override
	public boolean equals(Object obj) {
		return ((Item)obj).id == this.id;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
