package com.mehana.smschat.dao;

import com.mehana.smschat.model.User;

public interface UserDAO {

    User autenticate(String email, String password);

}
