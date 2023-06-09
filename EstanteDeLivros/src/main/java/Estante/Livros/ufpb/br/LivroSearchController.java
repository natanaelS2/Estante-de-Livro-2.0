package Estante.Livros.ufpb.br;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class LivroSearchController implements ActionListener {

    private final InterfaceDeLivros interfaceDeLivros;
    private final JFrame JanelaPrincipal;

    public LivroSearchController(InterfaceDeLivros interfaceDeLivros, JFrame janelaPrincipal) {
        this.interfaceDeLivros = interfaceDeLivros;
        this.JanelaPrincipal = janelaPrincipal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nomeLivro = JOptionPane.showInputDialog("Qual é o nome do Livro?");
        String nomeAutor = JOptionPane.showInputDialog("Qual é o Nome do autor?");
        Collection<DadosDosLivros> Pesquisar = interfaceDeLivros.pesquisaLivros(nomeLivro, nomeAutor);
        if (Pesquisar.size() > 0) {
            JOptionPane.showMessageDialog(JanelaPrincipal, "Livros encontrado");
            for (DadosDosLivros p : Pesquisar) {
                JOptionPane.showMessageDialog(JanelaPrincipal, p.toString());
            }
        } else {
            JOptionPane.showMessageDialog(JanelaPrincipal, "Livros não encontrado");
        }
    }
}
