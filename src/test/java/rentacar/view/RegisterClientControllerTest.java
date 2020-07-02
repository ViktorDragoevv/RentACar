package rentacar.view;

import static org.junit.Assert.assertFalse;

import org.hibernate.Session;
import org.junit.Test;

import rentacar.Client;

public class RegisterClientControllerTest {

	@Test
	public void testCheckCl() {		
		String pin = "9802026584";
		Session session = rentacar.HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
		Client c = (Client) session.createQuery("from Client s where s.clientPIN='" + pin + "'").uniqueResult();
		session.getTransaction().commit();
		assertFalse("Client is null",RegisterClientController.checkCl(c));
	}

}
