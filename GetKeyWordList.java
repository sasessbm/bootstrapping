package bootstrapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import makeTriplicity.*;


public class GetKeyWordList {

//	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
//
//		String sentence = "TARGETMEDICINE後、９か月目のMRIの結果・・ 以前壊死を起こしていて、TARGETMEDICINE治療で壊死が消えていた部分一部に、壊死が再発していました";
//		String target = "壊死";
//		String effect = "消えて";
//		String keyWord = "";
//
//		keyWord = getKeyWord(sentence, target, effect);
//
//		System.out.println(keyWord);
//
//	}

	public static ArrayList<String> getKeyWordList
	(ArrayList<String> medicineNameList, ArrayList<Sentence> sentenceList, String target, String effect) 
														throws SAXException, IOException, ParserConfigurationException{

		ArrayList<String> keyWordList = new ArrayList<String>();

		for(Sentence sentence : sentenceList){
			ArrayList<Integer> P3keyWordIdList = new ArrayList<Integer>();
			ArrayList<Integer> P4keyWordIdList = new ArrayList<Integer>();
			
			String sentenceText = sentence.getText();
			
			//構文解析結果をXml形式で取得
			ArrayList<String> xmlList = new ArrayList<String>();
			xmlList = SyntaxAnalys.GetSyntaxAnalysResultXml(sentenceText);

			//phraseList取得　(phrase,morphemeの生成)
			ArrayList<Phrase> phraseList = XmlReader.GetPhraseList(xmlList);
			
			//薬剤名を戻す
			phraseList = PostProcessing.restoreMedicineName(phraseList, sentence.getMedecineNameMap());
			
			P3keyWordIdList.addAll(SearchKeyWord.getKeyWordIdList(medicineNameList, phraseList, target, effect, 3));
			P4keyWordIdList.addAll(SearchKeyWord.getKeyWordIdList(medicineNameList, phraseList, target, effect, 4));
			
			if(P3keyWordIdList.size() != 0){ 
				for(int id : P3keyWordIdList){
					Morpheme morpheme = phraseList.get(id).getMorphemeList().get(1);
					if(!morpheme.getOriginalForm().equals("*")){
						keyWordList.add(morpheme.getOriginalForm());
						System.out.println(morpheme.getOriginalForm());
					}else{
						keyWordList.add(morpheme.getMorphemeText());
						System.out.println(morpheme.getMorphemeText());
					}
					
				}
			}
			
			if(P4keyWordIdList.size() != 0){
				for(int id : P4keyWordIdList){
					Morpheme morpheme = phraseList.get(id).getMorphemeList().get(0);
					if(!morpheme.getOriginalForm().equals("*")){
						keyWordList.add(morpheme.getOriginalForm());
						System.out.println(morpheme.getOriginalForm());
					}else{
						keyWordList.add(morpheme.getMorphemeText());
						System.out.println(morpheme.getMorphemeText());
					}
				}
			}
			
			System.out.println(sentence.getId());
		}
		return keyWordList;
	}

}
