package edu.utd.cc.set;

import java.util.HashSet;
import java.util.Set;

import edu.utd.cc.utils.Utils;

/**
 * A transaction contains an identification number and a bag of items
 * 
 * @author sonnguyen
 *
 */
public class Transaction {
	private static final String ITEM_SEPERATOR = ",";

	private static final String ITEM_QUAN_SEPERATOR = ":";

	Integer tid;
	ItemBag bag;
	
	public static void main(String[] args) {
		Set<Transaction> trans = Transaction.parse(Miner.INPUT_FILE);
		Set<Item> allItems = new HashSet<Item>();
		for (Transaction tran : trans) {
			System.out.println(tran);
			for (Item item : tran.getItemSet()) {
				allItems.add(item);
			}
		}
		System.out.println(allItems.size());
	}
	
	@Override
	public boolean equals(Object obj) {
		return tid.equals(((Transaction)obj).tid);
	}

	public Transaction() {
		bag = new ItemBag();
	}

	public Transaction(Integer id) {
		this.tid = id;
		bag = new ItemBag();
	}
	
	public void setBag(ItemBag bag) {
		this.bag = bag;
	}

	/**
	 * A quantitative transaction database contains a list of transactions
	 * 
	 * @param filePath
	 * @return
	 */
	public static Set<Transaction> parse(String filePath) {
		Set<Transaction> database = new HashSet<>();
		String txt = Utils.read(filePath).trim();
		String[] transTxt = txt.split("\n");
		int i = 0;
		// For each transaction
		for (String t : transTxt) {
			Transaction tran = new Transaction(i++);
			ItemBag bag = new ItemBag();
			String[] qitems = t.trim().split(ITEM_SEPERATOR);
			// For each item in the transaction
			for (String qitem : qitems) {
				String[] iq = qitem.split(ITEM_QUAN_SEPERATOR);
				if (iq.length == 2) {
					try {
						Item it = new Item(Integer.parseInt(iq[0]));
						Integer quan = Integer.parseInt(iq[1]);
						bag.add(it, quan);
					} catch (NumberFormatException e) {e.printStackTrace();}
				}
			}
			tran.setBag(bag);
			database.add(tran);
		}

		return database;
	}

	@Override
	public String toString() {
		return tid+":"+bag.toString();
	}
	
	public Set<Item> getItemSet(){
		return this.bag.itemset;
	}
	public int getF(Item i) {
		if(!bag.itemset.contains(i))
			return 0;
		return bag.itemquan.get(i);
	}
	@Override
	public int hashCode() {
		return tid;
	}
}
