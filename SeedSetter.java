package bootstrapping;

import java.util.ArrayList;

import makeTriplicity.Element;
import makeTriplicity.TripleSet;

public class SeedSetter {
	
	public static ArrayList<TripleSet> getSeedList(){
		
		ArrayList<String> seedTextList = new ArrayList<String>();
		
//		seedTextList.add("わかっ,硝子体内混濁");
//		seedTextList.add("効き目,ある");
//		seedTextList.add("生理,止まる");
//		seedTextList.add("カルボプラチン,望んだのです");
//		seedTextList.add("副作用,観察する");
//		seedTextList.add("TJ療法,する");
//		seedTextList.add("アバスチンの説明書,もらったのです");
//		seedTextList.add("こと,整理できた");
//		seedTextList.add("組み合わせ,あ");
//		seedTextList.add("クリーム,塗る");
//		seedTextList.add("効果,得られる");
//		seedTextList.add("今のところ,続ける");
//		seedTextList.add("ブロ友さんの記事で,～");
//		seedTextList.add("鼻,かみすぎる");
//		seedTextList.add("6本の点滴,終わるの");
//		seedTextList.add("新生血管の瘢痕跡,見えなくなる");
//		seedTextList.add("血,止まりにくい");
//		seedTextList.add("CT,確認できない");
//		seedTextList.add("CEA,上がる");
//		seedTextList.add("壊死,消える");
//		seedTextList.add("異常,きたす");
//		seedTextList.add("２か月？の間隔,空ける");
//		seedTextList.add("今回,効く");
//		seedTextList.add("鼻血,出す");
//		seedTextList.add("血圧,上がりやすくなる");
//		seedTextList.add("副作用,だす");
//		seedTextList.add("高血圧,ある");
//		seedTextList.add("血圧上昇,ある");
		
		seedTextList.add("生理,止まる");
		seedTextList.add("血,止まりにくい");
		seedTextList.add("壊死,消える");
		seedTextList.add("鼻血,出す");
		seedTextList.add("血圧,上がりやすくなる");
		seedTextList.add("高血圧,ある");
		seedTextList.add("血圧上昇,ある");
		seedTextList.add("胃潰瘍,出来る");
		seedTextList.add("血圧,高くなる");
		seedTextList.add("左足の動き,改善する");
		seedTextList.add("塞栓,ある");
		seedTextList.add("生存期間,延長する");
		seedTextList.add("出血,ある");
		seedTextList.add("鼻血,酷い");
		seedTextList.add("高血圧,あります");
		seedTextList.add("転移性の乳癌,消滅した");
		seedTextList.add("高血圧,あるのです");
		seedTextList.add("肌荒れ,酷くなる");
		seedTextList.add("便秘,酷くなる");
		seedTextList.add("便秘,ある");
		seedTextList.add("調子,悪くなる");
		seedTextList.add("効果,なかったようだ");
		seedTextList.add("調子,よくなりました");
		seedTextList.add("蕁麻疹,ありました");
		seedTextList.add("気持ち,落ち着かせる");
		
		
		return Transformation.stringToTripleSet(seedTextList);
		
	}
	
	

}
