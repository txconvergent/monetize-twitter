package assign.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "transactions" )
@XmlRootElement(name = "transaction")
@XmlAccessorType(XmlAccessType.FIELD)
public class Transaction {
	private Item item;
	private String location;
	private Long sellerId;
	private Long buyerId;
	private Long value;
	private Long Id;
	
	@Id
	@Column(name="transaction_id")
	@XmlAttribute(name="id")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	public Long getId() {
		return this.Id;
	}
	
	public void setId(Long Id) {
		this.Id = Id;
	}
	
	@OneToOne
    @JoinColumn(name="item_id")
	public Item getItem() {
		return this.item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public Long getBuyerId() {
		return this.buyerId;
	}
	
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Long getSellerId() {
		return this.sellerId;
	}
	
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	
	public Long getValue() {
		return this.value;
	}
	
	public void setValue(Long value) {
		this.value = value;
	}
}
