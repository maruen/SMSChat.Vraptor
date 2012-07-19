package com.mehana.smschat.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;

import com.mehana.smschat.dao.ConfigDAO;
import com.mehana.smschat.model.Config;

@Component
public class ConfigDAOImpl implements ConfigDAO {

    private EntityManager manager;

    public ConfigDAOImpl(EntityManager manager) {
        this.manager = manager;
    }

    public Config getProperty(String property) {
        try {
            
        	Query query = manager.createQuery("SELECT obj FROM Config obj WHERE obj.property= :property", Config.class);
        	query.setParameter("property",property);
        	return (Config) query.getSingleResult();
  
        } catch (NoResultException e) {
            return null;
        }
    }

}
