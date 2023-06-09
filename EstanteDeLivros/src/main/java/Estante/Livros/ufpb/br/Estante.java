package Estante.Livros.ufpb.br;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Estante implements InterfaceDeLivros {

    private final Map<String, DadosDosLivros> LivroMap;

    public Estante(){
        this.LivroMap = new HashMap<>();
    }

    @Override
    public boolean cadastraLivros(String nomeLivro, String nomeAutor, String diaDeixadoLivro) {
        if (!this.LivroMap.containsKey(nomeAutor)) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setLenient(false);
                Date dataDeixadoLivro = dateFormat.parse(diaDeixadoLivro);

                Calendar calendarAtual = Calendar.getInstance();
                Calendar calendarLivro = Calendar.getInstance();
                calendarLivro.setTime(dataDeixadoLivro);

                int diaAtual = calendarAtual.get(Calendar.DAY_OF_MONTH);
                int mesAtual = calendarAtual.get(Calendar.MONTH) + 1; // +1 pois o mês começa em 0
                int anoAtual = calendarAtual.get(Calendar.YEAR);

                int diaLivro = calendarLivro.get(Calendar.DAY_OF_MONTH);
                int mesLivro = calendarLivro.get(Calendar.MONTH) + 1; // +1 pois o mês começa em 0
                int anoLivro = calendarLivro.get(Calendar.YEAR);

                if (diaLivro == diaAtual && mesLivro == mesAtual && anoLivro == anoAtual) {
                    this.LivroMap.put(nomeAutor, new DadosDosLivros(nomeLivro, nomeAutor, diaDeixadoLivro));
                    return true;
                } else {
                    return false;
                }
            } catch (ParseException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Collection<DadosDosLivros> pesquisaLivros(String nomeLivro, String NomeAutor) {
        Collection<DadosDosLivros> dadosDosLivros = new ArrayList<>();
        for (DadosDosLivros p : this.LivroMap.values()){
            if (p.getNomeDoLivro().equals(nomeLivro) && p.getNomeAutor().equals(NomeAutor)){
                dadosDosLivros.add(p);
            }
        }
        return dadosDosLivros;
    }

    @Override
    public boolean removeLivro(String NomeAutor){
        if (this.LivroMap.containsKey(NomeAutor)){
            this.LivroMap.remove(NomeAutor);
            return true;
        } else {
            return false;
        }
    }

    public Collection<DadosDosLivros> getLivros(){
        return this.LivroMap.values();
    }

    public void setLivross(Collection<DadosDosLivros> dadosDosLivros) {
        for (DadosDosLivros p : dadosDosLivros) {
            this.LivroMap.put(p.getNomeAutor(), p);
        }
    }
}

