package ServerAndClient;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import DatabaseManager.SimpleDatabase;

public class ServerGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField email;
	private JTable table;
	private boolean showEmergency= false;
	public static String eMail ="kemaldemirel1132@gmail.com";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerGui frame = new ServerGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static String getEMail(){
		return eMail;
	}

	/**
	 * Create the frame.
	 */
	public ServerGui() {
		setTitle("Temperature records");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 802, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JCheckBox emergencies = new JCheckBox("only show emergencies");
		
		panel.add(emergencies);
		
		JLabel lblEmailToWarn = new JLabel("E-mail to warn:");
		panel.add(lblEmailToWarn);
		
		email = new JTextField();
		panel.add(email);
		email.setColumns(20);
		
		JButton btnSet = new JButton("Set");
		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eMail=email.getText();
				System.out.println(eMail);
			}
		});
		panel.add(btnSet);
		
		JButton btnRefresh = new JButton("Refresh");
		
		panel.add(btnRefresh);
		
		table = new JTable();
		contentPane.add(table, BorderLayout.CENTER);
		
		SimpleDatabase database = new SimpleDatabase();
		
		RecordTabelModel model = new RecordTabelModel(database.getResultsFromDatabase(showEmergency));
		table.setModel(model);
		
		
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Refreshed");
				RecordTabelModel model = new RecordTabelModel(database.getResultsFromDatabase(showEmergency));
				table.setModel(model);
			}
		});
		
		emergencies.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				showEmergency = !showEmergency;
				System.out.println("Refreshed");
				RecordTabelModel model = new RecordTabelModel(database.getResultsFromDatabase(showEmergency));
				table.setModel(model);
			}
		});
	}
}
