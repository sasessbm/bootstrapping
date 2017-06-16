package bootstrapping;

import java.util.ArrayList;

import makeTriplicity.Phrase;
import makeTriplicity.SearchElementPhrase;
import makeTriplicity.TripleSet;
import makeTriplicity.TripleSetInfo;

public class GetTripleSetList {
	
	public static ArrayList<TripleSet> getTripleSetList
	(String keyWord, ArrayList<Sentence> sentenceList, ArrayList<String> medicineNameList){
		
		ArrayList<TripleSet> tripleSetList = new ArrayList<TripleSet>();
		
		for(Sentence sentence : sentenceList){
			ArrayList<Phrase> phraseReplaceList = sentence.getPhraseReplaceList();
			ArrayList<Phrase> phraseRestoreList = sentence.getPhraseRestoreList();
			ArrayList<String> keyWordList = new ArrayList<String>();
			keyWordList.add(keyWord);
			ArrayList<TripleSetInfo> tripleSetInfoList = SearchElementPhrase.getTripleSetInfoList(phraseReplaceList, keyWordList);
			if(tripleSetInfoList.size() == 0){ continue; }
			tripleSetList.addAll(makeTriplicity.GetTripleSetList.getTripleSetList(tripleSetInfoList, phraseRestoreList, medicineNameList));
		}
		
		return tripleSetList;
		
	}

}
