package bootstrapping;

import java.util.ArrayList;

import makeTriplicity.*;

public class Logic {

	public static ArrayList<String> medicineNameList;

	//	public static void main(String[] args) throws Exception{
	//		
	//		ArrayList<KeyWord> list1 = new ArrayList<KeyWord>();
	//		list1.add(new KeyWord("あああ"));
	//		list1.add(new KeyWord("いいい"));
	//		list1.add(new KeyWord("ううう"));
	//		list1.add(new KeyWord("ooo"));
	//		
	//		ArrayList<KeyWord> list2 = new ArrayList<KeyWord>();
	//		list2.add(new KeyWord("えええ"));
	//		list2.add(new KeyWord("あああ"));
	//		list2.add(new KeyWord("いいい"));
	//		
	//		list2 = deleteOverlappingFromList(list2, list1);
	//		
	//		for(KeyWord key : list2){
	//			System.out.println(key.getKeyWordText());
	//		}
	//	}


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

//	public static ArrayList<TripleSet> deleteOverlappingFromListForTripleSet
//	(ArrayList<TripleSet> removeList, ArrayList<TripleSet> compareList){
//
//		for(TripleSet tripleSet : compareList){
//			
//			String targetBase = tripleSet.getTargetElement().getText();
//			String effectBase = tripleSet.getEffectElement().getText();
//			
//			for(int i = removeList.size() - 1; i >= 0; i--){
//				if(removeList.get(i).getTargetElement().getText().equals(targetBase) && ){
//					removeList.remove(i);
//				}
//			}
//		}
//		return removeList;
//	}

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



}
