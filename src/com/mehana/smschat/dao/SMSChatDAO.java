package com.mehana.smschat.dao;

import com.mehana.smschat.model.User;

public interface SMSChatDAO {

	User autenticate(String email, String password);

}
