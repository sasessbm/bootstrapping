package bootstrapping;

import java.util.ArrayList;

public class KeyWord {
	
	private String keyWordText;
	private ArrayList<DoubleSet> setList;
	
	public KeyWord(){
		
	}

	public String getKeyWordText() {
		return keyWordText;
	}

	public void setKeyWordText(String keyWordText) {
		this.keyWordText = keyWordText;
	}

	public ArrayList<DoubleSet> getSetList() {
		return setList;
	}

	public void setSetList(ArrayList<DoubleSet> setList) {
		this.setList = setList;
	}

}
