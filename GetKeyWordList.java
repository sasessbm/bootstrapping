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

	public static ArrayList<KeyWord> getKeyWordList
	(ArrayList<String> medicineNameList, ArrayList<Sentence> sentenceList, String target, String effect) 
			throws SAXException, IOException, ParserConfigurationException{
		ArrayList<KeyWord> keyWordList = new ArrayList<KeyWord>();

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

			//手がかり語探索
			P3keyWordIdList.addAll(SearchKeyWord.getKeyWordIdList(medicineNameList, phraseList, target, effect, 3));
			P4keyWordIdList.addAll(SearchKeyWord.getKeyWordIdList(medicineNameList, phraseList, target, effect, 4));

			//手がかり語リストに追加
			keyWordList = addKeyWord(keyWordList, P3keyWordIdList, phraseList, 1);
			keyWordList = addKeyWord(keyWordList, P4keyWordIdList, phraseList, 0);
			//System.out.println(sentence.getId());
		}
		return keyWordList;
	}

	public static ArrayList<KeyWord> addKeyWord
	(ArrayList<KeyWord> keyWordList, ArrayList<Integer> keyWordIdList, ArrayList<Phrase> phraseList, int keyWordIndex){
		
		if(keyWordIdList.size() == 0){ return keyWordList; }
		for(int id : keyWordIdList){
			KeyWord keyWord = new KeyWord();
			Morpheme morpheme = phraseList.get(id).getMorphemeList().get(keyWordIndex);
			if(!morpheme.getOriginalForm().equals("*")){
				//keyWordList.add(morpheme.getOriginalForm());
				keyWord.setKeyWordText(morpheme.getOriginalForm());
				//System.out.println(morpheme.getOriginalForm());
			}else{
				//keyWordList.add(morpheme.getMorphemeText());
				keyWord.setKeyWordText(morpheme.getMorphemeText());
				//System.out.println(morpheme.getMorphemeText());
			}
			keyWordList.add(keyWord);
		}
		return keyWordList;
	}

}
