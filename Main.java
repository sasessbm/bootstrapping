package bootstrapping;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import makeTriplicity.*;

public class Main {

	public static void main(String[] args) throws Exception {
		
		ArrayList<String> sentenceList = GetSentence.getSentenceList(0, 1000);
		ArrayList<TripleSet> tripleSetList = makeTriplicity.Main.run(0, 1000);
		ArrayList<String> keyWordList = new ArrayList<String>();
		for(TripleSet tripleSet : tripleSetList){
			String target = tripleSet.getTargetElement().getText();
			String effect = tripleSet.getEffectElement().getText();
			keyWordList.addAll(GetKeyWordList.getKeyWordList(sentenceList, target, effect));
		}
		
		for(String keyWord : keyWordList){
			System.out.println(keyWord);
		}

	}
	
	

}
