package edu.utd.cc.bag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.utd.cc.utils.Utils;

public class BagMiner {

	public static void main(String[] args) {
		String fsTempFile = "/Users/sonnguyen/Desktop/change bags/repos-junit-bags/junit-team/junit/sets_f_3.txt";

		Set<ItemBag> frequentBags = new HashSet<>();
		Set<Transaction> trans = Transaction.parseConverted(Miner.INPUT_FILE);
		Set<Item> allItems = new HashSet<>();
		for (Transaction tran : trans)
			allItems.addAll(tran.getItemSet());
		List<ITNode> frequentSets = extractFS(fsTempFile, trans);
		if (!frequentSets.isEmpty()) {
			// Save frequent item bags
			StringBuffer fib = new StringBuffer();
			String parent = (new File(Miner.INPUT_FILE)).getParentFile().getAbsolutePath();
			for (int i = 55000; i < frequentSets.size(); i++) {
				ITNode n = frequentSets.get(i);
				System.out.println(i);
				System.out.println(n.itemset);
				Set<ItemBag> bs = getFrequentBags(n);
				frequentBags.addAll(bs);
				fib.append(n.getF() + "-" + n.toString()).append("\n");
				fib.append(bs.toString()).append("\n");
				fib.append("--------\n");
				if(i%5000 == 0){
					Utils.write(parent + File.separator + "bags_bk.txt", fib.toString());
				}
			}
			Utils.write(parent + File.separator + "bagsxxx.txt", fib.toString());
		}
	}

	private static List<ITNode> extractFS(String fsTempFile, Set<Transaction> trans) {
		List<ITNode> fs = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fsTempFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
					int s = line.indexOf("-");
					line = line.substring(s + 1, line.length()).replace("[", "").replace("]", "");
					String[] parts = line.split(":");
					if (parts.length == 2) {
						ITNode node = new ITNode();
						Set<Item> itset = getItemSet(parts[0]);
						Set<Transaction> trset = getTranSet(parts[1], trans);
						node.itemset = itset;
						node.transet = trset;
						fs.add(node);
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fs;
	}

	private static Set<Transaction> getTranSet(String trs, Set<Transaction> trans) {
		Set<Transaction> trset = new HashSet<>();
		String[] ts = trs.split(",");
		for (String t : ts) {
			t = t.trim();
			Transaction tr = findTranByID(Integer.parseInt(t), trans);
			if (tr == null)
				System.err.println("ERROR");
			trset.add(tr);
		}
		return trset;
	}

	private static Transaction findTranByID(int parseInt, Set<Transaction> trans) {
		for (Transaction t : trans) {
			if (t.tid.equals(parseInt))
				return t;
		}
		return null;
	}

	private static Set<Item> getItemSet(String its) {
		Set<Item> s = new HashSet<>();
		String[] itt = its.split(",");
		for (String it : itt) {
			Item i = new Item(Integer.parseInt(it.trim()));
			s.add(i);
		}
		return s;
	}

	private static Set<ItemBag> getFrequentBags(ITNode n) {
		for (Item item : n.itemset) {
			for (Transaction tr : n.transet) {
				int quan = tr.bag.itemquan.get(item);
				item.quantities.add(quan);
			}
		}
		List<Item> itemlist = new ArrayList<Item>(n.itemset);
		Set<ItemBag> bags = new HashSet<ItemBag>();
		Set<ItemBag> result = caculateFrequentBags(itemlist, itemlist.size(), bags, n.transet);
		return result;
	}

	private static Set<ItemBag> caculateFrequentBags(List<Item> itemlist, int n, Set<ItemBag> currBagSet,
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
				if (f >= Miner.BAG_FREQUENCY_THRESHOLD) {
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
					if (f >= Miner.BAG_FREQUENCY_THRESHOLD) {
						b.f = f;
						result.add(b);
					} else
						break;
				}
			}
			return result;
		}

	}

	public static Integer getF(Map<Item, Integer> bag, Set<Transaction> ts) {
		int bf = 0;
		for (Transaction t : ts) {
			if (t.bag.gteq(bag))
				bf++;
		}
		return bf;
	}

}
