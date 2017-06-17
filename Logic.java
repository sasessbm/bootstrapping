package bootstrapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import makeTriplicity.*;

public class Logic {

	public static ArrayList<String> medicineNameList;

	public static void main(String[] args) throws Exception{

		//		ArrayList<Integer> idList = getRandomIdList(100,1,100);
		//		for(int id : idList){
		//			System.out.println(id);
		//		}

		ArrayList<Integer> usedIdList = new ArrayList<Integer>();
		usedIdList.add(2);
		usedIdList.add(3);
		usedIdList.add(6);
		usedIdList.add(9);
		usedIdList.add(12);

		ArrayList<Integer> additionalIdList = getAdditionalRandomIdList(10, 1, 30, usedIdList);

		for(int id : additionalIdList){
			System.out.println(id);
		}

	}


	//重複した組を削除
	public static ArrayList<KeyWord> deleteOverlappingFromListForKey
	(ArrayList<KeyWord> removeList, ArrayList<KeyWord> compareList){

		for(KeyWord key : compareList){
			String textBase = key.getKeyWordText();
			for(int i = removeList.size() - 1; i >= 0; i--){
				if(removeList.get(i).getKeyWordText().equals(textBase)){
					removeList.remove(i);
				}
			}
		}
		return removeList;
	}

	public static ArrayList<TripleSetInfo> deleteOverlappingFromListForTripleSetInfo
	(ArrayList<TripleSetInfo> removeList, ArrayList<TripleSetInfo> compareList){

		for(TripleSetInfo tripleSetInfo : compareList){
			int sentenceId = tripleSetInfo.getSentenceId();
			int medicinePhraseId = tripleSetInfo.getMedicinePhraseId();
			int targetPhraseId = tripleSetInfo.getTargetPhraseId();
			int effectPhraseId = tripleSetInfo.getEffectPhraseId();

			for(int i = removeList.size() - 1; i >= 0; i--){
				TripleSetInfo tSI = removeList.get(i);
				if(tSI.getSentenceId() == sentenceId && tSI.getMedicinePhraseId() == medicinePhraseId && 
						tSI.getTargetPhraseId() == targetPhraseId && tSI.getEffectPhraseId() == effectPhraseId){
					removeList.remove(i);
				}
			}
		}
		return removeList;
	}

	public static ArrayList<KeyWord> deleteOverlappingFromListForString
	(ArrayList<KeyWord> removeList, ArrayList<String> compareList){

		for(String word : compareList){
			//String textBase = key.getKeyWordText();
			for(int i = removeList.size() - 1; i >= 0; i--){
				if(removeList.get(i).getKeyWordText().equals(word)){
					removeList.remove(i);
				}
			}
		}
		return removeList;
	}

	//ゴミ取り
	public static String cleanWord(String word){
		word = word.replace(".", "");
		word = word.replace("｡", "");
		word = word.replace("。", "");
		word = word.replace("､", "");
		word = word.replace("、", "");
		word = word.replace("｣", "");
		word = word.replace("｢", "");
		word = word.replace("】", "");
		word = word.replace("【", "");
		word = word.replace("／", "");
		word = word.replace("/", "");
		word = word.replace("\\", "");
		word = word.replace("\"", "");
		word = word.replace("\'", "");
		word = word.replace("@", "");
		word = word.replace("（", "");
		word = word.replace("）", "");
		word = word.replace("(", "");
		word = word.replace(")", "");
		word = word.replace("-", "");
		word = word.replace(",", "");

		return word;
	}

	//薬剤名を含むか判定
	public static boolean containsMedicine(String word){
		for(String medicineName : medicineNameList){
			if(word.contains(medicineName)){return true;}
		}
		return false;
	}

	//手がかり語の適切性判定
	public static boolean properKeyWord(Morpheme morpheme){

		//手がかり語は名詞または動詞とする
		if(!(morpheme.getPartOfSpeech().equals("名詞") || morpheme.getPartOfSpeech().equals("動詞"))){ return false; }

		//数字は不適
		if(morpheme.getPartOfSpeechDetails().equals("数")){ return false; }

		//薬剤名が含まれていた場合は不適
		if(containsMedicine(morpheme.getMorphemeText())){ return false; }

		return true;
	}

	//ランダムなIDリスト作成
	public static ArrayList<Integer> getRandomIdList(int idNum, int startIdIndex, int endIdIndex){

		ArrayList<Integer> randomIdList = new ArrayList<Integer>();
		Random rand = new Random();
		boolean isCreated;
		int id = rand.nextInt(endIdIndex + 1 - startIdIndex) + startIdIndex;

		randomIdList.add(id);

		for(int i=0; i < idNum-1; ){
			isCreated = false;
			id = rand.nextInt(endIdIndex + 1 - startIdIndex) + startIdIndex;
			for(Integer idInList : randomIdList){
				if(idInList == id){
					isCreated = true;
				}
			}
			if(!isCreated){
				randomIdList.add(id);
				i++;
			}
		}
		Collections.sort(randomIdList);
		return randomIdList;
	}

	//IDリストを更新
	public static ArrayList<Integer> getAdditionalRandomIdList(int idNum, int startIdIndex, int endIdIndex, ArrayList<Integer> usedIdList){

		ArrayList<Integer> additionalIdList = new ArrayList<Integer>();
		Random rand = new Random();
		boolean isUsedId = false;
		boolean isCreated;
		int id = 0;

		while(true){
			isUsedId = false;
			id = rand.nextInt(endIdIndex + 1 - startIdIndex) + startIdIndex;

			for(int usedId : usedIdList){
				if(id == usedId){
					isUsedId = true;
					break;
				}
			}
			if(!isUsedId){
				break;
			}
		}
		additionalIdList.add(id);
		for(int i=0; i < idNum-1; ){
			isCreated = false;

			while(true){
				isUsedId = false;
				id = rand.nextInt(endIdIndex + 1 - startIdIndex) + startIdIndex;

				for(int usedId : usedIdList){
					if(id == usedId){
						isUsedId = true;
						break;
					}
				}
				if(!isUsedId){ break; }
			}
			for(Integer idInList : additionalIdList){
				if(idInList == id){
					isCreated = true;
				}
			}
			if(!isCreated){
				additionalIdList.add(id);
				i++;
			}
		}
		Collections.sort(additionalIdList);
		return additionalIdList;
	}
	
	public static void displayResult(ArrayList<TripleSetInfo> tripleSetInfoList){
		
		ArrayList<CorrectAnswer> correctAnswerList = SeedSetter.getCorrectAnswerList();
		int correctAnswerNum = correctAnswerList.size();
		int allExtractionNum = tripleSetInfoList.size();
		int correctExtractionNum = 0;
		
		for(TripleSetInfo tripleSetInfo : tripleSetInfoList){
			int sentenceId = tripleSetInfo.getSentenceId();
			int medicinePhraseId = tripleSetInfo.getMedicinePhraseId();
			int targetPhraseId = tripleSetInfo.getTargetPhraseId();
			int effectPhraseId = tripleSetInfo.getEffectPhraseId();
			
			for(CorrectAnswer correctAnswer : correctAnswerList){
				if(correctAnswer.getSentenceId() == sentenceId && correctAnswer.getMedicinePhraseId() == medicinePhraseId
						&& correctAnswer.getTargetPhraseId() == targetPhraseId && correctAnswer.getEffectPhraseId() == effectPhraseId){
					correctExtractionNum ++;
				}
			}
		}
		
		ArrayList<Double> resultList = Calculator.getResultList(allExtractionNum, correctExtractionNum, correctAnswerNum);
		
		System.out.println("適合率：" + resultList.get(0));
		System.out.println("再現率：" + resultList.get(1));
		System.out.println("F値：" + resultList.get(2));
		
	}



}
