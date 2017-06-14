package bootstrapping;

import java.util.ArrayList;

import makeTriplicity.TripleSet;

public class Logic {

	//重複した組を削除
	public static ArrayList<KeyWord> deleteOverlappingFromList
							(ArrayList<KeyWord> removeList, ArrayList<KeyWord> compareList){

		//ArrayList<String> tripleSetListBase = tripleSetList;

		for(int i = 0 ; i < compareList.size() ; i++){
			int sameCount = 0;
			String textBase = compareList.get(i).getKeyWordText();

			for(KeyWord key : removeList){
				if(key.getKeyWordText().equals(textBase)){
					sameCount++;
				}
			}
			if(sameCount>=2){
				removeList.remove(compareList.get(i));
			}
		}
		return removeList;
	}

}
