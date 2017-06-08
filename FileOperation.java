package bootstrapping;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FileOperation {
	
	public static void writeTextInFile(String keyWord, String filePath, boolean isAdditional) 
							throws UnsupportedEncodingException, FileNotFoundException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter
													(new FileOutputStream(filePath,isAdditional),"utf-8")));
		pw.println(keyWord);
		pw.close();
	}
	
	public static ArrayList<DoubleSet> makeSeedList(String filePath) {
		
		ArrayList<DoubleSet> seedList = new ArrayList<DoubleSet>();
		ArrayList<String> seedTextList = makeTriplicity.GetTextFileList.fileRead(filePath);
		
		for(String seedText : seedTextList){
			String[] elementArray = seedText.split(",");
			seedList.add(new DoubleSet(elementArray[0], elementArray[1]));
		}
		return seedList;
	}
	
}
