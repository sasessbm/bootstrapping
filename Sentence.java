package bootstrapping;

import java.util.ArrayList;

import makeTriplicity.Phrase;

public class Sentence {
	
	private String text;
	private int id;
	//private TreeMap<Integer, String> medicineNameMap;
	private ArrayList<Phrase> phraseReplaceList;
	private ArrayList<Phrase> phraseRestoreList;
	
	public Sentence(String text, int id, ArrayList<Phrase> phraseReplaceList, ArrayList<Phrase> phraseRestoreList){
		this.text = text;
		this.id = id;
		this.phraseReplaceList = phraseReplaceList;
		this.phraseRestoreList = phraseRestoreList;
		//this.medicineNameMap = medicineNameMap;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
//	public TreeMap<Integer, String> getMedecineNameMap(){
//		return medicineNameMap;
//	}
//	
//	public void setMedicineNameMap(TreeMap<Integer, String> medicineNameMap){
//		this.medicineNameMap = medicineNameMap;
//	}

	public ArrayList<Phrase> getPhraseReplaceList() {
		return phraseReplaceList;
	}

	public void setPhraseReplaceList(ArrayList<Phrase> phraseReplaceList) {
		this.phraseReplaceList = phraseReplaceList;
	}

	public ArrayList<Phrase> getPhraseRestoreList() {
		return phraseRestoreList;
	}

	public void setPhraseRestoreList(ArrayList<Phrase> phraseRestoreList) {
		this.phraseRestoreList = phraseRestoreList;
	}
	
	

}
