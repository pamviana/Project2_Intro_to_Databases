import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class DatabaseMenu {

	private JFrame frame;

	
	public static void main(String[] args) {
		Store.employees = new ArrayList<>();		
		Store.objects = new ArrayList<>();		
		String employee = "";
		String object = "";
		 
				
		try {
			
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ Store.project, Store.user, Store.password);
			Statement stmt = myConn.createStatement ();		
			Statement stmt2 = myConn.createStatement ();	
			ResultSet employeeName = stmt.executeQuery("select * from employee");
			ResultSet objectName = stmt2.executeQuery("select * from object");
			
			
			while(employeeName.next()) {
				employee = employeeName.getString("first_name") + " " + employeeName.getString("last_name");
				Store.employees.add(employee);	
				
			}				
						
			while(objectName.next()) {
				object = objectName.getString("Name");
				Store.objects.add(object);	
				
			}	
				
		}
		catch (Exception exc ) {
			exc.printStackTrace();			
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatabaseMenu DMwindow = new DatabaseMenu();
					DMwindow.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DatabaseMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {		
		String userSelected;
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblMenu = new JLabel("Menu", JLabel.CENTER);
		lblMenu.setBounds(132, 0, 169, 37);
		frame.getContentPane().add(lblMenu);
		
		JButton btnAssignPriv = new JButton("Assign/Delete Privileges");
		btnAssignPriv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				try {
					AssignPrivileges APframe = new AssignPrivileges();
					APframe.setVisible(true);
				} catch (Exception h) {
					h.printStackTrace();
				}
				
			}
		});
		btnAssignPriv.setBounds(118, 60, 187, 60);
		frame.getContentPane().add(btnAssignPriv);
		
		JButton btnGenRep = new JButton("Generate Report");
		btnGenRep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						GenerateReport GRframe = new GenerateReport();
						GRframe.setVisible(true);
					} catch (Exception h) {
						h.printStackTrace();
					}
				}
		});
		
		btnGenRep.setBounds(118, 143, 187, 60);
		frame.getContentPane().add(btnGenRep);
	}

}
