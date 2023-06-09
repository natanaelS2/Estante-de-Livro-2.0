package Estante.Livros.ufpb.br;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class GravarLivros {
    private final String pasta = "Dados";
    private final String nomeArquivo = "DadosDosLivros.dat";
    private final String caminhoArquivo;

    public GravarLivros() {
        String diretorioAtual = System.getProperty("user.dir");
        this.caminhoArquivo = diretorioAtual + File.separator + pasta + File.separator + nomeArquivo;
    }

    public void gravaLivro(Collection<DadosDosLivros> dadosDosLivros) throws IOException {
        criarDiretorioSeNaoExistir();
        try (ObjectOutputStream gravarLivros = new ObjectOutputStream(new FileOutputStream(this.caminhoArquivo))) {
            ArrayList<DadosDosLivros> livrosNovos = new ArrayList<>(dadosDosLivros);
            gravarLivros.writeObject(livrosNovos);
        }
    }

    public Collection<DadosDosLivros> recuperaLivro() throws IOException {
        try (ObjectInputStream gravarLivros = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            ArrayList<DadosDosLivros> livrosRecuperados = (ArrayList<DadosDosLivros>) gravarLivros.readObject();
            return livrosRecuperados;
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
    }

    private void criarDiretorioSeNaoExistir() {
        File diretorio = new File(this.pasta);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
    }
}