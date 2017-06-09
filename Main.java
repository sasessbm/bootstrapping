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
	
	private static String keyWordIncreaseFilePath 
					= "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\手がかり語\\keyword_increase.txt";
	
	private static String keyWordIncreaseFinalFilePath 
					= "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\手がかり語\\keyword_increase_final.txt";
	
	private static String seedFilePath = "C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\組\\seed.txt";

	public static void main(String[] args) throws Exception {
		
		ArrayList<SeedSet> seedSetList = FileOperation.makeSeedList(seedFilePath);
		
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
		
		//ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(500, 1000, medicineNameList);
		ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(idList, medicineNameList);
		ArrayList<String> keyWordTextList = new ArrayList<String>();
		ArrayList<String> keyWordTmpList = new ArrayList<String>();
		//ArrayList<SeedSet> seedSetList = new ArrayList<SeedSet>();
		double threshold = 1;
		
		//手がかり語取得
		for(SeedSet seedSet : seedSetList){
			ArrayList<KeyWord> keyWordList = new ArrayList<KeyWord>();
			String target = seedSet.getTarget();
			String effect = seedSet.getEffect();
			System.out.println(target + " , " + effect);
			keyWordList = GetKeyWordList.getKeyWordList(medicineNameList, sentenceList, target, effect);
			if(keyWordList == null){ continue; }
			
			for(KeyWord keyWord : keyWordList){
				keyWordTextList.add(keyWord.getKeyWordText());
			}
			
			seedSet.setKeyWordList(keyWordList);
		}
		
		ArrayList<String> keyWordTextListNoDouble = new ArrayList<String>(new LinkedHashSet<>(keyWordTextList));
		
		//手がかり語のエントロピー計算
		for(String keyWordText : keyWordTextListNoDouble){
			double entropy = 0;
			int keyWordTextAllNum = 0;
			int keyWordTextNum = 0;
			ArrayList<Integer> keyWordNumList = new ArrayList<Integer>();
			
			for(SeedSet seedSet : seedSetList){
				keyWordTextNum = seedSet.getKeyWordNum(keyWordText);
				keyWordNumList.add(keyWordTextNum);
				keyWordTextAllNum += keyWordTextNum;
			}
			
			entropy = EntropyCalculator.caluculateEntropy(keyWordNumList, keyWordTextAllNum);
			System.out.println(keyWordText + "　→　" + entropy);
			//if(entropy > threshold){
			//FileOperation.writeTextInFile(keyWordText, keyWordIncreaseFilePath, true);
			keyWordTmpList.add(keyWordText);
			FileOperation.writeTextInFile(keyWordText, keyWordIncreaseFinalFilePath, true);
			//}
		}
		
		//三つ組取得
		for(Sentence sentence : sentenceList){
			ArrayList<Phrase> phraseReplaceList = sentence.getPhraseReplaceList();
			ArrayList<Phrase> phraseRestoreList = sentence.getPhraseRestoreList();
			ArrayList<TripleSetInfo> tripleSetInfoList 
						= SearchElementPhrase.getTripleSetInfoList(phraseReplaceList, keyWordTmpList);
			ArrayList<TripleSet> tripleSetList 
						= GetTripleSetList.getTripleSetList(tripleSetInfoList, phraseRestoreList, medicineNameList);
			for(TripleSet tripleSet : tripleSetList){
				PostProcessing.deleteParentheses(tripleSet);
				System.out.println
				(tripleSet.getMedicineName()+","+tripleSet.getTargetElement().getText()+","+tripleSet.getEffectElement().getText());
			}
			
			
		}
		
		
//		for(String keyWordText : keyWordTextList){
//			System.out.println(keyWordText);
//		}

	}
	
	

}
