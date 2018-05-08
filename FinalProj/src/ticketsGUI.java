import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ticketsGUI implements ActionListener {
	
	//class objects
	Dao dao = new Dao(); //CRUD
	String checkA = null;
	private JFrame mainF;
	
	JScrollPane jsp = null;
	//main menu items
	private JMenu menuF = new JMenu("File");
	private JMenu menuA = new JMenu("Admin");
	private JMenu menuT = new JMenu("Tickets");
	//private JMenu menuH = new JMenu("History");
	
	JMenuItem menuIE;
	JMenuItem menuIU;
	JMenuItem menuID;
	JMenuItem menuIOT;
	JMenuItem menuIVT;
	JMenuItem menuIHT;
	
	//constructor
	public ticketsGUI(boolean Ais) {
		JOptionPane.showMessageDialog(null, "WELCOME!! ");
		if(Ais) {
			
		}
		dao.createTables();
		dao.createAllUsers();
		cMenu(Ais);
		prepareGUI(Ais);
	}
	//method
	private void cMenu(boolean Ais) {
		//initialize sub items
		menuIE = new JMenuItem("Exit");
		menuIE.addActionListener(this);
		menuF.add(menuIE);
		menuIU = new JMenuItem("Update Ticket");
		menuIU.addActionListener(this);
		menuA.add(menuIU);
		if (Ais) {
			menuID = new JMenuItem("Delete Ticket");
			menuID.addActionListener(this);
			menuA.add(menuID);
		}
		menuIOT = new JMenuItem("Open Ticket");
		menuIOT.addActionListener(this);
		menuT.add(menuIOT);
		menuIVT = new JMenuItem("View Ticket");
		menuIVT.addActionListener(this);
		menuT.add(menuIVT);
		menuIHT = new JMenuItem("View Ticket History");
		menuIHT.addActionListener(this);
		menuT.add(menuIHT);
	}
	//method
	private void prepareGUI(boolean Ais) {
		mainF = new JFrame("Tickets");
		//menu bar
		JMenuBar mb = new JMenuBar();
		mb.add(menuF);
		if(Ais) {
			mb.add(menuA);
		}
		mb.add(menuT);
		mainF.setJMenuBar(mb);
		mainF.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent winE) {
				System.exit(0);
			}
		});
		mainF.setSize(360, 360);
		mainF.getContentPane().setBackground(Color.green);
		mainF.setLocationRelativeTo(null);
		mainF.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuIE) {
			System.exit(0);
		}else if (e.getSource() == menuIOT) {
			try {
				String tNAme = JOptionPane.showInputDialog(null, "Please Enter Your Name");
				String tDesc = JOptionPane.showInputDialog(null, "Enter Ticket Description");
				Connection dc = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false" + "&user=fp411&password=411");
				Statement stmt = dc.createStatement();
				int res = dao.createTicket(tNAme, tDesc);
				ResultSet rs = null;
				rs = stmt.executeQuery("SELECT * FROM T_Choe_tab_tickets");
				int id = 0;
				if(rs.next()) {
					id = rs.getInt(1);
				}
				if (res != 0) {
					System.out.println("Ticket ID: " + id + " created successfully.");
					JOptionPane.showMessageDialog(null, "Ticket ID: " + id + " created");
				}else {
					System.out.println("Ticket can't be created!");
				}
			}catch(SQLException eB) {
				eB.printStackTrace();
			}
		}else if (e.getSource() == menuIVT) {
			try {
				Connection dc = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false" + "&user=fp411&password=411");
				Statement stmt = dc.createStatement();
				ResultSet res = stmt.executeQuery("SELECT * FROM T_Choe_tab_tickets");
				JTable jt = new JTable(ticketsJTable.buildTableModel(res));
				jt.setBounds(35, 45, 190, 290);
				jsp = new JScrollPane(jt);
				mainF.add(jsp);
				mainF.setVisible(true);
				stmt.close();
				dc.close();
			}catch(SQLException eA) {
				eA.printStackTrace();
			}
		}else if (e.getSource() == menuID) {
			System.out.println("Delete?");
			String tID = JOptionPane.showInputDialog(null, "Enter Ticket ID to Delete: ");
			int tid = Integer.parseInt(tID);
			dao.deleteTicketById(tid);
		}else if (e.getSource() == menuIU) {
			System.out.println("Update?");
			String tID = JOptionPane.showInputDialog(null, "Enter Ticket ID to be Updated: ");
			int tid = Integer.parseInt(tID);
			String t_issuer = JOptionPane.showInputDialog(null, "Enter New Ticket-Issuer Name: ");
			String t_desc = JOptionPane.showInputDialog(null, "Enter New Ticket Description: ");
			dao.updateTicketById(tid, t_issuer, t_desc);
		}else if (e.getSource() == menuIHT) {
			Connection dc;
			try {
				dc = DriverManager.getConnection("jdbc:mysql://www.papademas.net/tickets?autoReconnect=true&useSSL=false" + "&user=fp411&password=411");
				Statement stmt = dc.createStatement();
				ResultSet res = stmt.executeQuery("SELECT * FROM T_Choe_tab_history");
				JTable jt = new JTable(ticketsJTable.buildTableModel(res));
				jt.setBounds(35, 45, 190, 290);
				jsp = new JScrollPane(jt);
				mainF.add(jsp);
				mainF.setVisible(true);
				stmt.close();
				dc.close();
			}catch(SQLException eA) {
				eA.printStackTrace();
			}
		}
	}

}
