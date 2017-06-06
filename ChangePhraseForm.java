package bootstrapping;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import makeTriplicity.*;

public class ChangePhraseForm {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public static String changePhraseForm(ArrayList<Morpheme> morphemeList, int phraseType){

		String text = "";
		ArrayList<Morpheme> elementMorphemeList = new ArrayList<Morpheme>();
		boolean isVerb = false;

		for(Morpheme morpheme : morphemeList){

			//助詞が出現("の"以外) 
			if(morpheme.getPartOfSpeech().equals("助詞") & !morpheme.getOriginalForm().equals("の") ){ break; }

			if(morpheme.getOriginalForm().equals("、") || morpheme.getOriginalForm().equals("。")){ break; }
			//System.out.println(morpheme.getMorphemeText() + "→" + morpheme.getPartOfSpeechDetails());

			if(morpheme.getPartOfSpeech().equals("動詞")){

				//if(denialIndex % 2 == 1 || morphemeIndex != morphemeList.size()){
				//text += morpheme.getMorphemeText(); 
				//elementMorphemeList.add(morpheme);
				//}

				isVerb = true;

				elementMorphemeList.add(morpheme);

			}else{
				isVerb = false;
				elementMorphemeList.add(morpheme);
			}
		}

		for(int i = 0; i < elementMorphemeList.size(); i++){

			if(isVerb && i == elementMorphemeList.size() - 1 && phraseType == 2){
				text += elementMorphemeList.get(i).getOriginalForm(); //「効果」要素で、最後が動詞だった時
			}else{
				text += elementMorphemeList.get(i).getMorphemeText();
			}
		}

		//		if(denialIndex % 2 == 1){
		//			text += "ない";
		//			//elementMorphemeList.add(morpheme);
		//		}
		
		text = deleteParentheses(text);
		
		return text;
	}
	
	public static String deleteParentheses(String text){

		String regex = "\\(|\\)|\\（|\\）|「|」|『|』";
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(text);

		while(matcher.find()){
			String matchstr = matcher.group();
			text = PostProcessing.deleteCharacter(text, matchstr);
			//System.out.println(targetText);
		}
		return text;
	}

}
