package br.com.alura.LiterAlura_Challenge.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "livros")
public class Livro {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    private List<String> subjects;
    private int download_count;
    private String languages;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new ArrayList<>();


    public Livro() {}

    public Livro(DadosLivro dados) {
        this.title = dados.titulo();
        this.autores = dados.autores().stream()
                .map(Autor::new)
                .collect(Collectors.toList());
        this.languages = dados.idiomas().isEmpty() ? "Idioma Desconhecido" : dados.idiomas().get(0);
        this.download_count = dados.numeroDeDownloads();
        this.subjects = dados.genero();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public int getDownload_count() {
        return download_count;
    }

    public void setDownload_count(int download_count) {
        this.download_count = download_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String idioma) {
        this.languages = idioma;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        String autorString = autores.isEmpty() ? "Desconhecido" : autores.get(0).getName();
        return "\n----- LIVRO -----\n" +
                "Título: " + title + "\n" +
                "Autor: " + autorString + "\n" +
                "Idioma: " + languages + "\n" +
                "Número de downloads: " + download_count + "\n" +
                "------------------\n";
    }
}