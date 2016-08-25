package br.com.agf.dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;

import br.com.agf.domain.Usuario;

public class UsuarioDao {

	protected EntityManager entityManager;
	
	@Inject
	public UsuarioDao(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	public void saveUsuarioInNewTransaction(Usuario usuario){
		entityManager.getTransaction().begin();
		save(usuario);
		entityManager.getTransaction().commit();
	}
	
	public void save(Usuario usuario){
		entityManager.persist(usuario);
	}
}
