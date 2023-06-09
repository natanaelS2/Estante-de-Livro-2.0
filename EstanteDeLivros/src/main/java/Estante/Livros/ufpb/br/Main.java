package Estante.Livros.ufpb.br;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Main {
    public static void  main(String []args){
        GravarLivros gravador = new GravarLivros();
        Collection<DadosDosLivros> dadosDosLivrosRecuperados = new ArrayList<>();
        try {
             dadosDosLivrosRecuperados = gravador.recuperaLivro();
            for (DadosDosLivros p : dadosDosLivrosRecuperados){
                JOptionPane.showMessageDialog(null,"Dados Dos Livros:" + p.getNomeDoLivro());
            }

        }catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Problema ao recuperar o livro");
            e.printStackTrace();
        }
        String nomeLivro = JOptionPane.showInputDialog("Qual é o nome do Livro?");
        String NomeAutor = JOptionPane.showInputDialog("Qual é o Nome Do Autor");
        String diaDeixadoLivro = JOptionPane.showInputDialog("Qual o dia que o livor foi deixado");
        dadosDosLivrosRecuperados.add(new DadosDosLivros(nomeLivro,NomeAutor,diaDeixadoLivro));
        try{
            gravador.gravaLivro(dadosDosLivrosRecuperados);
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Problema ao gravar o livro");
            e.printStackTrace();
        }
    }
}
