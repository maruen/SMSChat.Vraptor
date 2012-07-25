package com.mehana.smschat.dao;


public interface ActiveOperatorDAO extends GenericDAO<> {

    void removeAllOperatorsByServer(String servername);

}
