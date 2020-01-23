package edu.utd.cc.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
	List<Item> items;
	List<Integer> tids;

	public Node() {
		items = new ArrayList<Item>();
		tids = new ArrayList<Integer>();
	}
	public int fk(Integer k){
		//TODO: Check input
		int result = Integer.MAX_VALUE;
		for (Item item : items) {
			int t = item.occrs.get(k).size();
			if(t < result){
				result = t;
			}
		}
		return result;
	}
	
	Occurrences get(int i, int k){
		//TODO: Check input
		return items.get(i).occrs.get(k);
	}

	@Override
	public String toString() {
		String result = "";
		String is = "";
		String oss = "";
		for (Item item : items) {
			if (is.isEmpty())
				is += item.toString();
			else
				is += ", " + item.toString();

		}
		for (Integer tid : tids) {
			String os = "";
			for (Item item : items) {
				Occurrences o = item.occrs.get(tid);
				if (os.isEmpty())
					os += ("[" + o.toString() + "]");
				else
					os += (", " + ("[" + o.toString() + "]"));
			}
			os = tid+":["+os+"]";
			if(oss.isEmpty())
				oss+= (os);
			else
				oss+=", " + os;
		}

		is = "[" + is + "]";
		oss = "[" + oss + "]";
		result = is + " : " + oss;
		return result;
	}
}
