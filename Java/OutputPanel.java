
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class OutputPanel extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
			}
		});
	}
	
	public OutputPanel(DefaultTableModel model) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Color black = new Color(72 ,72, 72);
		contentPane.setBackground(black);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			setVisible(false);
			}
		});
		btnBack.setBounds(10, 11, 89, 23);
		contentPane.add(btnBack);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 45, 414, 205);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setShowGrid(true);
		table.setShowVerticalLines(true);
		table.setModel(model);
		
	}
	

}
