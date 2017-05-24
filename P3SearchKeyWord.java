package bootstrapping;

import java.util.ArrayList;

import makeTriplicity.*;

public class P3SearchKeyWord {
	
	private static  ArrayList<Phrase> phraseList;
	public static final String TARGETMEDICINE = "TARGETMEDICINE";

	public static String keyWord(ArrayList<Phrase> phraseList, String target, String effect){

		String keyWord = "";
		int targetDependencyIndex = -1;
		int effectId = -1;
		P3SearchKeyWord.phraseList = phraseList;

		for(Phrase phrase : phraseList){
			String text = phrase.getPhraseText();
			if(text.contains(target)){
				targetDependencyIndex = phrase.getDependencyIndex();
				effectId = getEffectId(targetDependencyIndex, effect);
				if(effectId == -1){ continue; }
				keyWord = searchKeyWord(effectId);
				continue;
			}
		}
		return keyWord;

	}

	public static int getEffectId(int targetDependencyIndex, String effect){

		int effectId = -1;

		for(Phrase phrase : phraseList){
			String text = phrase.getPhraseText();
			int phraseId = phrase.getId();
			if(phraseId == targetDependencyIndex && text.contains(effect)){
				effectId = phraseId;
				break;
			}
		}
		return effectId;
	}

	public static String searchKeyWord(int effectId){

		String keyWord = "";

		for(Phrase phrase : phraseList){
			String text = phrase.getPhraseText();
			int dependencyIndex = phrase.getDependencyIndex();

			if(dependencyIndex == effectId && text.contains(TARGETMEDICINE)){
				keyWord = phrase.getMorphemeList().get(1).getMorphemeText();
			}
		}
		return keyWord;
	}

}
