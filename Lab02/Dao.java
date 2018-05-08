package Lab02;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.sql.DriverManager;

public class Dao {

	//Code database URL
	//static final String DB_URL = "jdbc:mysql://www.papademas.net/411labs?autoReconnect=true&useSSL=false";
	DBConnect conn = null;
	Statement stmt = null;
	static String name = "T_Choe_tab";
	
	//connect to database
	public Connection connect() {
		if (this.conn == null) {
			this.conn = new DBConnect();
		}
		try {
			this.stmt = this.conn.connection.createStatement();
		}catch (SQLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.conn.connection;
	}
	//Database credentials
	//constructor calling connect
	public Dao() {
		this.connect();
	}
	//close method
	public void close() {
		try {
			this.conn.connection.close();
		}catch (SQLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Method createTable
	public void createTables() {
		String sql = "CREATE TABLE if not exists T_Choe_tab (\n" + "pid INTEGER not NULL,\n" + " id VARCHAR(10), \n"
	+ " income numeric(8,2), \n" + " pep VARCHAR(3), \n" + " PRIMARY KEY ( pid ))\n";
		
		System.out.println("Create Table...");
		System.out.println(sql);
		try {
			System.out.println("Creating table in given database....");
			this.stmt.execute(sql);
		} catch (SQLException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//variables for table
		final String createTicketsTable = "CREATE TABLE IF NOT EXISTS " + name + "_tickets("
				+ "ticket_id INT AUTO_INCREMENT PRIMARY KEY,"
				+ " ticket_issuer VARCHAR(30), "
				+ "ticket_description VARCHAR(200),"
				+ "ticket_open DATETIME,"
				+ "ticket_closed DATETIME)";
		final String createUsersTable = "CREATE TABLE IF NOT EXISTIS " + name + "_users("
				+ "uid INT AUTO_INCREMENT PRIMARY KEY, "
				+ "uname VARCHAR(30), "
				+ "upass VARCHAR(30))";
		final String createTicketsHistoryTable = "CREATE TABLE IF NOT EXISTS " + name + "_history("
				+ "ticket_id INT AUTO_INCREMENT PRIMARY KEY," 
				+ "ticket_issuer VARCHAR(30), "
				+ "ticket_description VARCHAR(200),"
				+ "ticket_status VARCHAR(64), "
				+ "ticket_open DATETIME,"
				+ "ticket_closed DATETIME)";
		try {
			//start table
			Statement statement = this.conn.connection.createStatement();
			
			System.out.println(createTicketsTable);
			statement.execute(createTicketsTable);
			
			System.out.println(createUsersTable);
			statement.execute(createUsersTable);
			
			System.out.println(createTicketsHistoryTable);
			statement.execute(createTicketsHistoryTable);
			
			System.out.println("Created tables in given database....");
			
			//end table
			statement.close();
		} catch (Exception e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		addUsers();
	}
	
	public void addUsers() {
		//add users from csv file to table
		String sql; 
		Connection connect = null;
		Statement statement = null;
		BufferedReader br;
		List<List<String>> array = new ArrayList<>();
		
		//read data
		try {
			br = new BufferedReader(new FileReader(new File("./userslist.csv")));
			
			String line;
			while ((line = br.readLine()) != null) {
				array.add(Arrays.asList(line.split(" , ")));
			} 
		}catch (Exception e) {
				System.out.println("There was a problem in loading the file!");
			}
			
			try {
				
				statement = new Dao().connect().createStatement();
				
				for (List<String> rowData : array) {
					sql = "insert into " + name + "_users(uname,upass) "
							+ "values('" + rowData.get(0) + "','" 
							+ rowData.get(1) + "');";
					statement.executeUpdate(sql);
				}
				System.out.println("Insert completed in given database....");
				
				statement.close();
				conn.connect().close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		public void truncateTable() {
			String sql = "DELETE FROM T_Choe_tab";
			try {
				this.stmt.execute(sql);
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void insertRecords(BankRecords[] robjs) {
			
			try {
				System.out.println("Inserting records in the table....");
				stmt = conn.connect().createStatement();
				
				for (int i = 0; i < robjs.length; ++i) {
					BankRecords br = robjs[i];
					
					String id = br.getId();
					double income = br.getIncome();
					String pep = br.getPep();
					
					String sql = "  ";
					sql += String.format("INSERT INTO T_Choe_tab VALUES (%d, '%s', %f, '%s')", i, id, income, pep);
					System.out.println("exec: " + sql);
					stmt.executeUpdate(sql);
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		
		public ResultSet retrieveRecords() {
			ResultSet rs = null;
			
			String sql = "SELECT id,income,pep from " + name + "_tickets order by pep desc";
			try {
				rs = this.stmt.executeQuery(sql);
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return rs;
		}
		
		public static void PrintResultSet(ResultSet rs) {
			ResultSetMetaData rsmd;
			try {
				rsmd = rs.getMetaData();
				String format = "%17s | ";
				
				String temp = "";
				for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
					temp += String.format(format, rsmd.getColumnLabel(i));
				}
				System.out.println("\n");
				System.out.println(" Loan Analysis Report.");
				System.out.println(temp);
				
				while (rs.next()) {
					String s = "";
					for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
						Object o = rs.getObject(i);
						s += String.format(format, o.toString());
					}
					System.out.println(s);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void createUser(String usern, String passw) {
			Statement statement;
			try {
				statement = conn.connection.createStatement();
				String sql = "INSERT INTO " + name + "_users (uname, upass) "
						+ String.format("VALUES ( '%s', '%s')", usern, passw);
				System.out.println(sql);
				statement.execute(sql);
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void createAllUsers() {
			File file = new File("userslist.csv");
			Scanner scan;
			try {
				scan = new Scanner(file);
				while (scan.hasNext()) {
					String line = scan.nextLine();
					String[] lines = line.split(",");
					String user = lines[0];
					String pass = lines[1];
					this.createUser(user, pass);
				}
			}catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		public int createTicket (String name, String desc) {
			Statement statement;
			try {
				statement = this.conn.connection.createStatement();
				int result = statement
						.executeUpdate("Insert into T_Choe_tab_tickets(ticket_issuer, ticket_description)" 
						+ " values(" + " '"
						+ name + "','" + desc + "')", Statement.RETURN_GENERATED_KEYS);
				return result;
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return -1;
		}
		
		public ArrayList<String> getTicketByID(int id) {
			String sql = "SELECT * FROM T_Choe_tab_tickets WHERE ticket_id = " + id;
			ArrayList<String> list = new ArrayList<String>();
			
			try {
				ResultSet rs = this.conn.connection.createStatement().executeQuery(sql);
				
				if(!rs.next()) {
					list.add("EMPTY");
					list.add("SET");
					return list;
				}
				rs.previous();
				
				String name = rs.getString(1);
				String desc = rs.getString(2);
				list.add(name);
				list.add(desc);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}
		
		public void deleteTicketById(int id) {
			try {
				Statement s = this.conn.connection.createStatement();
				ArrayList<String> res = this.getTicketByID(id);
				
				String sql = "DELETE FROM " + name + "_tickets WHERE ticket_id = " + id;
				s.execute(sql);
				
				logTicket(res.get(0), res.get(1));
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void updateTicketById(int id, String issuer, String desc) {
			try {
				PreparedStatement ps = this.conn.connection.prepareStatement("UPDATE " + name
						+ "_tickets SET ticket_description = ?"
						+ " , ticket_issuer = ?"
						+ " WHERE ticket_id = ?");
				int i = 0;
				ps.setString(++i, desc);
				ps.setString(++i,  issuer);
				ps.setInt(++i, id);
				
				System.out.println(ps.toString());
				ps.executeUpdate();
				
				logTicket(name, desc);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public int logTicket(String name, String desc) {
			try {
				Statement statement = this.conn.connection.createStatement();
				int result = statement.executeUpdate("Insert into T_Choe_tab_histroy(ticket_issuer, ticket_description)"
						+ " values(" + " '" + name + "','" + desc + "')", Statement.RETURN_GENERATED_KEYS);
				
				return result;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return -1;
		}
		
		public static void main(String[] args) {
			Dao d = new Dao();
			d.createTables();
			d.createAllUsers();
			d.updateTicketById(2, "issuer name", "issue description");
			d.getTicketByID(2);
			d.logTicket("add name", "add description");
		}

}
