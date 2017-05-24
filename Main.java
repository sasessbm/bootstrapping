package bootstrapping;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import makeTriplicity.*;

public class Main {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ

	}
	
	public static void run(ArrayList<String> sentenceList, String target, String effect) throws SAXException, IOException, ParserConfigurationException{
		
		for(String sentence : sentenceList){
			GetKeyWord.getKeyWord(sentence, target, effect);
		}
		
		
	}

}
