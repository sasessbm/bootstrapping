package bootstrapping;

import java.util.ArrayList;
import java.util.TreeMap;

import makeTriplicity.*;

public class GetSentence {

	public static ArrayList<String> getSentenceList(int startRecordNum, int endRecordNum) throws Exception{
		
		ArrayList<String> sentenceList = new ArrayList<String>();
		ArrayList<String> sentenceTmpList = new ArrayList<String>();
		
		//recordList取得　(recordの生成)
		ArrayList<Record> recordList = GetRecordList.getRecordList(startRecordNum, endRecordNum);
		
		//レコード単位
		for(Record record : recordList){
			
			String snippetText = record.getSnippet().getSnippetText();
			String TargetMedicineName = record.getMedicineName();
			if(!snippetText.contains(TargetMedicineName)){ continue; }  //対象薬剤名が無いスニペットは対象としない
			
			//SentenceList取得
			snippetText = PreProcessing.deleteBothSideDots(snippetText);	//両サイドの「・・・」を削除
			//ArrayList<String> sentenceTextList = new ArrayList<String>();
			//sentenceList.addAll(PreProcessing.getSentenceTextList(snippetText));
			sentenceTmpList = PreProcessing.getSentenceTextList(snippetText);
			
			for(String sentence : sentenceTmpList){
				//if(sentence.equals(null) || sentence.equals("")){ continue; }	//空白の文は対象としない
				if(!sentence.contains(TargetMedicineName)){ continue; } //対象薬剤名を含まない文は対象としない
				//sentenceTextBefore = sentenceText;

				//前処理
				TreeMap<Integer, String> otherMedicineNameMap = 
						PreProcessing.getOtherMedicineNameMap(sentence,TargetMedicineName);	//対象薬剤名以外の薬剤名取得
				sentence = 
						PreProcessing.replaceMedicineName(sentence, TargetMedicineName, otherMedicineNameMap);	//薬剤名置き換え
				sentence = PreProcessing.deleteParentheses(sentence);	//括弧削除
				sentence = PreProcessing.deleteSpace(sentence);	//スペース削除

				if(sentence.equals(null) || sentence.equals("")){ continue; }	//空白の文は対象としない
				sentenceList.add(sentence);
			}
		}
		
		return sentenceList;
		
	}

}
