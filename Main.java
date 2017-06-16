package bootstrapping;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import makeTriplicity.*;

public class Main {

	private static ArrayList<String> medicineNameList 
	= GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\薬剤名\\medicine_name.txt");
	
	//private static ArrayList<String> keyWordList = SeedSetter.getKeyWordSeedList();
	
	private static ArrayList<String> keyWordList = SeedSetter.getTestKeyWordSeedList();
	
//	private static ArrayList<String> keyWordList 
//	= GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\手がかり語\\keyword_extend.txt");
	
//	private static ArrayList<String> keyWordList 
//	= GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\手がかり語\\keyword_seed.txt");

	private static String keyWordIncreaseFilePath 
	= "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\手がかり語\\keyword_increase.txt";

	private static String keyWordIncreaseFinalFilePath 
	= "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\手がかり語\\keyword_increase_final.txt";

	//private static String seedFilePath = "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\組\\seed.txt";

	public static void main(String[] args) throws Exception {
		
		//RunFromTargetSeed.run(medicineNameList);
		
		Logic.medicineNameList = medicineNameList;
		
		RunFromKeyWordSeed.run(keyWordList, medicineNameList);
		
	}



}
