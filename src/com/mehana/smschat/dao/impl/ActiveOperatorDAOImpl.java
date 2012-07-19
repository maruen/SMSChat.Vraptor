package com.mehana.smschat.dao.impl;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;

import com.mehana.smschat.dao.ActiveOperatorDAO;

@Component
public class ActiveOperatorDAOImpl implements ActiveOperatorDAO {

    @SuppressWarnings("unused")
    private EntityManager manager;

    public ActiveOperatorDAOImpl(EntityManager manager) {
        this.manager = manager;
    }

    public void removeAllOperatorsByServer(String servername) {
        
    }

}
