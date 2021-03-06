package bootstrapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import makeTriplicity.*;

public class GetSentence {

	//	private static ArrayList<String> medicineNameList 
	//	= GetTextFileList.fileRead("C:\\Users\\sase\\Desktop\\実験\\リスト\\medicine_name.txt");

	public static ArrayList<Sentence> getSentenceList
	(ArrayList<Integer> idList, ArrayList<String> medicineNameList) throws Exception{

		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();

		//recordList取得　(recordの生成)
		ArrayList<Record> recordList = GetRecord.getRecordList(idList);

		sentenceList = makeSentenceList(recordList, medicineNameList);

		return sentenceList;
	}

	public static ArrayList<Sentence> getSentenceList
	(int startRecordNum, int endRecordNum, ArrayList<String> medicineNameList) throws Exception{

		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();

		//recordList取得　(recordの生成)
		ArrayList<Record> recordList = GetRecord.getRecordList(startRecordNum, endRecordNum);

		sentenceList = makeSentenceList(recordList, medicineNameList);

		return sentenceList;
	}

	public static ArrayList<Sentence> getRandomSentenceList
	(int sentenceNum, int startIdIndex, int endIdIndex, ArrayList<String> medicineNameList) 
			throws SAXException, IOException, ParserConfigurationException{

		ArrayList<Integer> randomIdList = Logic.getRandomIdList(sentenceNum, startIdIndex, endIdIndex);
		ArrayList<Record> recordList = GetRecord.getRecordList(randomIdList);
		ArrayList<Sentence> sentenceList = makeProperSentenceList(recordList, medicineNameList);
		ArrayList<Integer> usedIdList = randomIdList;
		ArrayList<Integer> additionalRandomIdList = new ArrayList<Integer>();
		
		while(true){
			int diff = sentenceNum - sentenceList.size();
			if(diff == 0){ break; }
			additionalRandomIdList = Logic.getAdditionalRandomIdList(diff, startIdIndex, endIdIndex, usedIdList);
			recordList = GetRecord.getRecordList(additionalRandomIdList);
			sentenceList.addAll(makeProperSentenceList(recordList, medicineNameList));
			usedIdList.addAll(additionalRandomIdList);
		}
		
		//Collections.sort(sentenceList.get);

		return sentenceList;

	}

	public static ArrayList<Sentence> makeSentenceList(ArrayList<Record> recordList, ArrayList<String> medicineNameList) 
			throws SAXException, IOException, ParserConfigurationException{

		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();
		ArrayList<String> sentenceTextList = new ArrayList<String>();
		int sentenceId = 0;

		//レコード単位
		for(Record record : recordList){
			String snippetText = record.getSnippet().getSnippetText();
			String TargetMedicineName = record.getMedicineName();
			int recordId = record.getId();
			if(!snippetText.contains(TargetMedicineName)){ continue; }  //対象薬剤名が無いスニペットは対象としない

			//SentenceList取得
			snippetText = PreProcessing.deleteBothSideDots(snippetText);	//両サイドの「・・・」を削除
			sentenceTextList = PreProcessing.getSentenceTextList(snippetText);

			for(String sentenceText : sentenceTextList){
				//if(sentence.equals(null) || sentence.equals("")){ continue; }	//空白の文は対象としない
				if(!sentenceText.contains(TargetMedicineName)){ continue; } //対象薬剤名を含まない文は対象としない

				//前処理
				sentenceText = PreProcessing.deleteParentheses(sentenceText);	//括弧削除
				sentenceText = PreProcessing.deleteSpace(sentenceText);	//スペース削除
				if(sentenceText.equals(null) || sentenceText.equals("")){ continue; }	//空白の文は対象としない

				TreeMap<Integer, String> medicineNameMap = 
						PreProcessing.getMedicineNameMap(sentenceText, medicineNameList); //薬剤名取得
				sentenceText = PreProcessing.replaceMedicineName(sentenceText, medicineNameMap);	//薬剤名置き換え

				//構文解析結果をXml形式で取得
				ArrayList<String> xmlList = new ArrayList<String>();
				xmlList = SyntaxAnalys.GetSyntaxAnalysResultXml(sentenceText);

				//文節リスト取得　(phrase,morphemeの生成)
				ArrayList<Phrase> phraseReplaceList = XmlReader.GetPhraseList(xmlList);
				ArrayList<Phrase> phraseRestoreList = new ArrayList<Phrase>();

				//文節リスト更新
				for(Phrase replacePhrase : phraseReplaceList){
					ArrayList<Morpheme> morphemeRestoreList = new ArrayList<Morpheme>();
					for(Morpheme morpheme : replacePhrase.getMorphemeList()){
						morphemeRestoreList.add(new Morpheme(morpheme.getId(), morpheme.getMorphemeText(), morpheme.getFeature()));
					}
					Phrase restorePhrase = 
							new Phrase(replacePhrase.getId(), replacePhrase.getPhraseText(), replacePhrase.getDependencyIndex(), morphemeRestoreList);
					phraseRestoreList.add(restorePhrase);
				}

				//薬剤名を戻す
				phraseRestoreList = PostProcessing.restoreMedicineName(phraseRestoreList, medicineNameMap);

				sentenceId ++;
				//sentence生成
				Sentence sentence = new Sentence(sentenceText, recordId, sentenceId, phraseReplaceList, phraseRestoreList);
				sentenceList.add(sentence);
			}
		}
		return sentenceList;
	}

	public static ArrayList<Sentence> makeProperSentenceList(ArrayList<Record> recordList, ArrayList<String> medicineNameList) throws SAXException, IOException, ParserConfigurationException{

		ArrayList<Sentence> sentenceList = new ArrayList<Sentence>();
		int sentenceId = 0;

		for(Record record : recordList){
			String snippetText = record.getSnippet().getSnippetText();
			ArrayList<String> sentenceTextCheckList = PreProcessing.getSentenceTextList(snippetText);
			int recordId = record.getId();

			if(sentenceTextCheckList.size() <= 2){ continue; } //3文以上のスニペットを適用

			for(int i = 0; i < sentenceTextCheckList.size(); i++){

				if(i==0 || i == sentenceTextCheckList.size() - 1){ continue; } //最初と最後以外の文を適用

				String sentenceText = sentenceTextCheckList.get(i);

				if(!Logic.containsMedicine(sentenceText)){ continue; } //薬剤名が含まれる文を適用
				
				if(sentenceText.contains("......")){ continue; } //文の不自然な区切りを防ぐ

				//前処理
				sentenceText = PreProcessing.deleteParentheses(sentenceText);	//括弧削除
				sentenceText = PreProcessing.deleteSpace(sentenceText);	//スペース削除

				if(sentenceText.equals("")){ continue; }	//空白の文は対象としない
				
				String sentenceTextBefore = sentenceText;

				TreeMap<Integer, String> medicineNameMap = 
						PreProcessing.getMedicineNameMap(sentenceText, medicineNameList); //薬剤名取得
				sentenceText = PreProcessing.replaceMedicineName(sentenceText, medicineNameMap);	//薬剤名置き換え

				//構文解析結果をXml形式で取得
				ArrayList<String> xmlList = new ArrayList<String>();
				xmlList = SyntaxAnalys.GetSyntaxAnalysResultXml(sentenceText);

				//文節リスト取得　(phrase,morphemeの生成)
				ArrayList<Phrase> phraseReplaceList = XmlReader.GetPhraseList(xmlList);
				ArrayList<Phrase> phraseRestoreList = new ArrayList<Phrase>();

				//文節リスト更新
				for(Phrase replacePhrase : phraseReplaceList){
					ArrayList<Morpheme> morphemeRestoreList = new ArrayList<Morpheme>();
					for(Morpheme morpheme : replacePhrase.getMorphemeList()){
						morphemeRestoreList.add(new Morpheme(morpheme.getId(), morpheme.getMorphemeText(), morpheme.getFeature()));
					}
					Phrase restorePhrase = 
							new Phrase(replacePhrase.getId(), replacePhrase.getPhraseText(), replacePhrase.getDependencyIndex(), morphemeRestoreList);
					phraseRestoreList.add(restorePhrase);
				}
				//薬剤名を戻す
				phraseRestoreList = PostProcessing.restoreMedicineName(phraseRestoreList, medicineNameMap);
				sentenceId++;
				//sentence生成
				Sentence sentence = new Sentence(sentenceTextBefore, recordId, sentenceId, phraseReplaceList, phraseRestoreList);
				sentenceList.add(sentence);
				break; //1レコードから1文とする
			}
		}
		return sentenceList;
	}

}
