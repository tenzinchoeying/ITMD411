package Lab02;
import java.sql.SQLException;
import java.sql.ResultSet;

public class LoanProcessing extends BankRecords {
	
	public static void main(String[] args) {
		Dao d = null;
		BankRecords br = new BankRecords();
		br.readData();
		
		try {
			d = new Dao();
			d.getConnection();
			
			System.out.println("Hey!");
			d.truncateTable();
			d.createTables();
			d.insertRecords(robjs);
			ResultSet rs = d.retrieveRecords();
			Dao.PrintResultSet(rs);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		d.close();
	}

}
