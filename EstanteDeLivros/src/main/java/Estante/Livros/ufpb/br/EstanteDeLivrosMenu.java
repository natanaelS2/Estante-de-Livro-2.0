package Estante.Livros.ufpb.br;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class EstanteDeLivrosMenu extends JFrame {
    private final InterfaceDeLivros interfaceDeLivros;
    private final GravarLivros gravador;
    private JTextField textFieldNomeLivro;
    private JTextField textFieldNomeAutor;
    private JTextField textFieldDiaDeixado;
    private JTextArea textAreaLivros;
    private final SalvarContas salvarContas;

    private final Map<String, String> contasUsuarios;
    public EstanteDeLivrosMenu() throws IOException {
        interfaceDeLivros = new Estante();
        gravador = new GravarLivros();
        salvarContas = new SalvarContas();

        contasUsuarios = salvarContas.carregarContas();

        exibirTelaLogin();
    }

    private void exibirTelaLogin() {
        JFrame frameLogin = new JFrame("Login");
        frameLogin.setSize(300, 150);
        frameLogin.setLocationRelativeTo(null);
        frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameLogin.setResizable(false);

        JPanel panelLogin = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel labelUsername = new JLabel("Login:");
        JTextField textFieldUsername = new JTextField(15);
        JLabel labelPassword = new JLabel("Senha:");
        JPasswordField passwordField = new JPasswordField(15);
        JButton buttonLogin = new JButton("Login");

        constraints.gridx = 0;
        constraints.gridy = 0;
        panelLogin.add(labelUsername, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panelLogin.add(textFieldUsername, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panelLogin.add(labelPassword, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panelLogin.add(passwordField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panelLogin.add(buttonLogin, constraints);

        frameLogin.add(panelLogin);
        frameLogin.setVisible(true);
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String password = new String(passwordField.getPassword());

                // Definir login e senha do funcionário
                String funcionarioLogin = "admin";
                String funcionarioSenha = "admin123";

                if (funcionarioLogin.equals(username) && funcionarioSenha.equals(password)) {
                    frameLogin.dispose(); // Fecha a janela de login
                    exibirTelaFuncionario();
                } else if (contasUsuarios.containsKey(username) && contasUsuarios.get(username).equals(password)) {
                    frameLogin.dispose(); // Fecha a janela de login
                    exibirTelaUsuarioCadastrado();
                } else {
                    int cadastrar = JOptionPane.showConfirmDialog(null, "Usuário não encontrado. Deseja cadastrar?", "Usuário não encontrado", JOptionPane.YES_NO_OPTION);
                    if (cadastrar == JOptionPane.YES_OPTION) {
                        contasUsuarios.put(username, password);
                        try {
                            salvarContas.salvarContas(contasUsuarios);
                            frameLogin.dispose(); // Fecha a janela de login
                            exibirTelaUsuarioCadastrado();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(null, "Problema ao salvar as contas de usuário");
                            ex.printStackTrace();
                        }
                    } else {
                        // Redirecionar para a tela de login novamente
                        textFieldUsername.setText("");
                        passwordField.setText("");
                    }
                }
            }
        });
    }

    private void exibirTelaUsuarioCadastrado() {
        setTitle("Estante de Livros");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel superior com o título
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelTitulo = new JLabel("Estante de Livros");
        labelTitulo.setFont(new Font("Serif", Font.BOLD, 24));
        panelTitulo.add(labelTitulo);

        // Painel central com a lista de livros
        JPanel panelCentral = new JPanel(new BorderLayout());
        JPanel panelLivros = new JPanel(new BorderLayout());
        textAreaLivros = new JTextArea();
        textAreaLivros.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaLivros);
        panelLivros.add(new JLabel("Estante de Livros:"), BorderLayout.NORTH);
        panelLivros.add(scrollPane, BorderLayout.CENTER);

        panelCentral.add(panelLivros, BorderLayout.CENTER);

        add(panelTitulo, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        setVisible(true);

        // Define o comportamento de fechamento da janela
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salvarLivros();
            }
        });

        try {
            Collection<DadosDosLivros> dadosDosLivrosRecuperados = gravador.recuperaLivro();
            interfaceDeLivros.setLivross(dadosDosLivrosRecuperados);
            exibirLivrosCadastrados(dadosDosLivrosRecuperados);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Problema ao recuperar os livros");
            e.printStackTrace();
        }
    }


    private void exibirTelaFuncionario() {
        setTitle("Estante de Livros");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel superior com o título
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labelTitulo = new JLabel("Estante de Livros");
        labelTitulo.setFont(new Font("Serif", Font.BOLD, 24));
        panelTitulo.add(labelTitulo);

        // Painel central com o formulário e a lista de livros
        JPanel panelCentral = new JPanel(new BorderLayout());
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Labels e campos de texto para o formulário
        JLabel labelNomeLivro = new JLabel("Nome do Livro:");
        textFieldNomeLivro = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(labelNomeLivro, gbc);
        gbc.gridx = 1;
        panelFormulario.add(textFieldNomeLivro, gbc);

        JLabel labelNomeAutor = new JLabel("Nome do Autor:");
        textFieldNomeAutor = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(labelNomeAutor, gbc);
        gbc.gridx = 1;
        panelFormulario.add(textFieldNomeAutor, gbc);

        JLabel labelDiaDeixado = new JLabel("Data de Hoje:");
        textFieldDiaDeixado = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(labelDiaDeixado, gbc);
        gbc.gridx = 1;
        panelFormulario.add(textFieldDiaDeixado, gbc);

        ImageIcon adicionar = new ImageIcon("C:\\Users\\naelb\\Downloads\\EstanteDeLivros-20230607T163914Z-001\\EstanteDeLivros\\Dados\\livro.png");
        ImageIcon pesquisar = new ImageIcon("C:\\Users\\naelb\\Downloads\\EstanteDeLivros-20230607T163914Z-001\\EstanteDeLivros\\Dados\\lendo-um-livro.png");
        ImageIcon remover = new ImageIcon("C:\\Users\\naelb\\Downloads\\EstanteDeLivros-20230607T163914Z-001\\EstanteDeLivros\\Dados\\remover.png");

        // Botões de ação
        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton buttonCadastrar = new JButton("Cadastrar", adicionar);
        JButton buttonPesquisar = new JButton("Pesquisar", pesquisar);
        JButton buttonRemover = new JButton("Remover", remover);
        panelBotoes.add(buttonCadastrar);
        panelBotoes.add(buttonPesquisar);
        panelBotoes.add(buttonRemover);

        // Painel para a lista de livros
        JPanel panelLivros = new JPanel(new BorderLayout());
        textAreaLivros = new JTextArea();
        textAreaLivros.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaLivros);
        panelLivros.add(new JLabel("Estante de Livros:"), BorderLayout.NORTH);
        panelLivros.add(scrollPane, BorderLayout.CENTER);

        panelCentral.add(panelFormulario, BorderLayout.NORTH);
        panelCentral.add(panelLivros, BorderLayout.CENTER);

        add(panelTitulo, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
        setVisible(true);

        //identification o fechamento da dela para salvar
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salvarLivros();
            }
        });

        buttonCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarLivro();
            }
        });

        buttonPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pesquisarLivro();
            }
        });

        buttonRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerLivro();
            }
        });

        try {
            Collection<DadosDosLivros> dadosDosLivrosRecuperados = gravador.recuperaLivro();
            interfaceDeLivros.setLivross(dadosDosLivrosRecuperados);
            exibirLivrosCadastrados(dadosDosLivrosRecuperados);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Problema ao recuperar os livros");
            e.printStackTrace();
        }
    }

    private void cadastrarLivro() {
        String nomeLivro = textFieldNomeLivro.getText();
        String nomeAutor = textFieldNomeAutor.getText();
        String diaDeixadoLivro = textFieldDiaDeixado.getText();

        boolean cadastrou = interfaceDeLivros.cadastraLivros(nomeLivro, nomeAutor, diaDeixadoLivro);
        if (cadastrou) {
            JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso.");
            exibirLivrosCadastrados(interfaceDeLivros.getLivros());
        } else {
            JOptionPane.showMessageDialog(this, "Livro não foi cadastrado.\nVerifique se o livro já não existe.");
        }
        textFieldNomeLivro.setText("");
        textFieldNomeAutor.setText("");
        textFieldDiaDeixado.setText("");
    }

    private void pesquisarLivro() {
        String nomeLivro = textFieldNomeLivro.getText();
        String nomeAutor = textFieldNomeAutor.getText();

        Collection<DadosDosLivros> livrosEncontrados = interfaceDeLivros.pesquisaLivros(nomeLivro, nomeAutor);
        if (!livrosEncontrados.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Livros encontrados:\n");
            for (DadosDosLivros livro : livrosEncontrados) {
                sb.append(livro).append("\n");
            }
            textAreaLivros.setText(sb.toString());
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum livro encontrado.");
            textAreaLivros.setText("");
        }

    }

    private void removerLivro() {
        String nomeAutor = textFieldNomeAutor.getText();

        boolean removido = interfaceDeLivros.removeLivro(nomeAutor);
        if (removido) {
            JOptionPane.showMessageDialog(this, "Livro removido com sucesso.");
            exibirLivrosCadastrados(interfaceDeLivros.getLivros());
        } else {
            JOptionPane.showMessageDialog(this, "Livro não encontrado.\nOperação não realizada.");
        }

    }

    private void salvarLivros() {
        try {
            gravador.gravaLivro(interfaceDeLivros.getLivros());
            JOptionPane.showMessageDialog(this, "Livros salvos com sucesso.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Problema ao gravar os livros.");
            e.printStackTrace();
        }
    }

    private void exibirLivrosCadastrados(Collection<DadosDosLivros> livros) {
        StringBuilder sb = new StringBuilder();
        for (DadosDosLivros livro : livros) {
            sb.append(livro).append("\n");
        }
        textAreaLivros.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new EstanteDeLivrosMenu();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}