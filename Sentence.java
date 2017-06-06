package bootstrapping;

import java.util.TreeMap;

public class Sentence {
	
	private String text;
	private int id;
	private TreeMap<Integer, String> medicineNameMap;
	
	public Sentence(String text, int id, TreeMap<Integer, String> medicineNameMap){
		this.text = text;
		this.id = id;
		this.medicineNameMap = medicineNameMap;
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
	
	public TreeMap<Integer, String> getMedecineNameMap(){
		return medicineNameMap;
	}
	
	public void setMedicineNameMap(TreeMap<Integer, String> medicineNameMap){
		this.medicineNameMap = medicineNameMap;
	}

}
