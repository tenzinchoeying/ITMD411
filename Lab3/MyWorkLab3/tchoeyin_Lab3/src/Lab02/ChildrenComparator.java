package Lab02;
import java.util.Comparator;

public class ChildrenComparator implements Comparator<BankRecords>{
	
	@Override
	public int compare(BankRecords o1, BankRecords o2) {
	// use compareTo to compare strings
	 return (int)o1.getChildren() - o2.getChildren();
	}
}
