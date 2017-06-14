package bootstrapping;

import java.util.ArrayList;
import java.util.Collections;

import makeTriplicity.*;

public class P4Search {

	public static final String MEDICINE = "MEDICINE";

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

		int keyWordId = -1;
		boolean isKeyWordPhrase = false;
		//String keyWord = "";

		for(Phrase phrase : phraseList){
			int dependencyIndex = phrase.getDependencyIndex();

			if(effectId == targetId){ break; } //対象文節まで到達した時

			if(dependencyIndex == effectId){
				keyWordId = phrase.getId();
				isKeyWordPhrase = judgeKeyWordPhrase(keyWordId, phraseList ,medicineNameList);
				if(isKeyWordPhrase){
					//keyWord = phrase.getMorphemeList().get(0).getMorphemeText();
					//keyWord = phrase.getMorphemeList().get(0).getOriginalForm();
					//keyWord = phrase.getPhraseText();
					break;
				}
			}
			keyWordId = -1;
		}
		return keyWordId;
	}

	public static boolean judgeKeyWordPhrase(int keyWordId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){

		for(Phrase phrase : phraseList){
			//String text = phrase.getPhraseText();
			int dependencyIndex = phrase.getDependencyIndex();
			if(dependencyIndex == keyWordId && judgeKeyWordPhrase(phrase, medicineNameList)){
				return true;
			}
		}
		return false;
	}

	public static boolean judgeKeyWordPhrase(Phrase phrase, ArrayList<String> medicineNameList){

//		//文節の中身が1形態素以下なら不適
//		if(phrase.getMorphemeList().size() < 2){ return false; }

		String text = phrase.getPhraseText();
		for(String medicineName : medicineNameList){
			if(text.contains(medicineName)){
				return true;
			}
		}
		return false;
	}

}
