package assign.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "items_list")
@XmlAccessorType(XmlAccessType.FIELD)
public class Items {
	private List<Item> item;
	
	public Items () {}

	public Items (List<Item> item) {
		this.item = item;
	}
	
	public List<Item> getItems() {
		return item;
	}
	
	public void setItems(List<Item> item) {
		this.item = item;
	}
}
