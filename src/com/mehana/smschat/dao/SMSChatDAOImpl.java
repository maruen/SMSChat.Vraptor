package com.mehana.smschat.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;

import com.mehana.smschat.model.User;

@Component
public class SMSChatDAOImpl implements SMSChatDAO {

	private EntityManager manager;

	public SMSChatDAOImpl(EntityManager manager) {
		this.manager = manager;
	}

	public User autenticate(String email, String password) {
		try {
			Query query = manager.createQuery("from " + User.class.getName() + " where email = :email and password = :password");
			query.setParameter("email", email);
			query.setParameter("password", password);
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
