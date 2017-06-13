package bootstrapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import makeTriplicity.*;


public class GetKeyWordList {

	//	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
	//
	//		String sentence = "TARGETMEDICINE後、９か月目のMRIの結果・・ 以前壊死を起こしていて、TARGETMEDICINE治療で壊死が消えていた部分一部に、壊死が再発していました";
	//		String target = "壊死";
	//		String effect = "消えて";
	//		String keyWord = "";
	//
	//		keyWord = getKeyWord(sentence, target, effect);
	//
	//		System.out.println(keyWord);
	//
	//	}

	public static ArrayList<KeyWord> getKeyWordList
	(ArrayList<String> medicineNameList, ArrayList<Sentence> sentenceList, String target, String effect) 
			throws SAXException, IOException, ParserConfigurationException{
		ArrayList<KeyWord> keyWordList = new ArrayList<KeyWord>();

		for(Sentence sentence : sentenceList){
			ArrayList<Integer> P3keyWordIdList = new ArrayList<Integer>();
			ArrayList<Integer> P4keyWordIdList = new ArrayList<Integer>();
			String sentenceText = sentence.getText();
			ArrayList<Phrase> phraseRestoreList = sentence.getPhraseRestoreList();

			//手がかり語探索
			P3keyWordIdList.addAll(getKeyWordIdList(medicineNameList, phraseRestoreList, target, effect, 3));
			P4keyWordIdList.addAll(getKeyWordIdList(medicineNameList, phraseRestoreList, target, effect, 4));

			//手がかり語リストに追加
			keyWordList = addKeyWord(keyWordList, P3keyWordIdList, phraseRestoreList, 1);
			keyWordList = addKeyWord(keyWordList, P4keyWordIdList, phraseRestoreList, 0);
			//System.out.println(sentence.getId());
		}
		return keyWordList;
	}
	
	public static ArrayList<Integer> getKeyWordIdList
	(ArrayList<String> medicineNameList, ArrayList<Phrase> phraseList, String target, String effect, int patternType){

		ArrayList<Integer> keyWordIdList = new ArrayList<Integer>(); 
		int keyWordId = -1;
		int targetDependencyIndex = -1;
		int effectId = -1;
		int searchIndex = phraseList.size() - 1;
		int phraseId = 0;
		Phrase phrase = phraseList.get(searchIndex);
		ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
		
		while(searchIndex > 0){
			ArrayList<Morpheme> targetMorphemeList = new ArrayList<Morpheme>();
			phraseId = searchIndex;
			while(searchIndex > 0){
				Collections.reverse(morphemeList);
				targetMorphemeList.addAll(morphemeList);
				Collections.reverse(morphemeList);
				searchIndex--;
				phrase = phraseList.get(searchIndex);
				morphemeList = phrase.getMorphemeList();
				if(!morphemeList.get(morphemeList.size()-1).getMorphemeText().equals("の")){ break; }
			}
			Collections.reverse(targetMorphemeList);
			String targetForm = ChangePhraseForm.changePhraseForm(targetMorphemeList, 1);
			if(targetForm.contains(target)){
				targetDependencyIndex = phraseList.get(phraseId).getDependencyIndex();

				switch(patternType){
				case 3:
					effectId = P3Search.getEffectId(targetDependencyIndex, effect, phraseList);
					if(effectId == -1){ continue; }
					keyWordId = P3Search.getKeyWordId(effectId, phraseList, medicineNameList);
					break;
				case 4:
					effectId = P4Search.getEffectId(targetDependencyIndex, effect, phraseList);
					if(effectId == -1){ continue; }
					keyWordId = P4Search.getKeyWordId(effectId, phraseList, medicineNameList);
					break;
				}
				
				if(keyWordId == -1 || phraseId == keyWordId){ continue; }
				keyWordIdList.add(keyWordId);
			}
		}
		return keyWordIdList;
	}
	
	public static ArrayList<KeyWord> addKeyWord
	(ArrayList<KeyWord> keyWordList, ArrayList<Integer> keyWordIdList, ArrayList<Phrase> phraseList, int keyWordIndex){
		
		if(keyWordIdList.size() == 0){ return keyWordList; }
		for(int id : keyWordIdList){
			Morpheme morpheme = phraseList.get(id).getMorphemeList().get(keyWordIndex);
			
			if(!morpheme.getOriginalForm().equals("*")){
				keyWordList.add(new KeyWord(morpheme.getOriginalForm()));
			}else{
				keyWordList.add(new KeyWord(morpheme.getMorphemeText()));
			}
		}
		return keyWordList;
	}

}
