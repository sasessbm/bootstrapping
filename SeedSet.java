package bootstrapping;

import java.util.ArrayList;

public class SeedSet {
	
	private String target;
	private String effect;
	private ArrayList<KeyWord> keyWordList;
	
	public SeedSet(String target, String effect){
		this.target = target;
		this.effect = effect;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public ArrayList<KeyWord> getKeyWordList() {
		return keyWordList;
	}

	public void setKeyWordList(ArrayList<KeyWord> keyWordList) {
		this.keyWordList = keyWordList;
	}
	
	public int getKeyWordNum(String keyWordText){
		int count = 0;
		//String keyWordText = keyWord.getKeyWordText();
		for(KeyWord key : keyWordList){
			if(key.getKeyWordText().equals(keyWordText)){ count++; }
		}
		return count;
	}

}
