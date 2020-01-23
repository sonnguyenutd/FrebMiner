package edu.utd.cc.set;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Miner {
	//INPUT GENERATOR
	public static int MAX_QUAN_OF_ITEMS = 10;
	public static int NUM_OF_TRANS = 10;
	public static int NUM_OF_ITEMS = 10;
	
	public static final String INPUT_FILE = "/Users/sonnguyen/Desktop/input.txt";
	
	private static final int FULLY_SUBSUMED = 1;
	private static final int PARTIALLY_SUBSUMED = 0;
	public static int ABSOLUTE_FREQUENCY = 10;
	public static int DYNAMIC_THRESHOLD = 15;
	
	Set<Item> allItems;
	Set<Transaction> trans;
	Set<ITNode> frequentPatterns;
	int executionCounter = 0;
	

	public static void main(String[] args) {
		Miner miner = new Miner();
		miner.mine(Miner.INPUT_FILE);
		System.out.println(miner.frequentPatterns.size());
		for (ITNode n : miner.frequentPatterns) {
			System.out.println(n);
		}
		
	}

	public void mine(String inputFile) {
		frequentPatterns = new HashSet<>();
		trans = Transaction.parse(inputFile);
		allItems = new HashSet<>();
		for (Transaction tran : trans)
			allItems.addAll(tran.getItemSet());
		List<OneITNode> oneNodes = new ArrayList<>();
		for (Item item : allItems) {
			OneITNode node = new OneITNode(item);
			node.extractTranset(trans);
			oneNodes.add(node);
		}
		ITNode node = new ITNode();
		oneNodes = sort(node, oneNodes);
		solve(node, oneNodes);
	}

	private void solve(ITNode currentNode, List<OneITNode> remainingOneNodes) {
		executionCounter++;
		List<OneITNode> sortedOneNodes = sort(currentNode, remainingOneNodes);
		for (int i = 0; i < sortedOneNodes.size(); i++) {
			OneITNode node = sortedOneNodes.get(i);
			if (node != null) {
				ITNode newNode = currentNode.combine(node);
				// The first condition
				if (newNode.getF() >= ABSOLUTE_FREQUENCY) {
					List<OneITNode> newRemainingNodes = copy(sortedOneNodes);
					for (int j = i + 1; j < sortedOneNodes.size(); j++) {
						OneITNode otherOneNode = sortedOneNodes.get(j);
						if (otherOneNode != null) {
							// TODO: in the pseudocode:
							// newNode.combine(otherNode).
							// However, in the paper,
							// currentNode.combine(otherNode)
							// is correct
							ITNode otherNewNode = currentNode.combine(otherOneNode);
							// System.out.println(newNode + "-" +otherNewNode);

							// Handle some propoties
							if (newNode.transet.equals(otherNewNode.transet)
									&& newNode.compareFD(otherNewNode) == ITNode.FD_EQUIVALENT) {
								newNode = newNode.combine(otherOneNode);
								newRemainingNodes = removeOneNode(newRemainingNodes, otherOneNode);
								remainingOneNodes.set(j, null);
							}
							if ((newNode.transet.equals(otherNewNode.transet)
									|| newNode.transet.contains(otherNewNode.transet))
									&& otherNewNode.compareFD(newNode) == ITNode.FD_MORE_POWERFUL) {
								newNode = newNode.combine(otherOneNode);
								newRemainingNodes = removeOneNode(newRemainingNodes, otherOneNode);
							}
						}
					}
					if (newNode.getF() * newNode.itemset.size() > DYNAMIC_THRESHOLD) {
						int subsumptionResult = checkSubsumption(newNode);
						if (subsumptionResult != FULLY_SUBSUMED) {
							if (subsumptionResult != PARTIALLY_SUBSUMED) {
								frequentPatterns.add(newNode);
							}
							solve(newNode, newRemainingNodes);
						}
					}
				}
			}
		}
	}

	private int checkSubsumption(ITNode newNode) {
		int result = -1;
		Iterator<ITNode> itr = frequentPatterns.iterator();
		while (itr.hasNext()) {
			ITNode n = itr.next();
			if (n.transet.equals(newNode.transet)) {
				if (n.itemset.contains(newNode.itemset) && !n.itemset.equals(newNode.itemset)) {
					int comparedValue = n.compareFD(newNode);
					if (comparedValue == ITNode.FD_MORE_POWERFUL)
						return FULLY_SUBSUMED;
					else if (n.getF() == newNode.getF())
						return PARTIALLY_SUBSUMED;
				}
				if (newNode.itemset.contains(n.itemset) && !newNode.itemset.equals(n.itemset)) {
					if (newNode.getF() == n.getF()) {
						itr.remove();
					}
				}
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
	 * @return
	 */
	private List<OneITNode> copy(List<OneITNode> sortedOneNodes) {
		List<OneITNode> cp = null;
		for (OneITNode n : sortedOneNodes) {
			if (cp == null)
				cp = new ArrayList<OneITNode>();
			else
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
}
