package model.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import view.UserInteraction;


@Component
public class DatabaseConnection {
	Logger logger = LoggerFactory.getLogger(UserInteraction.class);
	private SessionFactory sessionFactory;
	public static DatabaseConnection connectionRepository = new DatabaseConnection();
	
	private DatabaseConnection() {
		sessionFactory = new Configuration().configure("configuration/hibernate.cfg.xml")
				.buildSessionFactory();
		logger.trace("Connection is made");
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
