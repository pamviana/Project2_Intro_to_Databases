
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class GenerateReport extends JFrame {

	private JPanel contentPane;
	public static JPanel PPUpanel;
	String userSelected;
	String objectSelected;


	/**
	 * Create the frame.
	 */
	public GenerateReport() {			
		DefaultComboBoxModel employeesList = new DefaultComboBoxModel(Store.employees.toArray());
		DefaultComboBoxModel objectsList = new DefaultComboBoxModel(Store.objects.toArray());
					
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
				
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 434, 261);
		contentPane.add(tabbedPane);
		
		// --------------- Screen to Back ------------------------------
		
		JPanel panelBack = new JPanel();
		tabbedPane.addTab("Return to Menu", null, panelBack, null);
		panelBack.setLayout(null);
		
				
		JButton btnMenu = new JButton("Menu");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnMenu.setBounds(148, 70, 132, 92);
		panelBack.add(btnMenu);
		
		
		// --------------- Screen "Check Privileges" ------------------------------
		
		
		JPanel CPpanel = new JPanel();
		tabbedPane.addTab("Check Privileges", null, CPpanel, null);
		CPpanel.setLayout(null);
		
		JToggleButton tglbtnUserObj = new JToggleButton("Total of privileges per object");
		tglbtnUserObj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String columns[] = {"Object", "Privilege", "Total"};	
					String data[][] = new String[50][3];
					data = totalObjectReport();
					DefaultTableModel model = new DefaultTableModel(data, columns);
					OutputPanel TotalObjectOutput = new OutputPanel(model);
					TotalObjectOutput.setVisible(true);
				} catch (Exception h) {
					h.printStackTrace();
				}
			}
		});			
		tglbtnUserObj.setBounds(92, 48, 246, 40);
		CPpanel.add(tglbtnUserObj);
		
		JToggleButton tglbtnTotalOfPrivileges = new JToggleButton("Total of privileges per user");
		tglbtnTotalOfPrivileges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {	
					String columns[] = {"First Name", "Last Name", "Privilege", "Total"};	
					String data[][] = new String[50][4];
					data = totalUserReport();
					DefaultTableModel model = new DefaultTableModel(data, columns);
					OutputPanel TotalUserOutput = new OutputPanel(model);
					TotalUserOutput.setVisible(true);
				} catch (Exception h) {
					h.printStackTrace();
				}
			}
		});			
		tglbtnTotalOfPrivileges.setBounds(92, 130, 246, 40);
		CPpanel.add(tglbtnTotalOfPrivileges);
		
		// --------------- Screen "Privileges per User" ------------------------------
		
		PPUpanel = new JPanel();
		tabbedPane.addTab("Privileges Per User", null, PPUpanel, null);
		PPUpanel.setLayout(null);
		
		JComboBox<String> comboBoxUser = new JComboBox<>();
		comboBoxUser.setBounds(151, 10, 118, 22);
		comboBoxUser.setModel(employeesList);
		PPUpanel.add(comboBoxUser);
		
		JLabel lblUser = new JLabel("Choose User:");
		lblUser.setBounds(21, 10, 78, 14);
		PPUpanel.add(lblUser);	
								
		JButton btnUserRun = new JButton("Submit");
		btnUserRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					userSelected = comboBoxUser.getSelectedItem().toString();	
					String columns[] = {"First Name", "Last Name", "Object", "Privilege"};	
					String data[][] = new String[50][4];
					data = privilegeReport(userSelected);
					DefaultTableModel model = new DefaultTableModel(data, columns);					
					OutputPanel UserOutput = new OutputPanel(model);
					UserOutput.setVisible(true);
					
				} catch (Exception h) {
					h.printStackTrace();
				}
			}
		});
		btnUserRun.setBounds(312, 10, 89, 23);
		PPUpanel.add(btnUserRun);
		
				// --------------- Screen "Privileges per Object" ------------------------------
		
		JPanel PPOpanel = new JPanel();
		tabbedPane.addTab("Privileges Per Object", null, PPOpanel, null);
		PPOpanel.setLayout(null);
		
		JComboBox<String> comboBoxObj = new JComboBox<>();
		comboBoxObj.setBounds(156, 57, 118, 22);
		comboBoxObj.setModel(objectsList);
		PPOpanel.add(comboBoxObj);
		
		JLabel lblObj = new JLabel("Choose Object:");
		lblObj.setBounds(57, 61, 89, 14);
		PPOpanel.add(lblObj);
		
		JButton btnObj = new JButton("Submit");
		btnObj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					objectSelected = comboBoxObj.getSelectedItem().toString();	
					String columns[] = {"Object", "Privilege"};	
					String data[][] = new String[4][2];
					data = privilegeObjectReport(objectSelected);
					DefaultTableModel model = new DefaultTableModel(data, columns);			
					OutputPanel ObjOutput = new OutputPanel(model);
					ObjOutput.setVisible(true);					
				} catch (Exception h) {
					h.printStackTrace();
				}
				
			}
		});
		btnObj.setBounds(297, 168, 89, 23);
		PPOpanel.add(btnObj);
	}
	
	
	
	
	// ----------------- Method that returns a table with the privileges per object ------------	
	
	public static String[][] privilegeObjectReport(String s) {		
		String data[][] = new String[4][2];
		
		try {			
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ Store.project, Store.user, Store.password);
			Statement stmt = conn.createStatement ();
			ResultSet test = stmt.executeQuery("SELECT DISTINCT object.name, ref_privilege.name \r\n"
					+ "FROM object \r\n"
					+ "JOIN ref_privilege\r\n"
					+ "ON Object_ID = Object_Object_ID\r\n"
					+ "WHERE object.name = '" + s + "'");
			
			int i = 0;
			
			while(test.next()) {				
				String object = test.getString("object.name");	
				String priv = test.getString("ref_privilege.name");	
				
				data[i][0] = object;
				data[i][1] = priv;
				
				i++;
			}
						
		}
		catch (Exception exc ) {
			exc.printStackTrace();			
		}
		
		return data;
	}
	
	
	// ----------------- Method that returns a table with the privileges per user ------------	
	
	public static String[][] privilegeReport(String s) {
		String [] name = s.split(" ");
		String lastName = name[1];
		String firstName = name[0];
		String data[][] = new String[50][4];
		
		try {			
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ Store.project, Store.user, Store.password);
			Statement stmt = conn.createStatement ();
			ResultSet test = stmt.executeQuery("SELECT First_Name, Last_Name, object.Name, ref_privilege.Name\r\n"
					+ "FROM ref_privilege\r\n"
					+ "JOIN subject\r\n"
					+ "ON subject_ID = subject_subject_ID\r\n"
					+ "JOIN user \r\n"
					+ "ON User_ID = User_User_ID\r\n"
					+ "JOIN employee\r\n"
					+ "ON Employee_ID = Employee_Employee_ID\r\n"
					+ "JOIN Object\r\n"
					+ "ON OBJECT_ID = OBJECT_Object_ID WHERE "
					+ "Last_Name = '" + lastName + "' and First_Name = '" + firstName + "'");
			
			int i = 0;
			
			while(test.next()) {				
				String fName = test.getString("First_Name");	
				String lName = test.getString("Last_Name");	
				String object = test.getString("object.Name");	
				String priv = test.getString("ref_privilege.Name");	
				
				data[i][0] = fName;
				data[i][1] = lName;
				data[i][2] = object;
				data[i][3] = priv;
				i++;
			}
						
		}
		catch (Exception exc ) {
			exc.printStackTrace();			
		}
		
		return data;
	}
	
	// ----------------- Method that returns the total of privileges per Object ------------------	
	
		public static String[][] totalObjectReport() {		
			String data[][] = new String[50][3];
			
			try {			
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ Store.project, Store.user, Store.password);
				Statement stmt = conn.createStatement ();
				ResultSet test = stmt.executeQuery("SELECT object_name, Privilege, Count(privilege) AS Total\r\n"
						+ "FROM privilege_user_object\r\n"
						+ "GROUP BY object_name, Privilege");

				// This would be the code without using the view privilege_user_object
//				"SELECT object.name, ref_privilege.name, Count(ref_privilege.Name) AS Total\r\n"
//				+ "FROM ref_privilege\r\n"
//				+ "JOIN object\r\n"
//				+ "ON OBJECT_ID = OBJECT_Object_ID\r\n"
//				+ "GROUP BY object.name, ref_privilege.name"
				
				int i = 0;
				
				while(test.next()) {				
					String object = test.getString("object_name");	
					String priv = test.getString("privilege");
					String total = test.getString("Total");
					
					data[i][0] = object;
					data[i][1] = priv;
					data[i][2] = total;
					
					i++;
				}
							
			}
			catch (Exception exc ) {
				exc.printStackTrace();			
			}
			
			return data;
		}
		
		
		// ----------------- Method that returns the total of privileges per User ------------------	
		
			public static String[][] totalUserReport() {		
				String data[][] = new String[50][4];
				
				try {			
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ Store.project, Store.user, Store.password);
					Statement stmt = conn.createStatement ();
					ResultSet test = stmt.executeQuery("SELECT First_Name, Last_Name, Privilege, Count(Privilege) AS Total\r\n"
							+ "FROM privilege_user_object\r\n"
							+ "GROUP BY First_Name, Last_Name, Privilege");
					
					
					// ----- This would be the code without using the view privilege_user_object
//					"SELECT First_Name, Last_Name, ref_privilege.Name, Count(ref_privilege.Name) AS Total\r\n"
//					+ "FROM ref_privilege\r\n"
//					+ "JOIN subject\r\n"
//					+ "ON subject_ID = subject_subject_ID\r\n"
//					+ "JOIN user \r\n"
//					+ "ON User_ID = User_User_ID\r\n"
//					+ "JOIN employee\r\n"
//					+ "ON Employee_ID = Employee_Employee_ID\r\n"
//					+ "JOIN Object\r\n"
//					+ "ON OBJECT_ID = OBJECT_Object_ID\r\n"
//					+ "GROUP BY First_Name, Last_Name, ref_privilege.Name"
					
					int i = 0;
					
					while(test.next()) {				
						String firstName = test.getString("First_Name");	
						String lastName = test.getString("Last_Name");
						String priv = test.getString("Privilege");
						String total = test.getString("Total");
						
						data[i][0] = firstName;
						data[i][1] = lastName;
						data[i][2] = priv;
						data[i][3] = total;
						
						i++;
					}
								
				}
				catch (Exception exc ) {
					exc.printStackTrace();			
				}
				
				return data;
			}
}
