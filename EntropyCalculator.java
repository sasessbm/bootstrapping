package bootstrapping;

import java.util.ArrayList;

public class EntropyCalculator {

	public static void main(String[] args) {
		//double ans = caluculateCooccurrenceProbability(2,3);
		//double ans = caluculateInformationContent(0.25);
		
		double ans = 0;
		
		ArrayList<Integer> candidateCooccurrenceList = new ArrayList<Integer>();
		candidateCooccurrenceList.add(1);
		//candidateCooccurrenceList.add(9);
		//candidateCooccurrenceList.add(1);
		//candidateCooccurrenceList.add(2);
		//candidateCooccurrenceList.add(2);
		//candidateCooccurrenceList.add(2);
		
		ans = caluculateEntropy(candidateCooccurrenceList, 1);
		
		
		System.out.println(ans);

	}

	public static double caluculateEntropy(ArrayList<Integer> candidateCooccurrenceList, int candidateTotalNum){
		
		double cooccurrenceProbability = 0;
		double informationContent = 0;
		double entropy = 0;
		
		for(int candidateCooccurrenceNum : candidateCooccurrenceList){
			if(candidateCooccurrenceNum == 0){ continue; }
			cooccurrenceProbability = caluculateCooccurrenceProbability(candidateCooccurrenceNum, candidateTotalNum);
			informationContent = caluculateInformationContent(cooccurrenceProbability);
			entropy += informationContent;
			//System.out.println(entropy);
		}
		
		return -entropy;
	}

	public static double caluculateCooccurrenceProbability(int candidateCooccurrenceNum, int candidateTotalNum){

		double ans = 0;
		ans = (double)candidateCooccurrenceNum / (double)candidateTotalNum;
		return ans;
	}
	
	public static double caluculateInformationContent(double cooccurrenceProbability){
		
		double informationContent = 0;
		
		informationContent = cooccurrenceProbability * (Math.log(cooccurrenceProbability) / Math.log(2.0));
		//informationContent = Math.log(cooccurrenceProbability) / Math.log(2.0);
		
		return informationContent;
	}
	
}
