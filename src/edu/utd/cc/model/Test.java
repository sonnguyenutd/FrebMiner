package edu.utd.cc.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		Item a = new Item(1, "a");
		Item c = new Item(3, "c");
		Item d = new Item(4, "d");
		List<Trans> transes = new ArrayList<Trans>(5);
		for(int i = 1; i <= 5; i++){
			transes.add(new Trans(i));
		}
		Occurrences o = null;
		o = new Occurrences(Arrays.asList(2, 6, 12));
		a.add(transes.get(0), o);
		o = new Occurrences(Arrays.asList(3, 5));
		c.add(transes.get(0), o);
		o = new Occurrences(Arrays.asList(1, 10, 11));
		d.add(transes.get(0), o);
		
		o = new Occurrences(Arrays.asList(12, 16));
		a.add(transes.get(1), o);
		o = new Occurrences(Arrays.asList(13, 15));
		c.add(transes.get(1), o);
		o = new Occurrences(Arrays.asList(10, 11));
		d.add(transes.get(1), o);
		
		o = new Occurrences(Arrays.asList(16));
		a.add(transes.get(2), o);
		o = new Occurrences(Arrays.asList(13, 15));
		c.add(transes.get(2), o);
		o = new Occurrences(Arrays.asList(20));
		d.add(transes.get(2), o);
		
		Node node = new Node();
		node.items = new ArrayList<Item>(Arrays.asList(a, c));
		node.tids = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
		System.out.println(node);
	}

}
