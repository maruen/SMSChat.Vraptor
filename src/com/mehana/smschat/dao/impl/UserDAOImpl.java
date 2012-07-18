package com.mehana.smschat.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;

import com.mehana.smschat.dao.UserDAO;
import com.mehana.smschat.model.User;

@Component
public class UserDAOImpl implements UserDAO {

    private EntityManager manager;

    public UserDAOImpl(EntityManager manager) {
        this.manager = manager;
    }

    public User autenticate(String email, String password) {
        try {
            String sql = "from " + User.class.getName() + " where email = :email and password = :password";
            Query query = manager.createQuery(sql);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
