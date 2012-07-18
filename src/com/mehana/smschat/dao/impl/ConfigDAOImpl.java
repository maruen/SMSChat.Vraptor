package com.mehana.smschat.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

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
            Config config = manager.find(Config.class, "property");
            return config;

        } catch (NoResultException e) {
            return null;
        }
    }

}
