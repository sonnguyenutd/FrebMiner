package edu.utd.cc.model;

import java.util.List;

import edu.utd.cc.set.Transaction;

public class Minner {
	private static final int ABSOLUTE_FREQUENCY = 0;
	private static final int SECOND_MORE_POWERFUL = 0;
	private static final int EQUIVALENT = 0;
	private static final int DYNAMIC_THRESHOLD = 0;
	private static final int FULLY_SUBSUMED = 0;
	private static final int PARTIALLY_SUBSUMED = 0;
	List<ItemSet> frequentPatterns;
	Transaction td;

	void mine(String codeChangesSequence) {
		ItemSet emptySet = null;
		ItemSet inputItems = getInputItems();
		solve(emptySet, inputItems);
	}

	private void solve(ItemSet currentItemset, ItemSet remainingItems) {
		ItemSet remainingSortedItems = sort(currentItemset, remainingItems);
		while (!remainingSortedItems.isEmpty()) {
			Item nextItem = remainingSortedItems.get(0);
			ItemSet newItemset = addItem(currentItemset, nextItem);
			if (getFrequency(newItemset) >= ABSOLUTE_FREQUENCY) {
				ItemSet newRemainingItems = copy(remainingSortedItems);
				for (Item forwardItem : remainingSortedItems) {
					int freqCompResult = compare(freqDescr(newItemset), freqDescr(addItem(newItemset, forwardItem)));
					if (freqCompResult == EQUIVALENT || freqCompResult == SECOND_MORE_POWERFUL) {
						newItemset = addItem(newItemset, forwardItem);
						removeItem(newRemainingItems, forwardItem);
						if (freqCompResult == EQUIVALENT) {
							removeItem(remainingSortedItems, forwardItem);
						}
					}
				}
				if (getFrequency(newItemset) * getSize(newItemset) >= DYNAMIC_THRESHOLD) {
					int subsumptionResult = checkSubsumption(newItemset);
					if (subsumptionResult != FULLY_SUBSUMED) {
						if (subsumptionResult != PARTIALLY_SUBSUMED) {
							addItemset(frequentPatterns, newItemset);
						}
						solve(newItemset, newRemainingItems);
					}
				}
			}
		}

	}

	private void addItemset(List<ItemSet> frequentPatterns2, ItemSet newItemset) {
		// TODO Auto-generated method stub
	}

	private ItemSet sort(ItemSet currentItemset, ItemSet remainingItems) {
		// TODO Auto-generated method stub
		return null;
	}

	private int checkSubsumption(ItemSet newItemset) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getSize(List<Item> newItemset) {
		// TODO Auto-generated method stub
		return 0;
	}

	private void removeItem(List<Item> newRemainingItems, Item forwardItem) {
		// TODO Auto-generated method stub

	}

	private int compare(Object freqDescr, Object freqDescr2) {
		// TODO Auto-generated method stub
		return 0;
	}

	private Object freqDescr(List<Item> newItemset) {
		// TODO Auto-generated method stub
		return null;
	}

	private ItemSet copy(ItemSet remainingSortedItems) {
		// TODO Auto-generated method stub
		return null;
	}

	private int getFrequency(List<Item> newItemset) {
		// TODO Auto-generated method stub
		return 0;
	}

	private ItemSet addItem(ItemSet currentItemset, Item nextItem) {
		// TODO Auto-generated method stub
		return null;
	}

	private ItemSet getInputItems() {
		// TODO Auto-generated method stub
		return null;
	}
}
