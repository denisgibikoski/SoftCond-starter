package com.github.adminfaces.starter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.adminfaces.starter.model.UnidadeMoradia;
import com.github.adminfaces.starter.model.Usuario;

@Repository
public interface UnidadeMoradiaRepository extends JpaRepository<UnidadeMoradia, Long> {

	UnidadeMoradia findByUsuario(Usuario usuario);




}
