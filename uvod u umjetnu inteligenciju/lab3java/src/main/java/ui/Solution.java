package ui;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public class Solution {

	public static void main(String ... args) throws IOException
	{
		String trainPath = args[0];
		String testPath = args[1];
		boolean limit = false;

		LinkedHashMap<String, List<String>> trainMap = DataLoader.loadMapFromFile(trainPath);
		LinkedHashMap<String, List<String>> testMap = DataLoader.loadMapFromFile(testPath);

		int treeLimit = 0;
		if (args.length == 3)
		{
			limit = true;
			treeLimit = Integer.parseInt(args[2]);
		}

		ID3 model;

		if(limit)
		{
			model = new ID3(treeLimit); //inicijaliziram model s ogranicenom dubinom
		}
		else
		{
			model = new ID3(); //model
		}
		model.fit(trainMap);
		model.predict(testMap);

	}

}
