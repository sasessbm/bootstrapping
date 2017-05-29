package bootstrapping;

import java.util.ArrayList;

import makeTriplicity.*;

public class P4SearchKeyWord {
	
	private static  ArrayList<Phrase> phraseList;
	public static final String TARGETMEDICINE = "TARGETMEDICINE";

	public static String keyWord(ArrayList<Phrase> phraseList, String target, String effect){

		String keyWord = "";
		int targetDependencyIndex = -1;
		int effectId = -1;
		
		P4SearchKeyWord.phraseList = phraseList;

		for(Phrase phrase : phraseList){
			//String text = phrase.getPhraseText();
			String changeTargetForm = ChangePhraseForm.changePhraseForm(phrase, 1);
			if(changeTargetForm.equals(target)){
				targetDependencyIndex = phrase.getDependencyIndex();
				effectId = getEffectId(targetDependencyIndex, effect);
				if(effectId == -1){ continue; }
				keyWord = getKeyWord(effectId);
				continue;
			}
		}
		return keyWord;
	}

	public static int getEffectId(int targetDependencyIndex, String effect){

		int effectId = -1;
		
		for(Phrase phrase : phraseList){
			//String text = phrase.getPhraseText();
			int phraseId = phrase.getId();
			
			if(phraseId == targetDependencyIndex){
				String changeEffectForm = ChangePhraseForm.changePhraseForm(phrase, 2);
				if(changeEffectForm.equals(effect)){
					effectId = phraseId;
					break;
				}
			}
			
//			if(phraseId == targetDependencyIndex && text.contains(effect)){
//				effectId = phraseId;
//				break;
//			}
		}
		return effectId;
	}

	public static String getKeyWord(int effectId){

		int keyWordId = -1;
		boolean isKeyWordPhrase = false;
		String keyWord = "";

		for(Phrase phrase : phraseList){
			int dependencyIndex = phrase.getDependencyIndex();
			if(dependencyIndex == effectId){
				keyWordId = phrase.getId();
				isKeyWordPhrase = judgeKeyWordPhrase(keyWordId);
				if(isKeyWordPhrase){
					keyWord = phrase.getMorphemeList().get(0).getMorphemeText();
					break;
				}
			}
		}
		return keyWord;
	}
	
	public static boolean judgeKeyWordPhrase(int keyWordId){
		
		for(Phrase phrase : phraseList){
			String text = phrase.getPhraseText();
			int dependencyIndex = phrase.getDependencyIndex();
			if(dependencyIndex == keyWordId && text.contains(TARGETMEDICINE)){
				return true;
			}
		}
		return false;
	}
	
}
