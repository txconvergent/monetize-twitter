package assign.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transaction_list")
@XmlAccessorType(XmlAccessType.FIELD)
public class Transactions {
	private List<Transaction> transaction;
	
	public Transactions () {}
	
	public Transactions (List<Transaction> transaction) {
		this.transaction = transaction;
	}
	
	public List<Transaction> getTransaction() {
		return transaction;
	}
	
	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}
}
