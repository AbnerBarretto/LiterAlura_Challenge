package br.com.alura.LiterAlura_Challenge.repository;

import br.com.alura.LiterAlura_Challenge.model.Autor;
import br.com.alura.LiterAlura_Challenge.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroLiterAluraRepository extends JpaRepository<Livro,Long> {

    @Query("SELECT a FROM Autor a WHERE a.birth_year <= :ano AND a.death_year >= :ano")
    List<Autor> buscarAutoresVivosPorAno(int ano);

    List<Livro> findByLanguages(String idioma);
}
