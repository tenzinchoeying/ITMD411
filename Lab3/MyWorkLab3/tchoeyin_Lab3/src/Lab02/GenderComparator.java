package Lab02;
import java.util.Comparator;

public class GenderComparator implements Comparator<BankRecords>{
	
	@Override
	public int compare(BankRecords o1, BankRecords o2) {
	// use compareTo to compare strings
	//primary sort
	int result = o1.getSex().compareTo(o2.getSex());
	if(result != 0) return result;
	//secondary sort
	 return o1.getRegion().compareTo(o2.getRegion());
	}
}
