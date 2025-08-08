package br.com.alura.LiterAlura_Challenge.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(@JsonAlias("title") String titulo,
                         @JsonAlias("authors") List<DadosAutor> autores,
                         @JsonAlias("subjects") List<String> genero,
                         @JsonAlias("download_count") Integer numeroDeDownloads,
                         @JsonAlias("languages") List<String> idiomas
) {
}