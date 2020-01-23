package edu.utd.cc.bag;

import java.util.HashSet;
import java.util.Set;

public class TestITNode {

	public static void main(String[] args) {
		Set<Transaction> trans = Transaction.parse(Miner.INPUT_FILE);
		Set<Item> allItems = new HashSet<>();
		for (Transaction tran : trans) {
			System.out.println(tran);
			allItems.addAll(tran.getItemSet());
		}
		Set<ITNode> oneNodes = new HashSet<>();
		for (Item item : allItems) {
			OneITNode node = new OneITNode(item);
			node.extractTranset(trans);
			oneNodes.add(node);
		}
		Set<ITNode> twoNodes = new HashSet<>();
		for (ITNode n : oneNodes) {
			for (ITNode m : oneNodes) {
				if (!m.equals(n)) {
					ITNode newIt = n.combine(m);
					if (!newIt.transet.isEmpty())
						twoNodes.add(newIt);
				}
			}
			System.out.println(n.toString());
		}
		Set<ITNode> threeNodes = new HashSet<>();
		for (ITNode n : twoNodes) {
			if (!n.transet.isEmpty()) {
				System.out.println(n.toString());
				for (ITNode m : oneNodes) {
					ITNode newIt = n.combine(m);
					if (!newIt.transet.isEmpty())
						threeNodes.add(newIt);
				}
			}
		}
		for (ITNode n : threeNodes) {
			// if (!n.transet.isEmpty())
			System.out.println(n.toString());
		}
	}

}
