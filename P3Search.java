package bootstrapping;

import java.util.ArrayList;
import java.util.Collections;

import makeTriplicity.*;

public class P3Search {
	
	public static final String MEDICINE = "MEDICINE";

	public static int getEffectId(int targetDependencyIndex, String effect, ArrayList<Phrase> phraseList){

		int effectId = -1;
		for(Phrase phrase : phraseList){
			int phraseId = phrase.getId();
			if(phraseId == targetDependencyIndex){
				String changeEffectForm = ChangePhraseForm.changePhraseForm(phrase.getMorphemeList(), 2);
				if(changeEffectForm.equals(effect)){
					effectId = phraseId;
					break;
				}
			}
		}
		return effectId;
	}

	public static String getKeyWord(int effectId, ArrayList<Phrase> phraseList){

		String keyWord = "";
		for(Phrase phrase : phraseList){
			String text = phrase.getPhraseText();
			int dependencyIndex = phrase.getDependencyIndex();

			if(dependencyIndex == effectId && text.contains(MEDICINE)){
				//keyWord = phrase.getMorphemeList().get(1).getMorphemeText();
				//keyWord = phrase.getMorphemeList().get(1).getOriginalForm();
				keyWord = phrase.getPhraseText();
			}
		}
		return keyWord;
	}

}
