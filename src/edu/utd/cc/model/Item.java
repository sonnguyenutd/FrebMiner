package edu.utd.cc.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Item {
	protected Integer id;
	protected Set<Trans> transes;
	protected Map<Integer, Occurrences> occrs;
	protected String content;
	public Item() {
		transes = new HashSet<Trans>();
		occrs = new HashMap<Integer, Occurrences>();
	}
	public Item(Integer id, String content){
		this.id = id;
		this.content = content;
		transes = new HashSet<Trans>();
		occrs = new HashMap<Integer, Occurrences>();
	}
	
	@Override
	public String toString() {
		String result = "";
		result = content+"";
		return result;
	}
	public void add(Trans trans, Occurrences o){
		this.transes.add(trans);
		occrs.put(trans.id, o);
	}
}
