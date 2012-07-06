package com.mehana.smschat.repository;

import com.mehana.smschat.model.Usuario;

public interface LoginRepository {

	Usuario autenticar(String email, String senha);

}
