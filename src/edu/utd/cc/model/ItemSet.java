package edu.utd.cc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSet extends ArrayList<Item> {
	private static final long serialVersionUID = 1L;
	// tid-->fk()
	protected Map<Integer, Integer> tidset;

	public ItemSet() {
		super();
		tidset = new HashMap<Integer, Integer>();
	}

	public ItemSet(List<Item> currentItemset) {
		super(currentItemset);
		tidset = new HashMap<Integer, Integer>();
	}

	@Override
	public boolean add(Item e) {
		return super.add(e);
	}

}
