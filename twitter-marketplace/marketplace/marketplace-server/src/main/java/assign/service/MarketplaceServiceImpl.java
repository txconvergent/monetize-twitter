package assign.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import assign.domain.Item;

public class MarketplaceServiceImpl implements MarketplaceService {
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("deprecation")
	public MarketplaceServiceImpl() {
		// A SessionFactory is set up once for an application
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
	}

	@Override
	public Item addItem(Item i) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(i);
		    tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				throw e;
			}
		}
		finally {
			session.close();
		}
		
		return i;
	}


	@Override
	public Item updateItem(Item i) throws Exception {
		Session session = sessionFactory.openSession();
		  Transaction tx = null;
		  Item ni = null;
		  try{
		     tx = session.beginTransaction();
		     ni = 
		                (Item)session.get(Item.class, i.getId()); 
		     
		     ni.setDescription(i.getDescription());
		     ni.setName(i.getName());
		     ni.setLocation(i.getLocation());
		     ni.setPrice(i.getPrice());
		     session.update(ni); 
		     tx.commit();
		  }	catch (Exception e) {
			  if (tx != null) {
				tx.rollback();
				throw e;
			  }
		  }
		  
		  return ni;
	}


	@Override
	public Item deleteItem(Long Id) throws Exception {
		Session session = sessionFactory.openSession();		
		session.beginTransaction();
		String query = "from Item i where i.Id = :Id";		
				
		Item i = (Item) session.createQuery(query).setParameter("Id", Id).list().get(0);
		
        session.delete(i);

        session.getTransaction().commit();
        session.close();
        
        return i;
	}


	@Override
	public Item getItem(Long Id) throws Exception {
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Item.class).
        		add(Restrictions.eq("Id", Id));
		
		@SuppressWarnings("unchecked")
		List<Item> items = criteria.list();
		
		session.close();
		
		if (items.size() > 0) {
			return items.get(0);			
		} else {
			return null;
		}
	}
	
	@Override
	public List<Item> getAllItems() throws Exception {
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(Item.class);
		
		@SuppressWarnings("unchecked")
		List<Item> items = criteria.list();
		
		session.close();
		
		if (items.size() > 0) {
			return items;			
		} else {
			return null;
		}
	}
	
	@Override
	public List<assign.domain.Transaction> getAllTransactions() throws Exception {
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(assign.domain.Transaction.class);
		
		@SuppressWarnings("unchecked")
		List<assign.domain.Transaction> transactions = criteria.list();
		
		session.close();
		
		if (transactions.size() > 0) {
			return transactions;			
		} else {
			return null;
		}
	}


	@Override
	public assign.domain.Transaction addTransaction(assign.domain.Transaction t) throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			session.save(t);
		    tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				throw e;
			}
		}
		finally {
			session.close();
		}
		
		return t;
	}


	@Override
	public assign.domain.Transaction updateTransaction(assign.domain.Transaction t) throws Exception {
		Session session = sessionFactory.openSession();
		  Transaction tx = null;
		  assign.domain.Transaction nt = null;
		  try{
		     tx = session.beginTransaction();
		     nt = 
		                (assign.domain.Transaction)session.get(assign.domain.Transaction.class, t.getId()); 
		     
		     nt.setValue(t.getValue());
		     session.update(nt); 
		     tx.commit();
		  }	catch (Exception e) {
			  if (tx != null) {
				tx.rollback();
				throw e;
			  }
		  }
		  
		  return nt;
	}
	
}
