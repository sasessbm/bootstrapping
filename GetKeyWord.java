package bootstrapping;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import makeTriplicity.*;

public class GetKeyWord {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		
		String sentence = "TARGETMEDICINE後、９か月目のMRIの結果・・ 以前壊死を起こしていて、TARGETMEDICINE治療で壊死が消えていた部分一部に、壊死が再発していました";
		String target = "壊死";
		String effect = "消えて";
		String keyWord = "";
		
		keyWord = getKeyWord(sentence, target, effect);
		
		System.out.println(keyWord);

	}
	
	public static String getKeyWord(String sentence, String target, String effect) 
								throws SAXException, IOException, ParserConfigurationException{
		
		String keyWord = "";
		
		if(!(sentence.contains(target) && sentence.contains(effect))){ return null; }
		
		//構文解析結果をXml形式で取得
		ArrayList<String> xmlList = new ArrayList<String>();
		xmlList = SyntaxAnalys.GetSyntaxAnalysResultXml(sentence);

		//phraseList取得　(phrase,morphemeの生成)
		ArrayList<Phrase> phraseList = XmlReader.GetPhraseList(xmlList);
		keyWord = P3SearchKeyWord.keyWord(phraseList, target, effect);
		//keyWord = P4SearchKeyWord.keyWord(phraseList, target, effect);
		
		return keyWord;
	}
	
	
	
	
	

}
