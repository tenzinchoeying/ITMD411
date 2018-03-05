package Lab02;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class Records extends BankRecords {
//create formatted object to write output directly 
//to the console and to a file
	static FileWriter fw = null;
	public Records() {
		try {
			fw = new FileWriter("bankrecords.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Records br = new Records();
		br.readData();
		//call functions to perform analytics
		AverageComp(); //analyze average income
		femsComp(); //analyze females w. mortgage/savings account
		malesComp(); //analyze male count with 1 child
		
		// *** close out file object ***//
		
		try {
			fw.close();
		}catch (IOException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
	private static void AverageComp() {
		Arrays.sort(robjs,new GenderComparator());
		
		double fsum = 0, msum = 0, fCt = 0, mCt = 0;
		for (int i = 0; i < robjs.length; i++)
			if (robjs[i].getSex().equals("FEMALE")) {
				fsum += robjs[i].getIncome();
				++fCt;
			}else {
				if(robjs[i].getSex().equals("MALE")) {
					msum += robjs[i].getIncome();
					++mCt;
				}
			}
		//print resulting averages to console and file
		double femAvg = fsum/(fCt);
		double maleAvg = msum/(mCt);
		
		System.out.println("Data Analytics Result: ");
		System.out.println("Average income for female: " + femAvg);
		System.out.println("Average income for male: " + maleAvg);
		
		try {
			fw.write("Data Analytics Result: ");
			fw.write("Average income for female: " + femAvg);
			fw.write("Average income for male: " + maleAvg);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void femsComp() {
		Arrays.sort(robjs,new GenderComparator());
		//store female count
		int fCt = 0;
		for (int i = 0; i < robjs.length; i++) 
			if (robjs[i].getSex().equals("FEMALE") && robjs[i].getMortgage().equals("YES") && robjs[i].getSave_act().equals("YES")) {
				fCt += 1;
			}
		
		System.out.println("Number of Females with Mortgage and Savings Account: " + fCt);
		
		try {
			fw.write("Number of Females with Mortgage and Savings Account: " + fCt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void malesComp() {
		Arrays.sort(robjs, new ChildrenComparator());
		
		//store male count
		int mCt_rural = 0, mCt_suburban = 0, mCt_town = 0, mCt_inner = 0;
		
		for (int i=0; i < robjs.length; i++)
			if (robjs[i].getSex().equals("MALE") && robjs[i].getRegion().equals("RURAL")
					&& robjs[i].getChildren()==1) {
				mCt_rural += 1;
			} else if (robjs[i].getSex().equals("MALE") && robjs[i].getRegion().equals("SUBURBAN")
					&& robjs[i].getChildren()==1){
				mCt_suburban += 1;
			} else if (robjs[i].getSex().equals("MALE") && robjs[i].getRegion().equals("TOWN")
					&& robjs[i].getChildren()==1){
				mCt_town += 1;
			} else if (robjs[i].getSex().equals("MALE") && robjs[i].getRegion().equals("INNER_CITY")
					&& robjs[i].getChildren()==1){
				mCt_inner += 1;
			}
		
		//printing results
		
		System.out.println("Rural region Male with 1 Child: " + mCt_rural);
		System.out.println("Suburban region Male with 1 Child: " + mCt_suburban);
		System.out.println("Town region Male with 1 Child: " + mCt_town);
		System.out.println("Innercity region Male with 1 Child: " + mCt_inner);
		
		//mandatory timestamp for every project
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		System.out.println("Cur dt=" + timeStamp + "\nProgrammed by Tenzin Choeying\n");
		
		try {
			fw.write("Rural region Male with 1 Child: " + mCt_rural);
			fw.write("Suburban region Male with 1 Child: " + mCt_suburban);
			fw.write("Town region Male with 1 Child: " + mCt_town);
			fw.write("Innercity region Male with 1 Child: " + mCt_inner);
			//mandatory timestamp for every project
			fw.write("Cur dt=" + timeStamp + "\nProgrammed by Tenzin Choeying\n");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
