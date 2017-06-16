package bootstrapping;

import java.util.ArrayList;

import makeTriplicity.*;

public class Test {
	
	private static ArrayList<String> medicineNameList 
	= GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\薬剤名\\medicine_name.txt");

	public static void main(String[] args) throws Exception {
		
		Logic.medicineNameList = medicineNameList;
		ArrayList<Sentence> sentenceList = GetSentence.getRandomSentenceList(10, 1, 20, medicineNameList);
		
		for(Sentence sentence : sentenceList){
			
			System.out.println("\r\nID = "+ sentence.getId());
			System.out.println(sentence.getText());
			
		}

//		ArrayList<Integer> randomIdList = Logic.getRandomIdList(10, 1, 20);
//
//		ArrayList<Record> recordList = GetRecord.getRecordList(randomIdList);
//
//		for(Record record : recordList){
//			System.out.println("\r\n"+record.getId());
//			System.out.println("\r\n"+record.getSnippet().getSnippetText());
//		}
//		

		//		ArrayList<TripleSet> tripleSetIncreaseList = new ArrayList<TripleSet>();
		//		TripleSet tripleSet = new TripleSet();
		//		Element targetElement = new Element();
		//		Element effectElement = new Element();
		//		targetElement.setText("目");
		//		effectElement.setText("覚めるので");
		//		tripleSet.setTargetElement(targetElement);
		//		tripleSet.setEffectElement(effectElement);
		//		tripleSet.setMedicineName("アモキサン");
		//		tripleSetIncreaseList.add(tripleSet);
		//		
		//		
		//		ArrayList<String> medicineNameList 
		//				= GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\ブートストラップ\\薬剤名\\medicine_name.txt");
		//		Logic.medicineNameList = medicineNameList;
		//		ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(3128, 3128, medicineNameList);
		//		
		//		//三つ組から手がかり語取得
		//		for(TripleSet ts : tripleSetIncreaseList){
		//			ArrayList<KeyWord> keyWordTmpList = new ArrayList<KeyWord>();
		//			String target = ts.getTargetElement().getText();
		//			String effect = ts.getEffectElement().getText();
		//			System.out.println(target + " , " + effect);
		//			keyWordTmpList = GetKeyWordList.getKeyWordList(medicineNameList, sentenceList, target, effect);
		//			if(keyWordTmpList == null){ continue; }
		//			
		//			//keyWordTmpList = Logic.deleteOverlappingFromListForString(keyWordTmpList, keyWordSeedList);
		//			//keyWordTmpList = Logic.deleteOverlappingFromListForKey(keyWordTmpList, keyWordIncreaseFinalList);
		//			
		//			if(keyWordTmpList == null || keyWordTmpList.size() == 0){ continue; }
		//			
		//			//手がかり語リストセット
		//			for(KeyWord keyWord : keyWordTmpList){
		//				System.out.println(target + " , " + effect+ " から 「"+ keyWord.getKeyWordText() + "」 を得ました");
		//				
		//				//keyWordTextForSearchList.add(keyWord.getKeyWordText());
		//			}
		//			tripleSet.setKeyWordList(keyWordTmpList);
		//			
		//		}

	}

}
