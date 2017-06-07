package bootstrapping;

import java.util.ArrayList;
import java.util.Collections;

import makeTriplicity.Morpheme;
import makeTriplicity.Phrase;

public class SearchKeyWord {

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

}
