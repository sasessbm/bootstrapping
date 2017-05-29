package bootstrapping;
import java.util.ArrayList;

import makeTriplicity.*;

public class ChangePhraseForm {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}
	
	public static String changePhraseForm(Phrase phrase, int phraseType){

		//Element element = new Element();
		ArrayList<Morpheme> morphemeList = phrase.getMorphemeList();
		String text = "";
		ArrayList<Morpheme> elementMorphemeList = new ArrayList<Morpheme>();
		//int denialIndex = 0;
		boolean isVerb = false;


		//		for(Morpheme morpheme : morphemeList){
		//			//System.out.println(morpheme.getMorphemeText());
		//			if(morpheme.getOriginalForm().equals("ない")){
		//				denialIndex ++;
		//			}
		//		}

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
		
		//phrase.setPhraseText(text);
		//phrase.setMorphemeList(elementMorphemeList);

		//element.setMorphemeList(elementMorphemeList);

		return text;
	}

}
