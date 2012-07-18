package com.mehana.smschat.dao;

import com.mehana.smschat.model.Config;

public interface ConfigDAO {

    Config getProperty(String property);

}
