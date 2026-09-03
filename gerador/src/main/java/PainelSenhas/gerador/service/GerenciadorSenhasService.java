package PainelSenhas.gerador.service;

import PainelSenhas.gerador.factory.SenhaFactory;
import PainelSenhas.gerador.factory.SenhaFactoryProvider;
import PainelSenhas.gerador.model.Senha;
import PainelSenhas.gerador.model.TipoSenha;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GerenciadorSenhasService {

    private static GerenciadorSenhasService instancia;

    private int numeroNormal;
    private int numeroPreferencial;

    @Getter
    private Senha senhaAtual;

    @Getter
    private Senha senhaNormalAtual;

    @Getter
    private Senha senhaPreferencialAtual;

    private final List<Senha> historico;

    private GerenciadorSenhasService() {
        numeroNormal = 0;
        numeroPreferencial = 0;
        historico = new ArrayList<>();
    }

    public static synchronized GerenciadorSenhasService getInstancia() {
        if (instancia == null) {
            instancia = new GerenciadorSenhasService();
        }
        return instancia;
    }

    public synchronized Senha gerarSenha(TipoSenha tipo) {
        int numero = (tipo == TipoSenha.NORMAL) ? ++numeroNormal : ++numeroPreferencial;


        SenhaFactory factory = SenhaFactoryProvider.getFactory(tipo);

        return factory.criarSenha(numero);
    }

    public synchronized void chamarSenha(Senha senha, int guiche) {
        senha.setGuiche(guiche);
        senha.setStatus("EM_ATENDIMENTO");

        senhaAtual = senha;

        if (senha.getTipo() == TipoSenha.NORMAL) {
            senhaNormalAtual = senha;
        } else {
            senhaPreferencialAtual = senha;
        }
        historico.add(0, senha);

        if (historico.size() > 20) {
            historico.remove(historico.size() - 1);
        }
    }

    public List<Senha> getHistorico() {
        return Collections.unmodifiableList(historico);
    }

    public synchronized void resetar() {
        numeroNormal = 0;
        numeroPreferencial = 0;
        senhaAtual = null;
        senhaNormalAtual = null;
        senhaPreferencialAtual = null;
        historico.clear();
    }
}
