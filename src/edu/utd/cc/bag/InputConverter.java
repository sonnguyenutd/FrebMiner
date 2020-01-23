package edu.utd.cc.bag;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.utd.cc.utils.Utils;

public class InputConverter {
	static Map<String, Item> info_items = new HashMap<>();
	static String SEPARATOR = "\\|\\|\\|";
	static String ITEM_SEPARATOR = ":";
	static Integer tid = 1;
	static Integer iid = 1;

	public static void main(String[] args) {
		String raw = "/Users/sonnguyen/Desktop/change bags/repos-IntelliJ-bags/JetBrains/intellij-community/transaction-bags.txt";
		// String raw = "/Users/sonnguyen/Desktop/change
		// bags/repos-junit-bags/junit-team/junit/test.txt";
		String converted = "/Users/sonnguyen/Desktop/change bags/repos-IntelliJ-bags/JetBrains/intellij-community/input_converted.txt";
		String mapping = "/Users/sonnguyen/Desktop/change bags/repos-IntelliJ-bags/JetBrains/intellij-community/mapping.txt";
		StringBuffer transText = new StringBuffer();
		try (BufferedReader br = new BufferedReader(new FileReader(raw))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
					Transaction t = extractTraction(line);
					transText.append(t.toString()).append("\n");
				}
			}
			Utils.write(converted, transText.toString());
			String mappingText = "";
			for (String info : info_items.keySet()) {
				mappingText += info_items.get(info).id + "-->" + info + "\n";
			}
			Utils.write(mapping, mappingText);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Transaction extractTraction(String line) {
		Transaction t = new Transaction(tid++);
		String[] parts = line.split(SEPARATOR);
		if (parts.length >= 2) {
			t.info = parts[0];
			for (int i = 1; i < parts.length; i++) {
				Item it = extractItem(parts[i]);
				if (it != null) {
					t.bag.itemset.add(it);
					int quan = 1;
					if (t.bag.itemquan.containsKey(it))
						quan = t.bag.itemquan.get(it) + 1;
					t.bag.itemquan.put(it, quan);
				}
			}
		}
		return t;
	}

	private static Item extractItem(String text) {
		String[] parts = text.split(ITEM_SEPARATOR);
		if (parts.length == 4) {
			String info = parts[0] + ITEM_SEPARATOR + parts[1];
			Item item = info_items.get(info);
			if (item == null) {
				item = new Item(iid++);
				item.info = info;
				info_items.put(info, item);
			}
			return item;
		}
		return null;
	}

}
