package edu.utd.cc.bag;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.utd.cc.utils.Utils;

public class Miner {
	// INPUT GENERATOR
	public static int MAX_QUAN_OF_ITEMS = 5;
	public static int NUM_OF_TRANS = 100;
	public static int NUM_OF_ITEMS = 10;

	public static final String INPUT_FILE = "/Users/sonnguyen/Desktop/change bags/repos-junit-bags/junit-team/junit/input_converted.txt";
	// public static final String INPUT_FILE = "/Users/sonnguyen/Desktop/change
	// bags/repos-IntelliJ-bags/JetBrains/intellij-community/input_converted.txt";
	private static final int FULLY_SUBSUMED = 1;
	private static final int PARTIALLY_SUBSUMED = 0;
	public static double SET_FREQUENCY_THRESHOLD = 3;
	public static double BAG_FREQUENCY_THRESHOLD = 3;

	Set<Item> allItems;
	Set<Transaction> trans;
	Set<ITNode> frequentSets;
	Set<ItemBag> frequentBags;

	int executionCounter = 0;

	public static void main(String[] args) {
		Long start = System.currentTimeMillis();
		Miner miner = new Miner();
		miner.mine(Miner.INPUT_FILE);
		Long end = System.currentTimeMillis();
		System.out.println(start - end);
	}

	public void mine(String inputFile) {
		frequentSets = new HashSet<>();
		frequentBags = new HashSet<>();
		trans = Transaction.parseConverted(inputFile);
		allItems = new HashSet<>();
		for (Transaction tran : trans)
			allItems.addAll(tran.getItemSet());

		List<OneITNode> oneNodes = new ArrayList<>();
		for (Item item : allItems) {
			OneITNode node = new OneITNode(item);
			node.extractTranset(trans);
			// The first filter
			if (node.getBaseF() >= BAG_FREQUENCY_THRESHOLD)
				oneNodes.add(node);
		}
		System.out.println("Parsing done...");
		ITNode node = new ITNode();
		oneNodes = sort(node, oneNodes);
		Long start = System.currentTimeMillis();
		solve(node, oneNodes);
		Long endSet = System.currentTimeMillis();
		// --------------------------
		if (!frequentSets.isEmpty()) {
			// Save frequent item sets (families of item bags)
			StringBuffer fis = new StringBuffer();
			// Save frequent item bags
			StringBuffer fib = new StringBuffer();
			for (ITNode n : frequentSets) {
				fis.append(n.getF() + "-" + n.toString()).append("\n");
				System.out.println(n.itemset);
				Set<ItemBag> bs = getFrequentBags(n);
				frequentBags.addAll(bs);
				fib.append(n.getF() + "-" + n.toString()).append("\n");
				fib.append(bs.toString()).append("\n");
				fib.append("--------\n");
				System.out.println("--------");
			}
			Long endBag = System.currentTimeMillis();
			System.out.println("Bag: " + (endBag - endSet));
			String parent = (new File(INPUT_FILE)).getParentFile().getAbsolutePath();
			Utils.write(parent + File.separator + "sets.txt", fis.toString());
			Utils.write(parent + File.separator + "bags.txt", fib.toString());
		}

		System.out.println("SET: " + (endSet - start));
		System.out.println(frequentSets.size() + "---" + frequentBags.size());
	}

	private Set<ItemBag> getFrequentBags(ITNode n) {
		for (Item item : n.itemset) {
			for (Transaction tr : n.transet) {
				int quan = tr.bag.itemquan.get(item);
				item.quantities.add(quan);
			}
		}
		List<Item> itemlist = new ArrayList<Item>(n.itemset);
		Set<ItemBag> bags = new HashSet<ItemBag>();
		Set<ItemBag> result = caculateFrequentBags(itemlist, itemlist.size(), bags, n.transet);
		for (ItemBag itemBag : result) {
			System.out.println(itemBag);
		}
		return result;
	}

	/**
	 * the "size" of current bags is itemlist.size - n The out put is
	 * itemlist.size - n + 1
	 * 
	 * @param itemlist
	 * @param size
	 * @param currBagSet
	 * @param transet
	 */
	private Set<ItemBag> caculateFrequentBags(List<Item> itemlist, int n, Set<ItemBag> currBagSet,
			Set<Transaction> transet) {
		Set<ItemBag> result = new HashSet<>();
		Item it = itemlist.get(n - 1);
		ItemBag b = null;
		if (n == 1) {
			for (Integer quan : it.quantities) {
				b = new ItemBag();
				b.itemset.add(it);
				b.itemquan.put(it, quan);
				int f = getF(b.itemquan, transet);
				if (f >= BAG_FREQUENCY_THRESHOLD) {
					b.f = f;
					result.add(b);
				} else
					break;
			}
			return result;
		} else {
			currBagSet = caculateFrequentBags(itemlist, n - 1, currBagSet, transet);
			for (ItemBag bag : currBagSet) {
				for (Integer quan : it.quantities) {
					b = new ItemBag(bag);
					b.itemset.add(it);
					b.itemquan.put(it, quan);
					int f = getF(b.itemquan, transet);
					if (f >= BAG_FREQUENCY_THRESHOLD) {
						b.f = f;
						result.add(b);
					} else
						break;
				}
			}
			return result;
		}

	}

	private void solve(ITNode currentNode, List<OneITNode> remainingOneNodes) {
		System.out.println(currentNode);
		List<OneITNode> sortedOneNodes = sort(currentNode, remainingOneNodes);
		for (int i = 0; i < sortedOneNodes.size(); i++) {
			OneITNode node = sortedOneNodes.get(i);
			if (node != null) {
				ITNode newNode = currentNode.combine(node);
				// The first condition
				if (!newNode.equals(currentNode) && newNode.getF() >= SET_FREQUENCY_THRESHOLD
						&& newNode.getBaseF() >= BAG_FREQUENCY_THRESHOLD) {
					List<OneITNode> newRemainingNodes = copy(sortedOneNodes, i);
					for (int j = i + 1; j < sortedOneNodes.size(); j++) {
						OneITNode otherOneNode = sortedOneNodes.get(j);
						if (otherOneNode != null) {
							ITNode otherNewNode = currentNode.combine(otherOneNode);
							// Handle some properties
							if (newNode.transet.equals(otherNewNode.transet)) {
								newNode = newNode.combine(otherOneNode);
								newRemainingNodes = removeOneNode(newRemainingNodes, otherOneNode);
								remainingOneNodes.set(j, null);
							} else if (newNode.transet.contains(otherNewNode.transet)) {
								newNode = newNode.combine(otherOneNode);
								newRemainingNodes = removeOneNode(newRemainingNodes, otherOneNode);
							}
						}
					}
					int subsumptionResult = checkSubsumption(newNode);
					if (subsumptionResult != FULLY_SUBSUMED) {
						if (subsumptionResult != PARTIALLY_SUBSUMED) {
							if (newNode.itemset.size() > 1)
								frequentSets.add(newNode);
						}
						solve(newNode, newRemainingNodes);
					}
				}
			}
		}
	}

	private int checkSubsumption(ITNode newNode) {
		int result = -1;
		Iterator<ITNode> itr = frequentSets.iterator();
		while (itr.hasNext()) {
			ITNode n = itr.next();
			if (n.transet.equals(newNode.transet)) {
				if (n.itemset.contains(newNode.itemset) && !n.itemset.equals(newNode.itemset))
					return FULLY_SUBSUMED;
				else if (n.getF() == newNode.getF())
					return PARTIALLY_SUBSUMED;

			}
			if (newNode.itemset.contains(n.itemset) && !newNode.itemset.equals(n.itemset)) {
				if (newNode.getF() == n.getF())
					itr.remove();
			}
		}
		return result;
	}

	private List<OneITNode> removeOneNode(List<OneITNode> newRemainingNodes, OneITNode otherOneNode) {
		List<OneITNode> result = new ArrayList<OneITNode>();
		for (OneITNode oneITNode : newRemainingNodes) {
			if (!oneITNode.equals(otherOneNode))
				result.add(oneITNode);
		}
		return result;
	}

	/**
	 * TODO: Check it
	 * 
	 * @param sortedOneNodes
	 * @param i
	 * @return
	 */
	private List<OneITNode> copy(List<OneITNode> sortedOneNodes, int i) {
		List<OneITNode> cp = new ArrayList<OneITNode>();
		for (int j = i + 1; j < sortedOneNodes.size(); j++) {
			OneITNode n = sortedOneNodes.get(j);
			cp.add(n);
		}
		return cp;
	}

	/**
	 * Sort 1-items (Y) of the current node X by the number of transactions
	 * containing X.combine(Y)
	 * 
	 * @param currentNode
	 * @param nodes
	 * @return
	 */
	private List<OneITNode> sort(ITNode currentNode, List<OneITNode> nodes) {
		List<OneITNode> result = new ArrayList<OneITNode>();
		for (OneITNode oneITNode : nodes) {
			ITNode t = currentNode.combine(oneITNode);
			oneITNode.setTempCounter(t.transet.size());
			result.add(oneITNode);
		}
		Collections.sort(result);
		return result;
	}

	public Integer getF(Map<Item, Integer> bag, Set<Transaction> ts) {
		int bf = 0;
		for (Transaction t : ts) {
			if (t.bag.gteq(bag))
				bf++;
		}
		return bf;
	}
}
