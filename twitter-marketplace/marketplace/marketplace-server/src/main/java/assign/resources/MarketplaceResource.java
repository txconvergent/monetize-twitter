package assign.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import assign.domain.Item;
import assign.domain.Items;
import assign.domain.Transaction;
import assign.domain.Transactions;
import assign.service.MarketplaceService;
import assign.service.MarketplaceServiceImpl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@Path("/databases")
public class MarketplaceResource {
	
	MarketplaceService marketplaceService;
	
	public MarketplaceResource() {
		this.marketplaceService = new MarketplaceServiceImpl();		
	}
	
	@GET
	public String test() {
		return "Hello World";
	}
	
	@POST
	@Path("items/")
	@Consumes("application/xml")
	public Response addNewItem(InputStream is) throws Exception {
		Item i = readNewItem(is);
		
		try {
			i = this.marketplaceService.addItem(i);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		return Response.created(URI.create("/items/" + i.getId())).status(Status.CREATED).build();
	}
	
	@POST
	@Path("transactions/")
	@Consumes("application/xml")
	public Response addNewTransaction(InputStream is) throws Exception {
		Transaction t = readNewTransaction(is);
		
		try {
			t = this.marketplaceService.addTransaction(t);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		return Response.created(URI.create("/transactions/" + t.getId())).status(Status.CREATED).build();
	}
	
	@PUT
	@Path("/items/{id}")
	@Consumes("application/xml")
	public Response updateItem(@PathParam("id") String id, InputStream is) throws Exception {
		Item i = readNewItem(is);	
		i.setId(Long.valueOf(id));
		
		try {
			marketplaceService.updateItem(i);
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.created(URI.create("/items/" + i.getId())).status(Status.NO_CONTENT).build();
	}
	
	@DELETE
	@Path("/items/{id}")
	@Produces("application/xml")
	public Response deleteItem(@PathParam("id") String itemID) throws Exception {
		
		Long id = Long.valueOf(itemID);
		
		try {
			marketplaceService.deleteItem(id);
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.status(Status.OK).build();
	}
	
	@GET
	@Path("/items/{id}")
	@Produces("application/xml")
	public Response getItem(@PathParam("id") String id) throws Exception {
		Item item = marketplaceService.getItem(Long.valueOf(id));
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();
        
		if (item != null) {
			outputItem(document, item);
			
			return Response.status(Status.OK).entity(document).build();
		} else {
		    return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("/items/")
	@Produces("application/xml")
	public Response getAllItems() throws Exception {
		List<Item> itemsList = marketplaceService.getAllItems();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();
        
		if (itemsList != null) {
			Items items = new Items(itemsList);
			outputItems(document, items);
			
			return Response.status(Status.OK).entity(document).build();
		} else {
		    return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@GET
	@Path("/transactions/")
	@Produces("application/xml")
	public Response getAllTransactions() throws Exception {
		List<Transaction> transactionList = marketplaceService.getAllTransactions();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();
        
		if (transactionList != null) {
			Transactions transactions = new Transactions(transactionList);
			outputTransactions(document, transactions);
			
			return Response.status(Status.OK).entity(document).build();
		} else {
		    return Response.status(Status.NOT_FOUND).build();
		}
	}
	
	@PUT
	@Path("/transactions/{id}")
	@Produces("application/xml")
	public Response updateTransaction(InputStream is, @PathParam("id") String id) throws Exception {
		
		Transaction t = readNewTransaction(is);
		
		t.setId(Long.valueOf(id));
		
		try {
			marketplaceService.updateTransaction(t);
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.created(URI.create("/transactions/" + t.getId())).status(Status.OK).build();
	}
	
	protected void outputItem(Document document, Item item) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(new Class [] {Item.class});
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(item, document);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}
	
	protected void outputTransaction(Document document, Transaction transaction) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(new Class [] {Transaction.class});
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(transaction, document);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}
	
	protected void outputItems(Document document, Items items) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(new Class [] {Items.class});
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(items, document);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}
	
	protected void outputTransactions(Document document, Transactions transactions) throws IOException {
		try { 
			JAXBContext jaxbContext = JAXBContext.newInstance(new Class [] {Transactions.class});
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	 
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(transactions, document);
		} catch (JAXBException jaxb) {
			jaxb.printStackTrace();
			throw new WebApplicationException();
		}
	}
	
	
	protected Item readNewItem(InputStream is) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    Document doc = builder.parse(is);
		    Element root = doc.getDocumentElement();
		    Item item = new Item();
		    NodeList nodes = root.getChildNodes();
		    for (int i = 0; i < nodes.getLength(); i++) {
		        Element element = (Element) nodes.item(i);
		        if (element.getTagName().equals("name")) {
		        	item.setName(element.getTextContent());
		        } else if (element.getTagName().equals("description")) {
		        	item.setDescription(element.getTextContent());
		        } else if (element.getTagName().equals("sellerId")) {
		        	item.setSellerId(Long.valueOf(element.getTextContent()));
		        } else if (element.getTagName().equals("location")) {
		        	item.setLocation(element.getTextContent());
		        } else if (element.getTagName().equals("price")) {
		        	item.setPrice(Long.valueOf(element.getTextContent()));
		        }
		    }
	        return item;
      }	catch (Exception e) {
         throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
      }
   }
	
	protected Transaction readNewTransaction(InputStream is) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		    Document doc = builder.parse(is);
		    Element root = doc.getDocumentElement();
		    Transaction transaction = new Transaction();
		    NodeList nodes = root.getChildNodes();
		    for (int i = 0; i < nodes.getLength(); i++) {
		        Element element = (Element) nodes.item(i);
		        if (element.getTagName().equals("buyerId")) {
		        	transaction.setBuyerId(Long.valueOf(element.getTextContent()));
		        } else if (element.getTagName().equals("sellerId")) {
		        	transaction.setSellerId(Long.valueOf(element.getTextContent()));
		        } else if (element.getTagName().equals("location")) {
		        	transaction.setLocation(element.getTextContent());
		        } else if (element.getTagName().equals("value")) {
		        	transaction.setValue(Long.valueOf(element.getTextContent()));
		        }
		    }
	        return transaction;
      }	catch (Exception e) {
         throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
      }
   }
}
