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
				String changeEffectForm = ChangePhraseForm.changePhraseForm(phrase.getMorphemeList(), 2);
				if(changeEffectForm.contains(effect)){
					effectId = phraseId;
					break;
				}
			}
		}
		return effectId;
	}

	public static int getKeyWordId(int effectId, ArrayList<Phrase> phraseList, ArrayList<String> medicineNameList){

		//String keyWord = "";
		int keyWordId = -1;
		for(Phrase phrase : phraseList){
			String text = phrase.getPhraseText();
			int dependencyIndex = phrase.getDependencyIndex();

			if(dependencyIndex == effectId && judgeKeyWordPhrase(text, medicineNameList)){
				//keyWord = phrase.getMorphemeList().get(1).getMorphemeText();
				//keyWord = phrase.getMorphemeList().get(1).getOriginalForm();
				//keyWord = phrase.getPhraseText();
				keyWordId = phrase.getId();
			}
		}
		return keyWordId;
	}
	
	public static boolean judgeKeyWordPhrase(String text, ArrayList<String> medicineNameList){
		
		for(String medicineName : medicineNameList){
			if(text.contains(medicineName)){
				return true;
			}
		}
		return false;
	}

}
