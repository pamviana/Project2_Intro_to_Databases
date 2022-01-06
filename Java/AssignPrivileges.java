import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class AssignPrivileges extends JFrame implements ActionListener{

	private JPanel contentPane;
	public static JComboBox<String> comboBoxUser;
	JButton btnSub;
	public static JComboBox<String> comboBox;
	JCheckBox chckbxSel;	
	JCheckBox chckbxIn;	
	JCheckBox chckbxUp;	
	JCheckBox chckbxDel;
	String objectSelected;
	String userSelected;
	JLabel feedbackLabel;
	
	
	public AssignPrivileges() {		
		DefaultComboBoxModel employeesList = new DefaultComboBoxModel(Store.employees.toArray());
		DefaultComboBoxModel objectsList = new DefaultComboBoxModel(Store.objects.toArray());		

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBoxUser = new JComboBox<>(employeesList);
		comboBoxUser.setBounds(74, 57, 122, 22);	
		contentPane.add(comboBoxUser);
		comboBoxUser.addActionListener(this);
		
		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(18, 61, 46, 14);
		contentPane.add(lblUser);
		
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(281, 57, 122, 22);	
		comboBox.setModel(objectsList);
		contentPane.add(comboBox);
		comboBox.addActionListener(this);
		
		JLabel lblObj = new JLabel("Object:");
		lblObj.setBounds(225, 61, 46, 14);
		contentPane.add(lblObj);
		
		chckbxSel = new JCheckBox("Select");
		chckbxSel.setBounds(38, 162, 97, 23);
		contentPane.add(chckbxSel);
		
		chckbxIn = new JCheckBox("Insert");
		chckbxIn.setBounds(38, 199, 97, 23);
		contentPane.add(chckbxIn);
		
		chckbxUp = new JCheckBox("Update");
		chckbxUp.setBounds(137, 162, 97, 23);
		contentPane.add(chckbxUp);
		
		chckbxDel = new JCheckBox("Delete");		
		chckbxDel.setBounds(137, 199, 97, 23);
		contentPane.add(chckbxDel);
		
		btnSub = new JButton("Assign");
		btnSub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> privilegesSelected = new ArrayList<>();
				objectSelected = getObjectId(comboBox.getSelectedItem().toString());
			 	userSelected = getSubjectId(comboBoxUser.getSelectedItem().toString());		 			 			 	
			 	
			 	if(chckbxSel.isSelected()) {
			 		privilegesSelected.add("S");
			 	} if(chckbxIn.isSelected()) {
			 		privilegesSelected.add("I");
			 	} if(chckbxUp.isSelected()) {
			 		privilegesSelected.add("U");
			 	} if(chckbxDel.isSelected()) {
			 		privilegesSelected.add("D");
			 	}	 	
			 			 	
			 	for(int i = 0; i< privilegesSelected.size(); i++) {
			 		addPrivilege(userSelected, objectSelected, privilegesSelected.get(i));	 		
			 	}
			 	feedbackLabel.setText("Privilege(s) assigned.");	
	 			feedbackLabel.setVisible(true);
			}
		});
		
		
		btnSub.setBounds(298, 199, 89, 23);
		contentPane.add(btnSub);

		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnBack.setBounds(10, 11, 89, 23);
		contentPane.add(btnBack);
		
		JButton btnNewButton = new JButton("Delete");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> privilegesSelected = new ArrayList<>();
				objectSelected = getObjectId(comboBox.getSelectedItem().toString());
			 	userSelected = getSubjectId(comboBoxUser.getSelectedItem().toString());		 			 			 	
			 	
			 	if(chckbxSel.isSelected()) {
			 		privilegesSelected.add("S");
			 	} if(chckbxIn.isSelected()) {
			 		privilegesSelected.add("I");
			 	} if(chckbxUp.isSelected()) {
			 		privilegesSelected.add("U");
			 	} if(chckbxDel.isSelected()) {
			 		privilegesSelected.add("D");
			 	}	 	
			 			 	
			 	for(int i = 0; i< privilegesSelected.size(); i++) {
			 		if(checkIfPrivilegeExists(userSelected, objectSelected, privilegesSelected.get(i))) {
			 			deletePrivilege(userSelected, objectSelected, privilegesSelected.get(i));
			 			feedbackLabel.setText("Privilege(s) deleted");	
			 			feedbackLabel.setVisible(true);
			 		} else {
			 			feedbackLabel.setText("Couldn't delete, because privileges weren't assigned.");	
			 			feedbackLabel.setVisible(true);
			 		}
			 	}
			 	
			}			
		});
		btnNewButton.setBounds(298, 162, 89, 23);
		contentPane.add(btnNewButton);
		
		
		feedbackLabel = new JLabel();
		feedbackLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		feedbackLabel.setForeground(Color.RED);
		feedbackLabel.setBounds(60, 236, 364, 14);	
		feedbackLabel.setVisible(false);
		contentPane.add(feedbackLabel);
		
		
	}
	
	public static String getObjectId(String s) {		
		String t = "";
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ Store.project, Store.user, Store.password);
			Statement stmt = conn.createStatement ();
			ResultSet test = stmt.executeQuery("select object_ID from object where Name = '" + s + "'");
			
			while(test.next()) {				
				t= test.getString("object_ID");
			}
			
			
		}
		catch (Exception exc ) {
			exc.printStackTrace();			
		}		
		return t;
		
	}
	
	public static String getSubjectId(String s) {
		String t = "";
		String [] name = s.split(" ");
		String lastName = name[1];
		String firstName = name[0];
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ Store.project, Store.user, Store.password);
			Statement stmt = conn.createStatement ();
			ResultSet test = stmt.executeQuery("SELECT SUBJECT_ID, USER_ID, EMPLOYEE_ID, Last_Name, First_Name\r\n"
					+ "FROM subject\r\n"
					+ "JOIN user \r\n"
					+ "ON User_ID = User_User_ID\r\n"
					+ "JOIN employee\r\n"
					+ "ON Employee_ID = Employee_Employee_ID WHERE "
					+ "Last_Name = '" + lastName + "' and First_Name = '" + firstName + "'");
					
			
			while(test.next()) {				
				t= test.getString("subject_ID");				
			}
						
		}
		catch (Exception exc ) {
			exc.printStackTrace();			
		}		
		return t;
	}
	
	
	public static boolean checkIfPrivilegeExists(String user, String object, String priv) {
		Boolean check = false;
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ Store.project, Store.user, Store.password);
			Statement stmt = conn.createStatement ();
			ResultSet result =stmt.executeQuery("SELECT * FROM ref_privilege WHERE SUBJECT_Subject_ID =" + user + " AND "
					+ "OBJECT_OBJECT_ID ="+ object + " AND NAME = '" + priv + "'");
			
						
			if(result.next()) {
				check = true;
			}
			return check;
														
		}
		catch (Exception exc ) {
			exc.printStackTrace();	
			return check;
		}		
			
	}
	
	public static void deletePrivilege(String user, String object, String priv) {	
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ Store.project, Store.user, Store.password);
			Statement stmt = conn.createStatement ();
			stmt.executeUpdate("DELETE FROM ref_privilege WHERE SUBJECT_Subject_ID =" + user + " AND "
					+ "OBJECT_OBJECT_ID ="+ object + " AND NAME = '" + priv + "'");
														
		}
		catch (Exception exc ) {
			exc.printStackTrace();			
		}	
	}
	
	public static void addPrivilege(String user, String object, String priv) {		
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ Store.project, Store.user, Store.password);
			Statement stmt = conn.createStatement ();
			stmt.executeUpdate("INSERT INTO ref_privilege (SUBJECT_Subject_ID, OBJECT_Object_ID, PRIVILEGE_TYPE_Privilege_Type_ID, Name) "
					+ "VALUES (" + user + "," + object + ",0,'" + priv + "')");
														
		}
		catch (Exception exc ) {
			exc.printStackTrace();			
		}		
		
	}	
	
	public void actionPerformed(ActionEvent e) {		
		 		 			 	
	}	
}

