package bootstrapping;

import java.util.ArrayList;
import java.util.Collections;

import makeTriplicity.Element;
import makeTriplicity.Morpheme;
import makeTriplicity.Phrase;
import makeTriplicity.PostProcessing;
import makeTriplicity.TripleSet;



public class GetTripleSetList {

	//	public static ArrayList<TripleSet> getTripleSetList
	//	(String keyWord, ArrayList<Sentence> sentenceList, ArrayList<String> medicineNameList){
	//		
	//		ArrayList<TripleSet> tripleSetList = new ArrayList<TripleSet>();
	//		
	//		for(Sentence sentence : sentenceList){
	//			ArrayList<Phrase> phraseReplaceList = sentence.getPhraseReplaceList();
	//			ArrayList<Phrase> phraseRestoreList = sentence.getPhraseRestoreList();
	//			ArrayList<String> keyWordList = new ArrayList<String>();
	//			keyWordList.add(keyWord);
	//			//ArrayList<TripleSetInfo> tripleSetInfoList = SearchElementPhrase.getTripleSetInfoList(phraseReplaceList, keyWordList);
	//			ArrayList<TripleSetInfo2> tripleSetInfoList = SearchElementPhrase2.getTripleSetInfoList(phraseReplaceList, keyWordList);
	//			if(tripleSetInfoList.size() == 0){ continue; }
	//			//tripleSetList.addAll(makeTriplicity.GetTripleSetList.getTripleSetList(tripleSetInfoList, phraseRestoreList, medicineNameList));
	//			tripleSetList.addAll(makeTriplicity.GetTripleSetList2.getTripleSetList(tripleSetInfoList, phraseRestoreList, medicineNameList));
	//		}
	//		
	//		return tripleSetList;
	//		
	//	}

	public static ArrayList<TripleSet> getTripleSetList
	(ArrayList<TripleSetInfo> tripleSetInfoList, ArrayList<Sentence> sentenceList, ArrayList<String> medicineNameList){

		ArrayList<TripleSet> tripleSetList = new ArrayList<TripleSet>();

		for(TripleSetInfo tripleSetInfo : tripleSetInfoList){
			ArrayList<Phrase> phraseRestoreList = sentenceList.get(tripleSetInfo.getSentenceId()-1).getPhraseRestoreList();
			tripleSetList.add(getTripleSet(tripleSetInfo, phraseRestoreList, medicineNameList));
		}
		
		return tripleSetList;
	}



	public static TripleSet getTripleSet
	(TripleSetInfo tripleSetInfo, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList) {

		int targetPhraseId = tripleSetInfo.getTargetPhraseId();
		//ArrayList<Integer> targetPhraseIdList = tripleSetInfo.getTargetPhraseIdList();
		int effectPhraseId = tripleSetInfo.getEffectPhraseId();
		//ArrayList<String> medicineNameInPhraseList = new ArrayList<String>();
		int medicinePhraseId = tripleSetInfo.getMedicinePhraseId();
		String medicineName = "";

		// とりあえず、薬剤名文節に薬剤名が複数含まれていた場合に対応
		for(Morpheme morpheme : phraseList.get(medicinePhraseId).getMorphemeList()){
			if(!morpheme.getPartOfSpeechDetails().equals("固有名詞") && 
					!morpheme.getPartOfSpeechDetails().equals("一般")){ continue; }

			for(String text : medicineNameList){
				String morphemeText = morpheme.getMorphemeText();
				if(morphemeText.contains(text)){
					//medicineNameInPhraseList.add(medicineName);
					medicineName = text;
					break;
				}
			}
		}

		ArrayList<Morpheme> targetMorphemeList = new ArrayList<Morpheme>();
		ArrayList<Morpheme> effectMorphemeList = new ArrayList<Morpheme>();
		Element targetElement = new Element();
		Element effectElement = new Element();
		int searchIndex = 0;

		// 対象要素の形態素リスト取得
		Phrase phrase = phraseList.get(targetPhraseId);
		ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
		while(true){
			Collections.reverse(morphemeList);
			targetMorphemeList.addAll(morphemeList);
			Collections.reverse(morphemeList);
			searchIndex++;
			phrase = phraseList.get(targetPhraseId - searchIndex);
			morphemeList = phrase.getMorphemeList();
			if(!morphemeList.get(morphemeList.size()-1).getMorphemeText().equals("の")){ break; }
		}
		Collections.reverse(targetMorphemeList);

		// 効果要素の形態素リスト取得
		for(Morpheme morpheme : phraseList.get(effectPhraseId).getMorphemeList()){
			effectMorphemeList.add(morpheme);
		}

		// 要素取得
		targetElement = getElement(targetMorphemeList, 1);
		effectElement = getElement(effectMorphemeList, 2);

		//for(String medicineNameInPhrase : medicineNameInPhraseList){
		TripleSet tripleSet = new TripleSet();
		tripleSet.setMedicineName(medicineName);
		tripleSet.setTargetElement(targetElement);
		tripleSet.setEffectElement(effectElement);
		tripleSet.setUsedKeyWord(tripleSetInfo.getUsedKeyWord());
		PostProcessing.deleteParentheses(tripleSet);
		//tripleSetList.add(tripleSet);
		//}

		//tripleSetList = deleteSameSet(tripleSetList);
		return tripleSet;
	}

	public static Element getElement(ArrayList<Morpheme> morphemeList, int elementType){

		Element element = new Element();
		String text = "";
		ArrayList<Morpheme> elementMorphemeList = new ArrayList<Morpheme>();
		//int denialIndex = 0;
		boolean isVerb = false;


		//		for(Morpheme morpheme : morphemeList){
		//			//System.out.println(morpheme.getMorphemeText());
		//			if(morpheme.getOriginalForm().equals("ない")){
		//				denialIndex ++;
		//			}
		//		}

		for(Morpheme morpheme : morphemeList){

			//助詞が出現("の"以外) 
			if(morpheme.getPartOfSpeech().equals("助詞") & !morpheme.getOriginalForm().equals("の") ){ break; }

			if(morpheme.getOriginalForm().equals("、") || morpheme.getOriginalForm().equals("。")){ break; }
			//System.out.println(morpheme.getMorphemeText() + "→" + morpheme.getPartOfSpeechDetails());

			if(morpheme.getPartOfSpeech().equals("動詞")){

				//if(denialIndex % 2 == 1 || morphemeIndex != morphemeList.size()){
				//text += morpheme.getMorphemeText(); 
				//elementMorphemeList.add(morpheme);
				//}

				isVerb = true;

			}else{
				isVerb = false;
			}

			elementMorphemeList.add(morpheme);
		}

		for(int i = 0; i < elementMorphemeList.size(); i++){

			if(isVerb && i == elementMorphemeList.size() - 1 && elementType == 2){
				text += elementMorphemeList.get(i).getOriginalForm(); //「効果」要素で、最後が動詞だった時
			}else{
				text += elementMorphemeList.get(i).getMorphemeText();
			}
		}

		//		if(denialIndex % 2 == 1){
		//			text += "ない";
		//			//elementMorphemeList.add(morpheme);
		//		}

		element.setText(text);
		element.setMorphemeList(elementMorphemeList);

		return element;
	}

	//重複した組を削除
	public static ArrayList<TripleSet> deleteSameSet(ArrayList<TripleSet> tripleSetList){

		ArrayList<TripleSet> tripleSetListBase = tripleSetList;

		for(int i = 0 ; i < tripleSetListBase.size() ; i++){
			int sameCount = 0;
			String medicineNameBase = tripleSetListBase.get(i).getMedicineName();
			String targetBase = tripleSetListBase.get(i).getTargetElement().getText();
			String effectBase = tripleSetListBase.get(i).getEffectElement().getText();

			for(TripleSet tripleSet : tripleSetList){
				String medicineName = tripleSet.getMedicineName();
				String target = tripleSet.getTargetElement().getText();
				String effect = tripleSet.getEffectElement().getText();

				if(medicineNameBase.equals(medicineName) && targetBase.equals(target) && effectBase.equals(effect)){
					sameCount++;
				}
			}
			if(sameCount>=2){
				tripleSetList.remove(tripleSetListBase.get(i));
			}
		}
		return tripleSetList;
	}



}
