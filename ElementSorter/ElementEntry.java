import java.util.ArrayList;
/**
 *	ElementEntry - (Description goes here)
 *
 *	Requires the Prompt and FileUtils classes
 *	@author	Pranav Belligundu
 *	@since	Dec 2nd, 2021
 */

public class ElementEntry {
	String name;
	int num;
	String symbol;
	double atomicMass;
	
	public ElementEntry(int num, String name, String symbol, double atomicMass){
		this.name = name;
		this.num = num;
		this.symbol = symbol;
		this.atomicMass = atomicMass;
	}
	public int compareToName(ElementEntry other){
		return name.compareTo(other.name);
	}
	public int compareToNum(ElementEntry other){
		return num - other.num;
	}
	public int compareToSymbol(ElementEntry other){
		return symbol.compareTo(other.symbol);
	}
	public int compareToAM(ElementEntry other){
		return (int)atomicMass - (int)other.atomicMass;
	}
	public String toString(){
		return num+" "+name+"\t"+symbol+"\t"+atomicMass;
	}
}
/*
 * public ElementEntry(String name, int num, String symbol, int atomicMass){
		this.name = name;
		this.num = num;
		this.symbol = symbol;
		this.atomicMass = atomicMass;
	}
	*/
