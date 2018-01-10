package assign.service;

import java.util.List;

import assign.domain.Item;
import assign.domain.Transaction;

public interface MarketplaceService {

	public Item addItem(Item i) throws Exception;
	
	public Item  updateItem(Item i) throws Exception;
	
	public Item deleteItem(Long Id) throws Exception;
	
	public Item getItem(Long Id) throws Exception;

	public Transaction addTransaction(Transaction t) throws Exception;

	public Transaction updateTransaction(Transaction t) throws Exception;

	public List<Item> getAllItems() throws Exception;

	public List<Transaction> getAllTransactions() throws Exception;
}
