package bootstrapping;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import makeTriplicity.*;

public class Main {
	
	private static ArrayList<String> medicineNameList 
	= GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_name.txt");

	public static void main(String[] args) throws Exception {
		
		ArrayList<TripleSet> tripleSetList = makeTriplicity.Main.run(0, 500);
		ArrayList<Integer> idList = new ArrayList<Integer>();
		idList.add(2);
		idList.add(8);
		idList.add(19);
		idList.add(65);
		idList.add(115);
		idList.add(125);
		idList.add(167);
		idList.add(185);
		idList.add(194);
		idList.add(204);
		idList.add(225);
		idList.add(226);
		idList.add(257);
		idList.add(272);
		idList.add(298);
		idList.add(335);
		idList.add(350);
		idList.add(372);
		idList.add(377);
		idList.add(390);
		idList.add(412);
		idList.add(422);
		idList.add(460);
		idList.add(480);
		idList.add(482);
		idList.add(485);
		
		ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(500, 1000);
		//ArrayList<Sentence> sentenceList = GetSentence.getSentenceList(idList);
		
		ArrayList<String> keyWordList = new ArrayList<String>();
		
		for(TripleSet tripleSet : tripleSetList){
			String target = tripleSet.getTargetElement().getText();
			String effect = tripleSet.getEffectElement().getText();
			keyWordList.addAll(GetKeyWordList.getKeyWordList(medicineNameList, sentenceList, target, effect));
		}
		
		for(String keyWord : keyWordList){
			System.out.println(keyWord);
		}

	}
	
	

}
