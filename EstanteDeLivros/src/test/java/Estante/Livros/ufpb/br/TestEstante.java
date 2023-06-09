package Estante.Livros.ufpb.br;

import org.junit.Test;
import java.util.Collection;
import static org.junit.Assert.*;

public class TestEstante {
    @Test
    public void testEstatnte(){
        Estante sistema= new Estante();
        DadosDosLivros dadosDosLivros = new DadosDosLivros("O menino de pijama listrado","John Boyne","07/06/2023");
        assertTrue(sistema.cadastraLivros(dadosDosLivros.getNomeDoLivro(),dadosDosLivros.getNomeAutor(), dadosDosLivros.getDiaQueDepositou()));
        Collection<DadosDosLivros> dadosDosLivrosCollection = sistema.pesquisaLivros(dadosDosLivros.getNomeDoLivro(), dadosDosLivros.getNomeAutor());
        assertTrue(dadosDosLivrosCollection.size() == 1);
        assertTrue(sistema.removeLivro(dadosDosLivros.getNomeAutor()));

    }
}
