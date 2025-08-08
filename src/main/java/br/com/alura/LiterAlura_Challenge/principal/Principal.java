package br.com.alura.LiterAlura_Challenge.principal;

import br.com.alura.LiterAlura_Challenge.model.*;
import br.com.alura.LiterAlura_Challenge.repository.AutorLiterAluraRepository;
import br.com.alura.LiterAlura_Challenge.repository.LivroLiterAluraRepository;
import br.com.alura.LiterAlura_Challenge.services.ConsumoApi;
import br.com.alura.LiterAlura_Challenge.services.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    Scanner leitura = new Scanner(System.in);
    private String ENDERECO = "https://gutendex.com/books/";
    ConsumoApi consumo = new ConsumoApi();
    ConverteDados conversor = new ConverteDados();

    @Autowired
    private LivroLiterAluraRepository repositorioLivro;

    @Autowired
    private AutorLiterAluraRepository repositorioAutores;


    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar Livro pelo título
                    2 - Buscar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroTitulo();
                    break;
                case 2:
                    buscarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosDeterminadoAno();
                    break;
                case 5:
                    listarLivrosDeterminadoIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção invalida!");
            }
        }
    }

    private Optional<DadosLivro> getDadosLivro() {
        System.out.println("Digite o nome de um Livro para busca");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + "?search=" + nomeLivro.replace(" ", "+"));
        ResultadoBusca resultado = conversor.obterDados(json, ResultadoBusca.class);

        if (resultado.results().isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultado.results().get(0));
        }
    }

    private void buscarLivroTitulo() {
        Optional<DadosLivro> dadosLivro = getDadosLivro();

        if (dadosLivro.isPresent()) {
            DadosLivro livroEncontrado = dadosLivro.get();
            Livro livro = new Livro(livroEncontrado);

            try {
                repositorioLivro.save(livro);
            } catch (Exception e) {
                System.out.println("Erro: Livro já registrado. Não foi possível salvar.");
            }
            System.out.println(livro);

        } else {
            System.out.println("Nenhum livro encontrado com o título informado.");
        }
    }

    private void buscarLivrosRegistrados() {
        List<Livro> livros = repositorioLivro.findAll();
        livros.forEach(System.out::println);

    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = repositorioAutores.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor foi encontrado!");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosDeterminadoAno() {
        System.out.println("Digite o ano para buscar um autor vivo no periodo:");
        var ano = leitura.nextInt();
        leitura.nextLine();
        List<Autor> autoresVivos = repositorioLivro.buscarAutoresVivosPorAno(ano);

        if(autoresVivos.isEmpty()){
            System.out.println("Nenhum autor vivo registrado foi encontrado esse ano!");
        }else{
            autoresVivos.forEach(System.out::println);
        }

    }

    private void listarLivrosDeterminadoIdioma() {
        System.out.println("""
                Insira o idioma para realizar a busca:
                
                1. pt - Português
                2. es - Espanhol
                3. en - Inglês
                4. fr - Francês
                
                """);

        var opcao = leitura.nextInt();
        leitura.nextLine();
        String idioma = " ";

        switch (opcao){

            case 1 :
                idioma = "pt";
                break;
            case 2:
                idioma = "es";
                break;
            case 3:
                idioma = "en";
                break;
            case 4:
                idioma = "fr";
                break;
            default:
                System.out.println("Insira uma opção válida!");
        }

        List<Livro> livros = repositorioLivro.findByLanguages(idioma);
            if(livros.isEmpty()){
                System.out.println("Nenhum livro encontrado no idioma !");
            }else{
                livros.forEach(System.out::println);
            }

    }
}
