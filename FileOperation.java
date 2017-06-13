package bootstrapping;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import makeTriplicity.Element;
import makeTriplicity.TripleSet;

public class FileOperation {

	public static void writeTextInFile(String keyWord, String filePath, boolean isAdditional) 
			throws UnsupportedEncodingException, FileNotFoundException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter
				(new FileOutputStream(filePath,isAdditional),"utf-8")));
		pw.println(keyWord);
		pw.close();
	}

	//	public static ArrayList<SeedSet> makeSeedList(String filePath) {
	//		
	//		ArrayList<SeedSet> seedList = new ArrayList<SeedSet>();
	//		ArrayList<String> seedTextList = makeTriplicity.GetTextFileList.fileRead(filePath);
	//		
	//		for(String seedText : seedTextList){
	//			//System.out.println(seedText);
	//			if(!seedText.contains(",")){ continue; }
	//			String[] elementArray = seedText.split(",");
	//			seedList.add(new SeedSet(elementArray[0], elementArray[1]));
	//		}
	//		return seedList;
	//	}

	public static ArrayList<TripleSet> makeTripleSetList(String filePath) {

		ArrayList<TripleSet> tripleSetList = new ArrayList<TripleSet>();
		ArrayList<String> seedTextList = makeTriplicity.GetTextFileList.fileRead(filePath);

		for(String seedText : seedTextList){
			//System.out.println(seedText);
			if(!seedText.contains(",")){ continue; }
			String[] elementArray = seedText.split(",");
			TripleSet tripleSet = new TripleSet();
			Element targetElement = new Element();
			Element effectElement = new Element();
			targetElement.setText(elementArray[0]);
			effectElement.setText(elementArray[1]);
			tripleSet.setTargetElement(targetElement);
			tripleSet.setEffectElement(effectElement);
			tripleSetList.add(tripleSet);
			//seedList.add(new SeedSet(elementArray[0], elementArray[1]));
		}
		return tripleSetList;
	}

}
