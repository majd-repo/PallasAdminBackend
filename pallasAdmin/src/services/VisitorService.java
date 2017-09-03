package services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import persistence.Transfer;
import persistence.Visitor;
import services.interfaces.VisitorServiceLocal;
import util.HtmlEmailSender;
import util.MailContentBuilder;

@Stateless
@LocalBean
public class VisitorService implements VisitorServiceLocal {

	private String NO_VISITOR_IMG = null;
	private String IMG_URL = null;

	@Resource(name = "java:jboss/mail/postfix")
	private Session session;

	@PersistenceContext
	private EntityManager em;

	public VisitorService() throws IOException {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = getClass().getClassLoader().getResourceAsStream(
					"config.properties");
			// load a properties file
			prop.load(input);
			NO_VISITOR_IMG = prop.getProperty("NO_VISITOR_IMG");
			IMG_URL = prop.getProperty("IMG_URL");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Visitor> findAllVisitors() {

		return em.createQuery("SELECT v from Visitor v", Visitor.class)
				.getResultList();

	}

	@Override
	public void addVisitor(Visitor visitor) {

		visitor.setPicture(NO_VISITOR_IMG);
		visitor.setRole("VISITOR");
		visitor.setActive(false);
		visitor.setDcr(new Date());
		em.persist(visitor);

		try {

			HtmlEmailSender.sendHtmlEmail(session, "Account created",
					MailContentBuilder.buildCreationtionMail(visitor),
					visitor.getEmail());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void saveVisitor(Visitor visitor) {
		visitor.setDmj(new Date());
		em.merge(visitor);

	}

	@Override
	public void removeVisitor(Integer id) {
		List<Transfer> transfers = em.createQuery("SELECT t FROM Transfer t",
				Transfer.class).getResultList();
		for (Transfer transfer : transfers) {
			transfer.getVisitors().remove(em.merge(em.find(Visitor.class, id)));
		}
		em.remove(em.merge(em.find(Visitor.class, id)));

	}

	@Override
	public Visitor findVisitorById(Integer id) {
		return em.find(Visitor.class, id);
	}

	@Override
	public void setPicture(Integer entityId, String fileName) {
		em.find(Visitor.class, entityId).setPicture(IMG_URL + fileName);

	}

	@Override
	public void activateVisitor(Integer id, Boolean active) {
		Visitor visitor = em.find(Visitor.class, id);
		visitor.setActive(active);
		visitor.setDmj(new Date());
		em.merge(visitor);

	}

}
