package edu.utd.cc.bk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Minner {
	private static final int ABSOLUTE_FREQUENCY = 0;
	private static final int DYNAMIC_THRESHOLD = 0;
	
	private static final int SECOND_MORE_POWERFUL = 0;
	private static final int EQUIVALENT = 1;
	private static final int FULLY_SUBSUMED = 1;
	private static final int PARTIALLY_SUBSUMED = 0;
	private static final int UNK = -1;
	List<ItemBag> frequentPatterns;
	TransactionDatabase td;
	
	public static void main(String[] args){
		Minner minner = new Minner();
		String codeChangesSequence;
		String dbFile = "";
		TransactionDatabase td = Utils.getTransactionDB(dbFile );
		minner.setTd(td);
//		minner.mine(codeChangesSequence);
	}

	public void setTd(TransactionDatabase td) {
		this.td = td;
	}
	void mine(String codeChangesSequence) {
		ItemBag emptySet = null;
		frequentPatterns = new ArrayList<ItemBag>();
		List<Item> inputItems = getInputItems();
		solve(emptySet, inputItems);
	}

	private void solve(ItemBag currentItemset, List<Item> remainingItems) {
		List<Item> remainingSortedItems = sort(currentItemset, remainingItems);
		while (!remainingSortedItems.isEmpty()) {
			Item nextItem = remainingSortedItems.get(0);
			ItemBag newItemset = addItem(currentItemset, nextItem);
			if (newItemset.getF() >= ABSOLUTE_FREQUENCY) {
				// COPY
				List<Item> newRemainingItems = new ArrayList<Item>(remainingSortedItems);
				for (int j = 0; j < remainingSortedItems.size(); j++) {
					Item forwardItem = remainingSortedItems.get(j);
					int freqCompResult = compare(newItemset.tidset, (addItem(newItemset, forwardItem)).tidset);
					if (freqCompResult == EQUIVALENT || freqCompResult == SECOND_MORE_POWERFUL) {
						newItemset = addItem(newItemset, forwardItem);
						newRemainingItems.remove(forwardItem);
						if (freqCompResult == EQUIVALENT) {
							remainingSortedItems.remove(forwardItem);
						}
					}
				}
				if (newItemset.getF() * newItemset.itemset.size() >= DYNAMIC_THRESHOLD) {
					int subsumptionResult = checkSubsumption(newItemset);
					if (subsumptionResult != FULLY_SUBSUMED) {
						if (subsumptionResult != PARTIALLY_SUBSUMED) {
							frequentPatterns.add(newItemset);
						}
						solve(newItemset, newRemainingItems);
					}
				}
			}
		}

	}

	private List<Item> sort(ItemBag currentItemset, List<Item> remainingItems) {
		ItemBag temp = null;
		for (Item item : remainingItems) {
			temp = addItem(currentItemset, item);
			item.tempCounter = temp.getF();
		}
		Collections.sort(remainingItems, (left, right) -> right.tempCounter - left.tempCounter);
		return remainingItems;
	}

	private int checkSubsumption(ItemBag X) {
		for (ItemBag Y : frequentPatterns) {
			if (Y.itemset.containsAll(X.itemset)) {
				if (compare(Y.tidset, X.tidset) == SECOND_MORE_POWERFUL) {
					return FULLY_SUBSUMED;
				} else if(Y.getF()==X.getF()){
					return PARTIALLY_SUBSUMED;
				}
			}
		}
		return UNK;
	}

	private int compare(Map<Integer, Integer> f1, Map<Integer, Integer> f2) {
		if (f1.equals(f2))
			return EQUIVALENT;
		if (f1.keySet().equals(f2) || f1.keySet().containsAll(f2.keySet())) {
			for (Integer tid : f2.keySet()) {
				if (f1.get(tid) < f2.get(tid))
					return UNK;
			}
		}
		return SECOND_MORE_POWERFUL;
	}

	private ItemBag addItem(ItemBag currentItemset, Item nextItem) {
		ItemBag result = new ItemBag(currentItemset);
		result.add(nextItem);
		return result;
	}

	private List<Item> getInputItems() {
		
		return null;
	}
}
