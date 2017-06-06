package bootstrapping;

import java.io.IOException;
import java.util.ArrayList;

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

	public static ArrayList<String> getKeyWordList(ArrayList<Sentence> sentenceList, String target, String effect) 
														throws SAXException, IOException, ParserConfigurationException{

		ArrayList<String> keyWordList = new ArrayList<String>();

		for(Sentence sentence : sentenceList){
			//if(sentence.equals("")){ continue; }
			String text = sentence.getText();
			//int id = sentence.getId();
			
			//構文解析結果をXml形式で取得
			ArrayList<String> xmlList = new ArrayList<String>();
			xmlList = SyntaxAnalys.GetSyntaxAnalysResultXml(text);

			//phraseList取得　(phrase,morphemeの生成)
			ArrayList<Phrase> phraseList = XmlReader.GetPhraseList(xmlList);
			keyWordList.addAll(SearchKeyWord.getKeyWordList(phraseList, target, effect, 3));
			keyWordList.addAll(SearchKeyWord.getKeyWordList(phraseList, target, effect, 4));
			
//			System.out.println(id);
		}
		return keyWordList;
	}

}
