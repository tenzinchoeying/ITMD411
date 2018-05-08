
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Login {
	//fields
	private Dao dao;
	private JPanel controlP;
	private JButton loginBtn;
	private JPasswordField pwordText;
	private JTextField uText;
	private JFrame mainF;
	private JLabel headerL;
	private JLabel statusL;
	private JLabel nameL;
	private JLabel pwordL;
	
	public Login() {
		prepareGUI();
		showTextFields();
		this.dao = new Dao();
	}
	//method
	private void prepareGUI() {
		//objects
		mainF = new JFrame("Login");
		headerL = new JLabel("", JLabel.CENTER);
		controlP = new JPanel();
		statusL = new JLabel("", JLabel.CENTER);
		//frame object
		headerL.setText("Account Access");
		statusL.setSize(360, 100);
		//window frame
		mainF.setSize(360, 360);
		mainF.setLayout(new GridLayout(3,1));
		mainF.getContentPane().setBackground(Color.BLUE);
		mainF.setLocationRelativeTo(null);
		//Frame object to mainframe
		mainF.add(headerL);
		mainF.add(statusL);
		mainF.add(controlP);
		
		mainF.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowE) {
				System.exit(0);
			}
		});
	}
	
	//method
	public void showTextFields() {
		//controls
		nameL = new JLabel("User ID: ", JLabel.RIGHT);
		pwordL = new JLabel("Password: ", JLabel.CENTER);
		uText = new JTextField(6);
		uText.setText("admin");
		pwordText = new JPasswordField(8);
		pwordText.setText("admin123");
		loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerfomed(ActionEvent e) {
				String uName = uText.getText();
				String pword = new String(pwordText.getPassword());
				boolean adminF = false;
				if(uName.equals("admin") && pword.equals("admin123")) {
					adminF = true;
					mainF.dispose();
					new ticketsGUI(adminF);
				}else if (uName.equals("tenzin") && pword.equals("tenzin123")) {
					adminF = false;
					mainF.dispose();
					new ticketsGUI(adminF);
				}else if (uName.equals("ngodup") && pword.equals("ngodup123")) {
					adminF = false;
					mainF.dispose();
					new ticketsGUI(adminF);
				}else if (!adminF) {
					Connection connect = Dao.getConnection();
					String queryString = "SELECT uname, upass FROM jpapa_users where uname=? and upass=?";
				    PreparedStatement ps;
				    ResultSet res = null;
				    try {
				    	ps = (PreparedStatement) connect.prepareStatement(queryString);
				    	ps.setString(1,  uName);
				    	ps.setString(2, pword);
				    	res = ps.executeQuery();
				    	if(res.next()) {
				    		JOptionPane.showMessageDialog(null, "Username and Password exists!");
				    		mainF.dispose();
				    		new ticketsGUI(adminF);
				    	}else {
				    		JOptionPane.showMessageDialog(null, "PLease try again!");
				    	}
				    }catch(SQLException eA) {
				    	eA.printStackTrace();
				    }finally {
				    	try {
				    		res.close();
				    	}catch(SQLException eA) {
				    		eA.printStackTrace();
				    	}
				    }
				}
			}

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		controlP.setBackground(Color.BLUE);
		controlP.setLayout(new FlowLayout());
		controlP.add(uText);
		controlP.add(nameL);
		controlP.add(pwordText);
		controlP.add(pwordL);
		controlP.add(loginBtn);
		mainF.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Login();
	}

}
