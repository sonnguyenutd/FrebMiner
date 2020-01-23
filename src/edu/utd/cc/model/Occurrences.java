package edu.utd.cc.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Occurrences extends ArrayList<Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Occurrences() {
	}
	public Occurrences(Collection<? extends Integer> c) {
		super(c);
	}
	@Override
	public String toString() {
		String result = "";
		Iterator<Integer> i = iterator();
		while (i.hasNext()) {
			if(result.isEmpty())
				result += (i.next());
			else
				result += ("," + (i.next()));
		}
		return result;
	}
}
