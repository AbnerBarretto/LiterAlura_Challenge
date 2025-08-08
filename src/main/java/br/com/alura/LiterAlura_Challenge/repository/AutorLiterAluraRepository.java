package br.com.alura.LiterAlura_Challenge.repository;

import br.com.alura.LiterAlura_Challenge.model.Autor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorLiterAluraRepository extends JpaRepository<Autor,Long> {

}