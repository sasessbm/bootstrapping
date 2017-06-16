package bootstrapping;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import makeTriplicity.TripleSet;
import makeTriplicity.*;

public class RunFromKeyWordSeed {

	public static void run(ArrayList<String> keyWordSeedList, ArrayList<String> medicineNameList) throws Exception {

		//シードセット
		ArrayList<TripleSet> tripleSetIncreaseList = new ArrayList<TripleSet>();
		ArrayList<KeyWord> keyWordIncreaseList = Transformation.stringToKeyWord(keyWordSeedList);
		ArrayList<String> keyWordTextIncreaseList = new ArrayList<String>();

		ArrayList<TripleSet> tripleSetIncreaseFinalList = new ArrayList<TripleSet>();
		ArrayList<KeyWord> keyWordIncreaseFinalList = new ArrayList<KeyWord>();

		ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(3001, 4000, medicineNameList);
		//ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(3500, 4000, medicineNameList);
		//ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(idList, medicineNameList);
		
		System.out.println("取得文書数は " + sentenceList.size() + "文 です");

		
		double constant = 0.1;
		int repeatCount = 10;

		for(int i=1; i <= repeatCount; i++){

			System.out.println("\r\n" + i + "回目");
			double threshold = 0;
			ArrayList<TripleSet> tripleSetForSearchList = new ArrayList<TripleSet>();
			ArrayList<String> keyWordTextForSearchList = new ArrayList<String>();
			int tripleSetUsedNum = 0;
			int keyWordUsedNum = 0;

			if(i==1){ System.out.println("\r\n「以下の初期手がかり語から三つ組取得」"); }
			else{ System.out.println("\r\n「以下の手がかり語から新たな三つ組取得」"); }
			
			//手がかり語から三つ組取得
			for(KeyWord keyWord : keyWordIncreaseList){
				String keyWordText = keyWord.getKeyWordText();
				System.out.println("\r\n" + keyWordText);
				//ArrayList<TripleSet> tripleSetTmpList = GetTripleSetList.getTripleSetList(keyWordText, sentenceList, medicineNameList);
				ArrayList<TripleSet> tripleSetTmpList = GetTripleSetList.getTripleSetList(keyWordText, sentenceList, medicineNameList);
				if(tripleSetTmpList.size() == 0){ continue; }
				tripleSetForSearchList.addAll(tripleSetTmpList);
				
				//すでに取得しているものは取得しない
				//tripleSetTmpList = Logic.deleteOverlappingFromListForKey(tripleSetTmpList, tripleSetIncreaseFinalList);
				
				System.out.println("「"+keyWord.getKeyWordText() + "」から、以下の三つ組を取得");
				for(TripleSet tripleSet : tripleSetTmpList){
					System.out.println(tripleSet.getMedicineName()+ " , " + tripleSet.getTargetElement().getText() 
																		+ " , " +tripleSet.getEffectElement().getText());
				}

				//手がかり語に三つ組リストセット
				keyWord.setTripleSetList(tripleSetTmpList);
				keyWordUsedNum ++;
			}

			if(keyWordUsedNum == 0){
				System.out.println("\r\n三つ組を取得できませんでした");
				break;
			}

			//三つ組探索用リストの重複削除
			tripleSetForSearchList = makeTriplicity.GetTripleSetList.deleteSameSet(tripleSetForSearchList);

			//閾値計算
//			if(i > 2){
//				//System.out.println("keyWordUsedNum・・・"+keyWordUsedNum);
//				threshold = constant * (Math.log(keyWordUsedNum) / Math.log(2.0));
//				System.out.println("閾値・・・" + threshold);
//			}

			System.out.println("\r\n「取得した三つ組のエントロピー計算」");
			//三つ組のエントロピー計算
			for(TripleSet tripleSet : tripleSetForSearchList){
				double entropy = 0;
				int tripleSetAllNum = 0;
				int tripleSetNum = 0;
				ArrayList<Integer> tripleSetNumList = new ArrayList<Integer>();

				for(KeyWord keyWord : keyWordIncreaseList){
					tripleSetNum = keyWord.getTripleSetNum(tripleSet);
					if(tripleSetNum == 0){ continue; }
					tripleSetNumList.add(tripleSetNum);
					tripleSetAllNum += tripleSetNum;
				}
				if(tripleSetAllNum != 0){
					//System.out.println("tripleSetAllNum・・・" + tripleSetAllNum);
					entropy = EntropyCalculator.caluculateEntropy(tripleSetNumList, tripleSetAllNum);
				}
				System.out.println(tripleSet.getMedicineName()+ " , " + tripleSet.getTargetElement().getText() + " , " 
						+tripleSet.getEffectElement().getText() +"　→　" + entropy + "　(" + tripleSet.getUsedKeyWord()+")");

				//閾値以上の三つ組をリストに追加
				if(entropy >= threshold){
					tripleSetIncreaseList.add(tripleSet);
				}
			}
			//手がかり語増加リスト初期化
			//keyWordIncreaseList.clear();
			keyWordTextIncreaseList.clear();

			System.out.println("\r\n「以下の三つ組から新たな手がかり語取得」");
			
			//三つ組から手がかり語取得
			for(TripleSet tripleSet : tripleSetIncreaseList){
				ArrayList<KeyWord> keyWordTmpList = new ArrayList<KeyWord>();
				String target = tripleSet.getTargetElement().getText();
				String effect = tripleSet.getEffectElement().getText();
				System.out.println(target + " , " + effect);
				keyWordTmpList = GetKeyWordList.getKeyWordList(medicineNameList, sentenceList, target, effect);
				if(keyWordTmpList == null){ continue; }
				
				//すでに取得しているものは取得しない
				keyWordTmpList = Logic.deleteOverlappingFromListForString(keyWordTmpList, keyWordSeedList);
				keyWordTmpList = Logic.deleteOverlappingFromListForKey(keyWordTmpList, keyWordIncreaseFinalList);
				
				if(keyWordTmpList == null || keyWordTmpList.size() == 0){ continue; }
				
				//手がかり語リストセット
				for(KeyWord keyWord : keyWordTmpList){
					System.out.println(target + " から 「"+ keyWord.getKeyWordText() + "」 を取得しました");
					
					keyWordTextForSearchList.add(keyWord.getKeyWordText());
				}
				tripleSet.setKeyWordList(keyWordTmpList);
				tripleSetUsedNum ++;
			}

			if(tripleSetUsedNum == 0){
				System.out.println("\r\n手がかり語を取得できませんでした");
				break;
			}

			//手がかり語探索用リストの重複削除
			keyWordTextForSearchList = new ArrayList<String>(new LinkedHashSet<>(keyWordTextForSearchList));

			//閾値計算
			//if(i != 1){
				//System.out.println("tripleSetUsedNum・・・"+tripleSetUsedNum);
//				threshold = constant * (Math.log(tripleSetUsedNum) / Math.log(2.0));
//				System.out.println("閾値・・・" + threshold);
			//}

			System.out.println("\r\n「取得した手がかり語のエントロピー計算」");
			
			//手がかり語のエントロピー計算
			for(String keyWordText : keyWordTextForSearchList){
				double entropy = 0;
				int keyWordTextAllNum = 0;
				int keyWordTextNum = 0;
				ArrayList<Integer> keyWordNumList = new ArrayList<Integer>();

				for(TripleSet tripleSet : tripleSetIncreaseList){
					keyWordTextNum = tripleSet.getKeyWordNum(keyWordText);
					if(keyWordTextNum == 0){ continue; }
					keyWordNumList.add(keyWordTextNum);
					keyWordTextAllNum += keyWordTextNum;
				}
				if(keyWordTextAllNum != 0){
					//System.out.println("keyWordTextAllNum・・・" + keyWordTextAllNum);
					entropy = EntropyCalculator.caluculateEntropy(keyWordNumList, keyWordTextAllNum);
				}
				System.out.println(keyWordText + "　→　" + entropy);

				//閾値以上の手がかり語をリストに追加
				if(entropy >= threshold){
					keyWordTextIncreaseList.add(keyWordText);
				}
			}
			
			//手がかり語増加リスト更新
			keyWordIncreaseList = Transformation.stringToKeyWord(keyWordTextIncreaseList);
			
			//手がかり語最終増加リスト更新
			keyWordIncreaseFinalList.addAll(keyWordIncreaseList);

			//三つ組最終増加リスト更新
			tripleSetIncreaseFinalList.addAll(tripleSetIncreaseList);
			
			//三つ組増加リスト初期化
			tripleSetIncreaseList.clear();
		}

		System.out.println("\r\n獲得結果");
		
		System.out.println("\r\n手がかり語");
		for(KeyWord keyWord : keyWordIncreaseFinalList){
			System.out.println(keyWord.getKeyWordText());
		}

		System.out.println("\r\n三つ組");
		for(TripleSet tripleSet : tripleSetIncreaseFinalList){
			System.out.println(tripleSet.getMedicineName()+ " , " + tripleSet.getTargetElement().getText() + " , " 
					+tripleSet.getEffectElement().getText());
		}


	}

}
