package lab_7_pack;
import java.sql.*;
import java.util.ArrayList;
import java.io.*;

public class DBTable {

    private Connection con;
    private Statement stmt;

    // Load class and create a connection to the database
    public DBTable() throws SQLException {
        // Connection string
        String connectionString = "jdbc:mysql://kc-sce-appdb01.kc.umkc.edu/ertgd8";
        String userID = "ertgd8";
        String password = "NRASn1pgbLn";
    
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(java.lang.ClassNotFoundException e) {
            System.out.println(e); 
            System.exit(0);
        }
        
        con = DriverManager.getConnection(connectionString,userID,password);
        stmt = con.createStatement();
        //createTable();
    }

    public void cleanup() throws SQLException {
        // Close connection and statement
        // Connections, statements, and result sets are
        // closed automatically when garbage collected
        // but it is a good idea to free them as soon
        // as possible.
        // Closing a statement closes its current result set.
        // Operations that cause a new result set to be
        // created for a statement automatically close
        // the old result set.
        stmt.close();
        con.close();
    }

    public void createTable() throws SQLException {
    	String sqlCmd1="CREATE TABLE IF NOT EXISTS Accounts " +
    			"(ID INT PRIMARY KEY AUTO_INCREMENT, " +
    			"Type VARCHAR(64), " +
    			"Balance INT)";
    	String sqlCmd2="INSERT INTO Accounts(Type,Balance) VALUES('Savings','100')";
    	String sqlCmd3="INSERT INTO Accounts(Type,Balance) VALUES('Checking','500')";
    	String sqlCmd4="INSERT INTO Accounts(Type,Balance) VALUES('Savings','300')";
    	String sqlCmd5="DELETE FROM TABLE Accounts";
    	stmt.executeUpdate(sqlCmd1);
    	//stmt.executeUpdate(sqlCmd2);
    	//stmt.executeUpdate(sqlCmd3);
    	//stmt.executeUpdate(sqlCmd4);
    }
    public int getRows() throws SQLException{
    	String sqlCmd="SELECT Count(*) FROM Accounts";
    	ResultSet rs=stmt.executeQuery(sqlCmd);
    	rs.next();
    	int count=rs.getInt(1);
    	return count;
    }
    public ArrayList<Account> retrieveTable() throws SQLException{
    	int numRows=getRows();
    	String sqlCmd1="SELECT * FROM Accounts";
    	ResultSet rs=stmt.executeQuery(sqlCmd1);
    	ResultSetMetaData rsmd1=rs.getMetaData();
    	int numCols=rsmd1.getColumnCount();
    	ArrayList<Account> accounts=new ArrayList<Account>();
    	
    	//for each row, make account to pass to UI
    	boolean more=rs.next();
    	while(more){
    		//create new account
    		Account account=new Account();
    		ArrayList<String> val_list=new ArrayList<String>(); //list of data vals
    		for(int i=1;i<=numCols;++i){
    			//get data for account
    			val_list.add(rs.getString(i));
    		}
    		account.setID(val_list.get(0)); //val_list 0 is the id
    		account.setType(val_list.get(1)); //vaal_list 1 is the type
    		account.setBalance(val_list.get(2)); //val_list 2 is the balance
    		accounts.add(account);
    		more=rs.next();
    	}
    	return accounts;
    }
    static public void transfer(int to,int from,int amount){
    	//ResultSet result=stmt.executeQuery()
    	String trans="START TRANSACTION;";
    	trans+="ROLLBACK";	
    }
    public void credit(String id,int amount) throws SQLException{
    	
    }
    public void debit(String id,int amount) throws SQLException{
    	
    }
    public void updateTable() throws SQLException { }
    public void queryTable() throws SQLException { }
}