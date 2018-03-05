package Lab02;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class BankRecords extends Client {
	
	//static objects for IO processing
	static BankRecords robjs[] = new BankRecords[600]; //array of BankRecords objects
	static ArrayList<List<String>> array = new ArrayList<>(); //arraylist to hold data
	//instance fields
	private String id;
	private int age;
	private String sex;
	protected String region;
	private double income;
	private String married;
	private int children;
	private String car;
	private String save_act;
	private String current_act;
	private String mortgage;
	private String pep;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BankRecords br = new BankRecords();
		br.readData();
	}

	@Override
	public void readData() {
		// TODO Auto-generated method stub
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File("bank-Detail.csv")));
			String line;
			try {
				while ((line = br.readLine()) !=null) {
					array.add(Arrays.asList(line.split(",")));
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.out.println("There was a problem loading the file");
		}
		processData();
	}

	@Override
	public void processData() {
		// TODO Auto-generated method stub
		int idx = 0;
		//create loop to grab each array index containing a list of values
		//and PASS that data into your record objects' setters
		for (List<String> rowData: array) {
			//initialize array of objects
			robjs[idx] = new BankRecords();
			//call setters 
			robjs[idx].setId(rowData.get(0)); //get first column
			robjs[idx].setAge(Integer.parseInt(rowData.get(1))); //get second column
			robjs[idx].setSex(rowData.get(2)); //get third column
			robjs[idx].setRegion(rowData.get(3));//get fourth column
			robjs[idx].setIncome(Double.parseDouble(rowData.get(4)));//get fifth column
			robjs[idx].setMarried(rowData.get(5));//get sixth column
			robjs[idx].setChildren(Integer.parseInt(rowData.get(6)));//get seventh column
			robjs[idx].setCar(rowData.get(7));//get eight column
			robjs[idx].setSave_act(rowData.get(8));//get ninth column
			robjs[idx].setCurrent_act(rowData.get(9));//get tenth column
			robjs[idx].setMortgage(rowData.get(10));//get eleventh column
			robjs[idx].setPep(rowData.get(11));//get twelfth column
			
			idx++;
		}
		printData();	
	}

	@Override
	public void printData() {
		// TODO Auto-generated method stub
		//printing header to define
		//System.out.printf("%s\t\t%n", "Id", "Age", "Sex", "Region", "Income", "Mortgage");
		System.out.println("ID\t\tAGE\t\tSEX\t\tREGION\t\t\tINCOME\t\tMORTGAGE\t");

		//loop through array of objects
		//printing values to the headers
		for (int i = 0; i<25; ++i)
			System.out.printf("%s\t\t%d\t\t%s\t\t%-10s\t\t%s\t\t%s%n", robjs[i].getId(), robjs[i].getAge(), 
					robjs[i].getSex(), robjs[i].getRegion(), 
					robjs[i].getIncome(), robjs[i].getMortgage());
		//mandatory timestamp for every project
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		System.out.println("Cur dt=" + timeStamp + "\nProgrammed by Tenzin Choeying\n");

	}
	
	//creating getter and setters
//get id to set id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	} 
//get age to set age	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
//get sex to set sex
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
//get region to set region
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
//get income to set income
	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}
//get married to set married
	public String getMarried() {
		return married;
	}

	public void setMarried(String married) {
		this.married = married;
	}
//get children to set children
	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}
//get car to set car
	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}
//get save_act to set save_act
	public String getSave_act() {
		return save_act;
	}

	public void setSave_act(String save_act) {
		this.save_act = save_act;
	}
//get current_act to set current_act
	public String getCurrent_act() {
		return current_act;
	}

	public void setCurrent_act(String current_act) {
		this.current_act = current_act;
	}
//get Mortgage to set Mortgage
	public String getMortgage() {
		return mortgage;
	}

	public void setMortgage(String mortgage) {
		this.mortgage = mortgage;
	}
//get pep to set pep
	public String getPep() {
		return pep;
	}

	public void setPep(String pep) {
		this.pep = pep;
	}

}
