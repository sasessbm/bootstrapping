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

		int keyWordId = -1;
		boolean isKeyWordPhrase = false;
		String keyWord = "";

		for(Phrase phrase : phraseList){
			int dependencyIndex = phrase.getDependencyIndex();
			if(dependencyIndex == effectId){
				keyWordId = phrase.getId();
				isKeyWordPhrase = judgeKeyWordPhrase(keyWordId, phraseList);
				if(isKeyWordPhrase){
					//keyWord = phrase.getMorphemeList().get(0).getMorphemeText();
					//keyWord = phrase.getMorphemeList().get(0).getOriginalForm();
					keyWord = phrase.getPhraseText();
					break;
				}
			}
		}
		return keyWord;
	}
	
	public static boolean judgeKeyWordPhrase(int keyWordId, ArrayList<Phrase> phraseList){
		
		for(Phrase phrase : phraseList){
			String text = phrase.getPhraseText();
			int dependencyIndex = phrase.getDependencyIndex();
			if(dependencyIndex == keyWordId && text.contains(MEDICINE)){
				return true;
			}
		}
		return false;
	}
	
}
