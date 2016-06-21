package lab_7_pack;
// This code gives the layout for the UI and
//   demonstrates two ways of updating the data
//   in a JTable.
// Another option to consider when using JTable is
//   creating your own data model by overriding
//   AbstractTableModel. You might use this option
//   if data for table was coming from say a DB.
//   One example: http://www.java2s.com/Code/Java/Swing-JFC/CreatingsimpleJTableusingAbstractTableModel.htm

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AccountLayout extends JFrame implements ActionListener {

	private JTable table;
	private JButton clearButton;
	private JButton transferButton;
	private JTextField toField;
	private JTextField amountField;
	private JTextField fromField;
	
	//data to stream in
	private DBTable data;
	
	//list of accounts in table
	private ArrayList<Account> accounts=new ArrayList<Account>();
	
	private String[] columnNames = {"Account ID",
            "Account Name",
            "Balance"};
	
	public AccountLayout() {
		
		setTable(); //get connected to database with the account table
		Object[][] account_data = {
				{accounts.get(0).getID(),accounts.get(0).getType(),
					accounts.get(0).getBalance()},
				{accounts.get(1).getID(),accounts.get(1).getType(),
						accounts.get(1).getBalance()},
				{accounts.get(2).getID(),accounts.get(2).getType(),
					accounts.get(2).getBalance()}
				};
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridBagLayout());
		
		DefaultTableModel dtm = new DefaultTableModel(account_data,columnNames);
		table = new JTable(dtm);
		// The default size of a JTable is something like
		// 450 X 400.
		Dimension smallerSize = new Dimension(450, 50);
		table.setPreferredScrollableViewportSize(smallerSize );
		
		JScrollPane scrollPaneForTable = new JScrollPane(table);
				
		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets = new Insets(4, 4, 4, 4);
		constraints.fill = GridBagConstraints.BOTH;

		contentPane.add(scrollPaneForTable,constraints);
		
		constraints.gridx = 0;
//		constraints.gridy = 1;
		constraints.weighty = 0;
		constraints.gridy = GridBagConstraints.RELATIVE;
		constraints.insets = new Insets(2, 4, 2, 4);
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel toLabel = new JLabel("From:");
		contentPane.add(toLabel,constraints);
		
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		fromField = new JTextField("3",8);
		// Workaround, because of: http://bugs.java.com/bugdatabase/view_bug.do?bug_id=4247013
		fromField.setMinimumSize(fromField.getPreferredSize());
		contentPane.add(fromField,constraints);
		
		constraints.gridx = 0;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel fromLabel = new JLabel("To:");
		contentPane.add(fromLabel,constraints);
		
		constraints.gridx = 1;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		toField = new JTextField("4",8);
		toField.setMinimumSize(toField.getPreferredSize());
		contentPane.add(toField,constraints);

		constraints.gridx = 0;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		JLabel amountLabel = new JLabel("Amount:");
		contentPane.add(amountLabel,constraints);
		
		constraints.gridx = 1;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		amountField = new JTextField("100",8);
		amountField.setMinimumSize(amountField.getPreferredSize());
		contentPane.add(amountField,constraints);

		constraints.gridx = 0;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		clearButton = new JButton("Clear");
		contentPane.add(clearButton,constraints);
		clearButton.addActionListener(this);/* {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel dtm = (DefaultTableModel) (table.getModel());
				int row = 0;
				int column = 2;
				// Example of how to read/write values from/to a JTable
				System.out.println("Old value: " + dtm.getValueAt(row, column));
				dtm.setValueAt(new Integer(999), row, column);
			}
		});*/
		
		constraints.gridx = 1;
//		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		transferButton = new JButton("Transfer");
		transferButton.addActionListener(this);

			/*@Override
			public void actionPerformed(ActionEvent e) {
				Object[][] newData = {
						//populate data here from database
						{new Integer(3), "Savings", new Integer(400)},
						{new Integer(4), "Checking", new Integer(370)}};
				// Example of how to change the table model of an
				//   existing JTable
				table.setModel(new DefaultTableModel(newData,columnNames));
				
			}
			
		});*/
		contentPane.add(transferButton,constraints);
	}
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource()==clearButton){
			//set text fields blank
			fromField.setText("");
			toField.setText("");
			amountField.setText("");
		}
		if(evt.getSource()==transferButton){
			int from=Integer.parseInt(fromField.getText());
			int to=Integer.parseInt(toField.getText());
			int amount=Integer.parseInt(amountField.getText());
			if(amount>0){
				DBTable.transfer(to,from,amount);
			}
			else{
				JOptionPane.showMessageDialog(null,"Amount cannot be transferred");
			}
		}
	}
	public void setTable(){
		try{
			data=new DBTable();
			data.createTable();
			accounts=data.retrieveTable();
			System.out.println("Hi");
		}
		catch(SQLException e){
			System.out.println("Could not connect to the database");
			System.err.println(e);
		}
	}
	public void getAccounts(){
		//for()
	}
	public void makeTable(){
		//for(int i=0;i<)
	}

	public static void main(String[] args) {
		JFrame frame = new AccountLayout();
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}