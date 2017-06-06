package bootstrapping;

import java.util.ArrayList;
import java.util.Collections;

import makeTriplicity.Morpheme;
import makeTriplicity.Phrase;

public class SearchKeyWord {

	public static ArrayList<String> getKeyWordList
	(ArrayList<Phrase> phraseList, String target, String effect, int patternType){

		ArrayList<String> keyWordList = new ArrayList<String>(); 
		String keyWord = "";
		int targetDependencyIndex = -1;
		int effectId = -1;
		int searchIndex = phraseList.size() - 1;
		int phraseId = 0;
		//P3Search.phraseList = phraseList;
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
			if(targetForm.equals(target)){
				targetDependencyIndex = phraseList.get(phraseId).getDependencyIndex();

				switch(patternType){
				case 3:
					effectId = P3Search.getEffectId(targetDependencyIndex, effect, phraseList);
					if(effectId == -1){ continue; }
					keyWord = P3Search.getKeyWord(effectId, phraseList);
					break;

				case 4:
					effectId = P4Search.getEffectId(targetDependencyIndex, effect, phraseList);
					if(effectId == -1){ continue; }
					keyWord = P4Search.getKeyWord(effectId, phraseList);
					break;
				}

//				if(patternType == 3){
//					effectId = P3Search.getEffectId(targetDependencyIndex, effect);
//					if(effectId == -1){ continue; }
//					keyWord = P3Search.getKeyWord(effectId);
//				}else if(patternType == 4){
//					effectId = P4Search.getEffectId(targetDependencyIndex, effect);
//					if(effectId == -1){ continue; }
//					keyWord = P4Search.getKeyWord(effectId);
//				}

				keyWordList.add(keyWord);
			}
		}
		return keyWordList;
	}

}
