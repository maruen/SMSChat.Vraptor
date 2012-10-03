package com.mehana.smschat.controller;

import static com.mehana.smschat.util.Utils.i18n;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;

import com.mehana.smschat.annotation.Public;
import com.mehana.smschat.component.UserSession;
import com.mehana.smschat.dao.ConfigDAO;
import com.mehana.smschat.dao.UserDAO;
import com.mehana.smschat.model.Config;
import com.mehana.smschat.model.User;

@Resource
public class ChatController {

    private final Result      result;
    private final UserDAO     userDAO;
    private final ConfigDAO   configDAO;
    private final UserSession userSession;
    
    private final static String TCP_ENGINE_PORT = "tcpEnginePort"; 

    public ChatController(Result result, UserDAO userDAO, ConfigDAO configDAO, UserSession userSession) {
        this.result = result;
        this.userDAO = userDAO;
        this.userSession = userSession;
        this.configDAO = configDAO;
    }

    @Public
    @Post("/autenticate")
    public void autenticate(User entity) {
        User user = userDAO.autenticate(entity.getUsername(), entity.getPassword());
        

        if (user != null) {
            userSession.setUser(user);
            
            Config config = configDAO.getProperty(TCP_ENGINE_PORT);
            userSession.setConfig(config);

            try {
                result.redirectTo(ChatController.class).chat();
            } catch (IllegalStateException e) {
                result.redirectTo(IndexController.class).index();
            }
        } else {
            result.include("error", i18n("email.senha.incorreta")).redirectTo(IndexController.class).index();
        }
    }

    @Path("/chat")
    public void chat() {

    }
    
    @Path("/SMSSimulator")
    public void SMSSimulator() {

    }

    @Get("/logout")
    public void logout() {
        userSession.logout();
        result.redirectTo(IndexController.class).index();
    }

}
