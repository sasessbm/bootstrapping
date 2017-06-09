package bootstrapping;

import java.util.ArrayList;

import makeTriplicity.TripleSet;

public class KeyWord {
	
	private String keyWordText;
	private ArrayList<TripleSet> tripleSetList;
	
	public KeyWord(){
		
	}

	public String getKeyWordText() {
		return keyWordText;
	}

	public void setKeyWordText(String keyWordText) {
		this.keyWordText = keyWordText;
	}

	public ArrayList<TripleSet> getTripleSetList() {
		return tripleSetList;
	}

	public void setTripleSetList(ArrayList<TripleSet> tripleSetList) {
		this.tripleSetList = tripleSetList;
	}

}
