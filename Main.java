package bootstrapping;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import makeTriplicity.*;

public class Main {
	
	private static ArrayList<String> medicineNameList 
					= GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_name.txt");
	
	private static String keyWordIncreaseFilePath 
					= "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\手がかり語\\keyword_increase.txt";
	
	private static String keyWordIncreaseFinalFilePath 
					= "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\手がかり語\\keyword_increase_final.txt";
	
	private static String seedFilePath = "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\組\\seed_id0-500.txt";

	public static void main(String[] args) throws Exception {
		
		ArrayList<DoubleSet> seedList = FileOperation.makeSeedList(seedFilePath);
		
		//ArrayList<TripleSet> tripleSetList = makeTriplicity.Main.run(0, 500);
		ArrayList<Integer> idList = new ArrayList<Integer>();
		idList.add(2);
		idList.add(8);
		idList.add(19);
		idList.add(65);
		idList.add(115);
		idList.add(125);
		idList.add(167);
		idList.add(185);
		idList.add(194);
		idList.add(204);
		idList.add(225);
		idList.add(226);
		idList.add(257);
		idList.add(272);
		idList.add(298);
		idList.add(335);
		idList.add(350);
		idList.add(372);
		idList.add(377);
		idList.add(390);
		idList.add(412);
		idList.add(422);
		idList.add(460);
		idList.add(480);
		idList.add(482);
		idList.add(485);
		
		//ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(500, 1000);
		ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(idList);
		ArrayList<String> keyWordTextList = new ArrayList<String>();
		ArrayList<DoubleSet> doubleSetList = new ArrayList<DoubleSet>();
		double threshold = 1;
		
		for(DoubleSet seed : seedList){
			
			ArrayList<KeyWord> keyWordList = new ArrayList<KeyWord>();
			String target = seed.getTarget();
			String effect = seed.getEffect();
			System.out.println(target + " , " + effect);
			keyWordList = GetKeyWordList.getKeyWordList(medicineNameList, sentenceList, target, effect);
			if(keyWordList.size() == 0){ continue; }
			
			for(KeyWord keyWord : keyWordList){
				keyWordTextList.add(keyWord.getKeyWordText());
				//System.out.println(keyWord.getKeyWordText());
			}
			
			DoubleSet doubleSet = new DoubleSet(target, effect);
			doubleSet.setKeyWordList(keyWordList);
			doubleSetList.add(doubleSet);
		}
		
		//for(TripleSet tripleSet : tripleSetList){
			
		//}
		
		ArrayList<String> keyWordTextListNoDouble = new ArrayList<String>(new LinkedHashSet<>(keyWordTextList));
		
		//手がかり語のエントロピー計算
		for(String keyWordText : keyWordTextListNoDouble){
			double entropy = 0;
			int keyWordTextAllNum = 0;
			int keyWordTextNum = 0;
			ArrayList<Integer> keyWordNumList = new ArrayList<Integer>();
			
			for(DoubleSet doubleSet : doubleSetList){
				keyWordTextNum = doubleSet.getKeyWordNum(keyWordText);
				keyWordNumList.add(keyWordTextNum);
				keyWordTextAllNum += keyWordTextNum;
			}
			
			entropy = EntropyCalculator.caluculateEntropy(keyWordNumList, keyWordTextAllNum);
			System.out.println(keyWordText + "　→　" + entropy);
			//if(entropy > threshold){
			FileOperation.writeTextInFile(keyWordText, keyWordIncreaseFilePath, false);
			FileOperation.writeTextInFile(keyWordText, keyWordIncreaseFinalFilePath, true);
			//}
		}
		
		
		
		
		
//		for(String keyWordText : keyWordTextList){
//			System.out.println(keyWordText);
//		}

	}
	
	

}
