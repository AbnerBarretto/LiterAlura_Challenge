package br.com.alura.LiterAlura_Challenge.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")

public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int birth_year;
    private int death_year;

    @ManyToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {
    }

    public Autor(DadosAutor dadosAutor) {
        this.name = dadosAutor.nome();
        if (dadosAutor.nascimento() != null) {
            this.birth_year = dadosAutor.nascimento();
        }
        if (dadosAutor.morte() != null) {
            this.death_year = dadosAutor.morte();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    public int getDeath_year() {
        return death_year;
    }

    public void setDeath_year(int death_year) {
        this.death_year = death_year;
    }

    @Override
    public String toString() {
        return  "\nAutor: " + name + "\n" +
                "Ano de nascimento: " + birth_year + "\n" +
                "Ano de falecimento: " + death_year + "\n";
    }
}