package com.mehana.smschat.repository;

import com.mehana.smschat.model.Usuario;
import com.mehana.smschat.model.UsuarioImage;
import com.mehana.smschat.repository.common.GenericImageRepository;
import com.mehana.smschat.repository.common.GenericRepository;

public interface UsuarioRepository extends GenericRepository<Usuario>, GenericImageRepository<Usuario, UsuarioImage> {

	Boolean isMailExist(Usuario entity);

}
