package Estante.Livros.ufpb.br;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LivroAddController implements ActionListener {

    private final InterfaceDeLivros interfaceDaEstanteDeLivros;
    private final JFrame JanelaPrincial;

    public LivroAddController(InterfaceDeLivros interfaceDaEstanteDeLivros, JFrame janelaPrincial){
        this.interfaceDaEstanteDeLivros = interfaceDaEstanteDeLivros;
        this.JanelaPrincial = janelaPrincial;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nomeDoLivro = JOptionPane.showInputDialog("Qual é o nome Do Livro?");
        String nomeAutor = JOptionPane.showInputDialog("Qual é o nome do Autor");
        String DiaQueDeixouLivro = JOptionPane.showInputDialog("Qual dia esta Deixando o livro? ");
        boolean cadastrou = this.interfaceDaEstanteDeLivros.cadastraLivros(nomeDoLivro,nomeAutor, String.valueOf(DiaQueDeixouLivro));
        if (cadastrou){
            JOptionPane.showMessageDialog(JanelaPrincial,
                    "Livros cadastrado.");

        }else {
            JOptionPane.showMessageDialog(JanelaPrincial,
                    "Livro não foi cadastrado." +
                    "Verifique se o livro já não existe");
        }

    }
}
