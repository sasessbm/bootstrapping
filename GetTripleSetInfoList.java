package bootstrapping;

import java.util.ArrayList;

import makeTriplicity.Morpheme;
import makeTriplicity.Phrase;

public class GetTripleSetInfoList {

	public static final String MEDICINE = "MEDICINE";
	private static  ArrayList<Phrase> phraseList;
	private static String keyWordText;
	//private static TripleSetInfo2 tripleSetInfo2;
	private static ArrayList<TripleSetInfo> tripleSetInfoList;
	private static int medicinePhraseId;
	private static int sentenceId;

	public static ArrayList<TripleSetInfo> getTripleSetInfoList (ArrayList<Sentence> sentenceList, String keyWordText) {

		//ArrayList<TripleSetInfo2> tripleSetInfoList = new ArrayList<TripleSetInfo2>();
		tripleSetInfoList = new ArrayList<TripleSetInfo>();
		GetTripleSetInfoList.keyWordText = keyWordText;

		for(Sentence sentence : sentenceList){
			GetTripleSetInfoList.phraseList = sentence.getPhraseReplaceList();
			sentenceId = sentence.getSentenceId();

			for(Phrase phrase : phraseList){
				keyWordText = "";
				String phraseText = phrase.getPhraseText();
				if(!phraseText.contains(MEDICINE)){ continue; }

				// 対象薬剤名のすぐ後ろに手がかり語があるか探索
				int keywordPlaceIndex = getKeywordPlaceIndex(phrase.getMorphemeList());
				ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
				int medicinePlaceIndex = -1;

				// 薬剤名の位置取得
				for(int i = 0; i<morphemeList.size(); i++){
					String morphemeText = morphemeList.get(i).getMorphemeText();
					if(!morphemeText.contains(MEDICINE)){ continue; }
					medicinePlaceIndex = i;
					break;
				}
				if(medicinePlaceIndex == -1){ continue; } // 薬剤名がない
				//tripleSetInfo2.setMedicinePhraseId(phrase.getId());
				medicinePhraseId = phrase.getId();

				// 同じ文節内にある
				if(keywordPlaceIndex > 0){
					if((keywordPlaceIndex - 1) != medicinePlaceIndex){ continue; } // 隣り合っているか
					// 自身のIDを渡す
					judgeKeywordPhrase(phrase.getId());
				}

				// 同じ文節内にはない (が、「対象薬剤名＋助詞」になっている)
				else{
					//if(morphemeList.size() <= medicinePlaceIndex + 1){ continue; }
					//if(!morphemeList.get(morphemeList.size()-1).getPartOfSpeech().equals("助詞")){ continue; }
					//係り先番号を渡す
					judgeKeywordPhrase(phrase.getDependencyIndex());
				}

//				if(tripleSetInfo2.getEffectPhraseId() == -1 ||tripleSetInfo2.getTargetPhraseIdList().size() == 0){ continue; }
//				tripleSetInfo2.setUsedKeyWord(keyWordText);
//				tripleSetInfo2.setSentenceId(sentence.getId());
//				tripleSetInfoList.add(tripleSetInfo2);
			}

		}


		return tripleSetInfoList;
	}

	// 手がかり語の位置を探索
	public static int getKeywordPlaceIndex(ArrayList<Morpheme> morphemeList){

		int keywordPlaceIndex = -1;
		int morphemeIndex = -1;

		for(Morpheme morpheme : morphemeList){
			morphemeIndex ++;
			String originalForm = morpheme.getOriginalForm();

			if(originalForm.equals(keyWordText)){
				//System.out.println(keyword);
				keywordPlaceIndex = morphemeIndex;
			}

		}
		return keywordPlaceIndex;
	}

	//「手がかり語」要素存在文節判定
	public static void judgeKeywordPhrase(int dependencyIndex){

		for(Phrase phrase : phraseList){
			if(phrase.getId() != dependencyIndex){ continue; }
			if(getKeywordPlaceIndex(phrase.getMorphemeList()) != -1){
				//一番最後の文節が、格助詞または接続助詞か確認
				String partOfSpeechDetails = phrase.getMorphemeList()
						.get(phrase.getMorphemeList().size()-1).getPartOfSpeechDetails();
				//if(partOfSpeechDetails.contains("格助詞") || partOfSpeechDetails.contains("接続助詞")){
				if(partOfSpeechDetails.contains("格助詞") || partOfSpeechDetails.contains("接続助詞") || partOfSpeechDetails.contains("読点")){
					//System.out.println("手がかり語存在文節:"+ phrase.getPhraseText());
					judgeEffectPhrase(phrase.getDependencyIndex(), dependencyIndex);
					break;
				}
				break;
			}
		}
	}

	//「効果」要素存在文節判定
	public static void judgeEffectPhrase(int effectId, int keyId){

		for(Phrase phrase : phraseList){
			int phraseId = phrase.getId();
			if(phraseId == effectId){
				//tripleSetInfo2.setEffectPhraseId(phraseId);
				judgeTargetPhrase(effectId, keyId);
				break;
			}
		}
	}

	//「対象」要素存在文節判定
	public static void judgeTargetPhrase(int effectId, int keyId){

		//ArrayList<Integer> targetPhraseIdList = new ArrayList<Integer>();

		// 逆から探索
		for(int i=1; i<=phraseList.size(); i++){
			int currentIndex = phraseList.size()-i;
			Phrase phrase = phraseList.get(currentIndex);
			int phraseDependencyIndex = phrase.getDependencyIndex();
			int phraseId = phrase.getId();

			//「手がかり語」文節まで到達した時
			if(phraseId == keyId){ break; } 

			if(phraseDependencyIndex == effectId){
				String lastMorphemeText = phrase.getMorphemeList()
						.get(phrase.getMorphemeList().size()-1)
						.getMorphemeText();
				if(lastMorphemeText.equals("が") || lastMorphemeText.equals("は") || lastMorphemeText.equals("を")){
					TripleSetInfo tripleSetInfo = new TripleSetInfo(sentenceId , medicinePhraseId, phraseId, effectId, keyWordText);
					tripleSetInfoList.add(tripleSetInfo);
					//targetPhraseIdList.add(phraseId);

					//break;
				}
			}
		}
	}




}
