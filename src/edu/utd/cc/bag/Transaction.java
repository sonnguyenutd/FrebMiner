package edu.utd.cc.bag;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	protected String info;
	protected Integer tid;
	public ItemBag bag;

	public static void main(String[] args) {
		Set<Transaction> trans = Transaction.parseConverted("/Users/sonnguyen/Desktop/change bags/repos-junit-bags/junit-team/junit/input_converted.txt");
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
		return tid.equals(((Transaction) obj).tid);
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
						it.quantities.add(quan);
						bag.add(it, quan);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			}
			tran.setBag(bag);
			database.add(tran);
		}

		return database;
	}

	@Override
	public String toString() {
		return tid + ":" + bag.toString();
	}

	public Set<Item> getItemSet() {
		return this.bag.itemset;
	}

	public int getF(Item i) {
		if (!bag.itemset.contains(i))
			return 0;
		return bag.itemquan.get(i);
	}

	@Override
	public int hashCode() {
		return tid;
	}

	public static Set<Transaction> parseConverted(String filePath) {
		Set<Transaction> database = new HashSet<>();
		// For each transaction
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String t;
			while ((t = br.readLine()) != null) {
				if (!t.isEmpty()) {
					Transaction tran = new Transaction();
					ItemBag bag = new ItemBag();
					String[] parts = t.split(":");
					tran.tid = Integer.parseInt(parts[0].trim());

					String[] qitems = parts[1].split(" |\\,|\\{|\\}");
					for (String qitem : qitems) {
						String[] iq = qitem.split("=");
						if (iq.length == 2) {
							try {
								Item it = new Item(Integer.parseInt(iq[0]));
								Integer quan = Integer.parseInt(iq[1]);
								it.quantities.add(quan);
								bag.add(it, quan);
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
					tran.setBag(bag);
					database.add(tran);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return database;
	}
}
