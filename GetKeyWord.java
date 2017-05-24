package bootstrapping;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import makeTriplicity.*;

public class GetKeyWord {
	
	private static  ArrayList<Phrase> phraseList;

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}
	
	public static ArrayList<String> getKeyWord(String sentence, String target, String effect) throws SAXException, IOException, ParserConfigurationException{
		
		ArrayList<String> keyWordlist = new ArrayList<String>();
		if(!(sentence.contains(target) && sentence.contains(effect))){ return null; }
		
		//構文解析結果をXml形式で取得
		ArrayList<String> xmlList = new ArrayList<String>();
		xmlList = SyntaxAnalys.GetSyntaxAnalysResultXml(sentence);

		//phraseList取得　(phrase,morphemeの生成)
		//ArrayList<Phrase> phraseList = new ArrayList<Phrase>();
		phraseList = XmlReader.GetPhraseList(xmlList);
		
		int targetDependencyIndex = -1;
		
		for(Phrase phrase : phraseList){
			String text = phrase.getPhraseText();
			if(text.contains(target)){
				targetDependencyIndex = phrase.getDependencyIndex();
				
				continue;
			}
		}
		
		return keyWordlist;
		
	}
	
	public static int searchEffectPhrase(int targetDependencyIndex, String effect){
		
		
		
	}
	
	
	
	
	

}
