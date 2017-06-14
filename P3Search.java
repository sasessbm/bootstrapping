package bootstrapping;

import java.util.ArrayList;
import java.util.Collections;

import makeTriplicity.*;

public class P3Search {
	
	//public static final String MEDICINE = "MEDICINE";

	public static int getEffectId(int targetDependencyIndex, String effect, ArrayList<Phrase> phraseList){

		int effectId = -1;
		for(Phrase phrase : phraseList){
			int phraseId = phrase.getId();
			if(phraseId == targetDependencyIndex){
				//String changeEffectForm = ChangePhraseForm.changePhraseForm(phrase.getMorphemeList(), 2);
				//if(changeEffectForm.contains(effect)){
					effectId = phraseId;
					break;
				//}
			}
		}
		return effectId;
	}

	public static int getKeyWordId(int targetId, int effectId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){

		//String keyWord = "";
		int keyWordId = -1;
		for(Phrase phrase : phraseList){
			//String text = phrase.getPhraseText();
			int dependencyIndex = phrase.getDependencyIndex();
			
			if(effectId == targetId){ break; } //対象文節まで到達した時

			if(dependencyIndex == effectId && judgeKeyWordPhrase(phrase, medicineNameList)){
				//keyWord = phrase.getMorphemeList().get(1).getMorphemeText();
				//keyWord = phrase.getMorphemeList().get(1).getOriginalForm();
				//keyWord = phrase.getPhraseText();
				keyWordId = phrase.getId();
			}
		}
		return keyWordId;
	}
	
	public static boolean judgeKeyWordPhrase(Phrase phrase, ArrayList<String> medicineNameList){
		
		//文節の中身が1形態素以下なら不適
		if(phrase.getMorphemeList().size() < 2){ return false; }
		
		String text = phrase.getPhraseText();
		for(String medicineName : medicineNameList){
			if(text.contains(medicineName)){
				return true;
			}
		}
		return false;
	}

}
