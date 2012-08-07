package com.mehana.smschat.dao.impl;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;

import com.mehana.smschat.dao.ActiveOperatorDAO;
import com.mehana.smschat.model.ActiveOperator;

@Component
public class ActiveOperatorDAOImpl extends GenericDAOImpl<ActiveOperator> implements ActiveOperatorDAO {

    protected ActiveOperatorDAOImpl(EntityManager manager) {
        super(manager);
    }

    public void removeAllOperatorsByServer(String servername) {
    }

    @Override
    public ActiveOperator loadById(Long id) {
        return null;
    }

}
