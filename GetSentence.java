package bootstrapping;

import java.util.ArrayList;
import java.util.TreeMap;

import makeTriplicity.*;

public class GetSentence {

	private static ArrayList<String> medicineNameList 
	= GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_name.txt");

	public static ArrayList<Sentence> getSentenceList(ArrayList<Integer> idList) throws Exception{

		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();

		//recordList取得　(recordの生成)
		ArrayList<Record> recordList = GetRecord.getRecordList(idList);
		
		sentenceList = makeSentenceList(recordList);
		
		return sentenceList;
	}

	public static ArrayList<Sentence> getSentenceList(int startRecordNum, int endRecordNum) throws Exception{

		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();

		//recordList取得　(recordの生成)
		ArrayList<Record> recordList = GetRecord.getRecordList(startRecordNum, endRecordNum);
		
		sentenceList = makeSentenceList(recordList);
		
		return sentenceList;
	}

	public static ArrayList<Sentence> makeSentenceList(ArrayList<Record> recordList){

		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();
		ArrayList<String> sentenceTextList = new ArrayList<String>();

		//レコード単位
		for(Record record : recordList){
			String snippetText = record.getSnippet().getSnippetText();
			String TargetMedicineName = record.getMedicineName();
			int id = record.getId();
			if(!snippetText.contains(TargetMedicineName)){ continue; }  //対象薬剤名が無いスニペットは対象としない

			//SentenceList取得
			snippetText = PreProcessing.deleteBothSideDots(snippetText);	//両サイドの「・・・」を削除
			sentenceTextList = PreProcessing.getSentenceTextList(snippetText);

			for(String sentenceText : sentenceTextList){
				//if(sentence.equals(null) || sentence.equals("")){ continue; }	//空白の文は対象としない
				if(!sentenceText.contains(TargetMedicineName)){ continue; } //対象薬剤名を含まない文は対象としない

				//前処理
				TreeMap<Integer, String> medicineNameMap = 
						PreProcessing.getMedicineNameMap(sentenceText,medicineNameList); //薬剤名取得
				sentenceText = PreProcessing.replaceMedicineName(sentenceText, medicineNameMap);	//薬剤名置き換え
				sentenceText = PreProcessing.deleteParentheses(sentenceText);	//括弧削除
				sentenceText = PreProcessing.deleteSpace(sentenceText);	//スペース削除

				if(sentenceText.equals(null) || sentenceText.equals("")){ continue; }	//空白の文は対象としない
				
				Sentence sentence = new Sentence(sentenceText, id, medicineNameMap); 
				sentenceList.add(sentence);
			}
		}
		return sentenceList;
	}

}
