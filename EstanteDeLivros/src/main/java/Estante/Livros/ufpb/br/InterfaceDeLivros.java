package Estante.Livros.ufpb.br;

import java.util.Collection;

public interface InterfaceDeLivros {

    boolean cadastraLivros(String nomeLivro, String nomeAutor, String diaDeixadoLivro);

    Collection<DadosDosLivros> pesquisaLivros(String nomeLivro, String NomeAutor);
    boolean removeLivro(String NomeAutor);


    Collection<DadosDosLivros> getLivros();
    void setLivross(Collection<DadosDosLivros> dadosDosLivros);
}

