package lab_7_pack;

public class Account {
	private String id,type,balance;
	public Account(){}
	public void setID(String string){
		id=string;
	}
	public void setType(String the_type){
		type=the_type;
	}
	public void setBalance(String the_balance){
		balance=the_balance;
	}
	public String getID(){
		return id;
	}
	public String getType(){
		return type;
	}
	public String getBalance(){
		return balance;
	}
}
