package com.mehana.smschat.component;

import java.io.Serializable;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

import com.mehana.smschat.model.Config;
import com.mehana.smschat.model.User;

@Component
@SessionScoped
public class UserSession implements Serializable {

    private static final long serialVersionUID = 8113472081570152045L;

    private User              user;
    private String            language;
    private Config            config;

    public boolean isLogged() {
        return user != null;
    }

    public void logout() {
        user = null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

}
