package com.mehana.smschat.dao;

import com.mehana.smschat.model.ActiveOperator;


public interface ActiveOperatorDAO extends GenericDAO<ActiveOperator> {

    void removeAllOperatorsByServer(String servername);

}
