package br.com.alura.LiterAlura_Challenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadoBusca(List<DadosLivro> results) {
}
