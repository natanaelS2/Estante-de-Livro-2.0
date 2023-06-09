package Estante.Livros.ufpb.br;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SalvarContas {
    private final String pastaDados = "Dados";
    private final String arquivoContas = pastaDados + File.separator + "contasUsuarios.txt";

    public void salvarContas(Map<String, String> contas) throws IOException {
        criarPastaDados();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoContas))) {
            for (Map.Entry<String, String> entry : contas.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        }
    }

    public Map<String, String> carregarContas() throws IOException {
        Map<String, String> contas = new HashMap<>();
        File file = new File(arquivoContas);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivoContas))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        contas.put(parts[0], parts[1]);
                    }
                }
            }
        }
        return contas;
    }

    private void criarPastaDados() {
        File pasta = new File(pastaDados);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
    }
}
