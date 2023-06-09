package Estante.Livros.ufpb.br;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LivroRemoverController implements ActionListener {
    private final InterfaceDeLivros interfaceDeLivros;
    private final JFrame JanelaPrincial;

    public LivroRemoverController(InterfaceDeLivros interfaceDeLivros, JFrame janelaPrincial){
        this.interfaceDeLivros = interfaceDeLivros;
        this.JanelaPrincial = janelaPrincial;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nomeAutor = JOptionPane.showInputDialog("Qual é o Nome do Autor?");

        boolean remover = interfaceDeLivros.removeLivro(nomeAutor);
        if (remover) {
            JOptionPane.showMessageDialog(JanelaPrincial, "Livro removido.");
        } else {
            JOptionPane.showMessageDialog(JanelaPrincial, "Livro não foi encontrado. Operação não realizada.");
        }
    }
}
