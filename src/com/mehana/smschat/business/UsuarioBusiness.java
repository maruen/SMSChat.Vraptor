package com.mehana.smschat.business;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.mehana.smschat.business.common.GenericImageBusiness;
import com.mehana.smschat.exception.CommonException;
import com.mehana.smschat.model.Usuario;
import com.mehana.smschat.model.UsuarioImage;
import com.mehana.smschat.repository.UsuarioRepository;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class UsuarioBusiness extends GenericImageBusiness<Usuario, UsuarioImage> implements UsuarioRepository {

	public UsuarioBusiness(EntityManager manager) {
		super(manager);
	}

	public Boolean isMailExist(Usuario entity) {
		try {
			Query query = manager.createQuery("select id from " + Usuario.class.getName() + " where email = :email and (:id is null or id != :id)");
			query.setParameter("email", entity.getEmail());
			query.setParameter("id", entity.getId());
			return (query.getSingleResult() != null);
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	public Usuario save(Usuario entity) throws CommonException {
		if (this.isMailExist(entity)) {
			throw new CommonException("email.ja.cadastrado");
		}

		return super.save(entity);
	}

	@Override
	public Usuario loadById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
