package com.github.adminfaces.starter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.adminfaces.starter.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query("FROM Usuario u WHERE u.statusUsuario = 'CADASTRO' ")
	List<Usuario> findAllStatusUsuario();

	Usuario findByEmail(String email);

	Usuario findBySindicoTrue();
	
}
