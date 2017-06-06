package bootstrapping;

import java.util.ArrayList;
import java.util.TreeMap;

import makeTriplicity.*;

public class GetSentence {

	private static ArrayList<String> medicineNameList 
	= GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_name.txt");

	public static ArrayList<Sentence> getSentenceList(ArrayList<Integer> idList) throws Exception{

		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();
		ArrayList<String> sentenceTmpList = new ArrayList<String>();

		//recordList取得　(recordの生成)
		ArrayList<Record> recordList = GetRecord.getRecordList(idList);

		//レコード単位
		for(Record record : recordList){
			String snippetText = record.getSnippet().getSnippetText();
			String TargetMedicineName = record.getMedicineName();
			int id = record.getId();
			if(!snippetText.contains(TargetMedicineName)){ continue; }  //対象薬剤名が無いスニペットは対象としない

			//SentenceList取得
			snippetText = PreProcessing.deleteBothSideDots(snippetText);	//両サイドの「・・・」を削除
			sentenceTmpList = PreProcessing.getSentenceTextList(snippetText);

			for(String text : sentenceTmpList){
				//if(sentence.equals(null) || sentence.equals("")){ continue; }	//空白の文は対象としない
				if(!text.contains(TargetMedicineName)){ continue; } //対象薬剤名を含まない文は対象としない

				//前処理
				TreeMap<Integer, String> medicineNameMap = 
						PreProcessing.getMedicineNameMap(text,medicineNameList); //薬剤名取得
				text = PreProcessing.replaceMedicineName(text, medicineNameMap);	//薬剤名置き換え
				text = PreProcessing.deleteParentheses(text);	//括弧削除
				text = PreProcessing.deleteSpace(text);	//スペース削除

				if(text.equals(null) || text.equals("")){ continue; }	//空白の文は対象としない
				Sentence sentence = new Sentence(text,id); 
				sentenceList.add(sentence);
			}
		}
		return sentenceList;
	}

	public static ArrayList<String> getSentenceList(int startRecordNum, int endRecordNum) throws Exception{

		ArrayList<String> sentenceList = new ArrayList<String>();
		ArrayList<String> sentenceTmpList = new ArrayList<String>();

		//recordList取得　(recordの生成)
		ArrayList<Record> recordList = GetRecord.getRecordList(startRecordNum, endRecordNum);

		//レコード単位
		for(Record record : recordList){
			String snippetText = record.getSnippet().getSnippetText();
			String TargetMedicineName = record.getMedicineName();
			if(!snippetText.contains(TargetMedicineName)){ continue; }  //対象薬剤名が無いスニペットは対象としない

			//SentenceList取得
			snippetText = PreProcessing.deleteBothSideDots(snippetText);	//両サイドの「・・・」を削除
			sentenceTmpList = PreProcessing.getSentenceTextList(snippetText);

			for(String sentence : sentenceTmpList){
				//if(sentence.equals(null) || sentence.equals("")){ continue; }	//空白の文は対象としない
				if(!sentence.contains(TargetMedicineName)){ continue; } //対象薬剤名を含まない文は対象としない

				//前処理
				TreeMap<Integer, String> medicineNameMap = 
						PreProcessing.getMedicineNameMap(sentence,medicineNameList); //薬剤名取得
				sentence = PreProcessing.replaceMedicineName(sentence, medicineNameMap);	//薬剤名置き換え
				sentence = PreProcessing.deleteParentheses(sentence);	//括弧削除
				sentence = PreProcessing.deleteSpace(sentence);	//スペース削除

				if(sentence.equals(null) || sentence.equals("")){ continue; }	//空白の文は対象としない
				sentenceList.add(sentence);
			}
		}
		return sentenceList;
	}

}
