package beans;

public class Order {

	public String id;
	public int amount;
	
	public Order(String id, int amount) {
		this.id = id;
		this.amount = amount;
	}
	
	public String getId() {
		return id;
	}
	
	public int getAmount() {
		return amount;
	}
	
	@Override
	public String toString() {
		return "<ID: " + id + ", Amount: " + amount + ">";
	}
	
}
