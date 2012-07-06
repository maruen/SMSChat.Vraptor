package com.mehana.smschat.business;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.mehana.smschat.model.Usuario;
import com.mehana.smschat.repository.LoginRepository;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class LoginBusiness implements LoginRepository {

	private EntityManager manager;

	public LoginBusiness(EntityManager manager) {
		this.manager = manager;
	}

	public Usuario autenticar(String email, String senha) {
		try {
			Query query = manager.createQuery("from " + Usuario.class.getName() + " where email = :email and senha = :senha");
			query.setParameter("email", email);
			query.setParameter("senha", senha);
			return (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
