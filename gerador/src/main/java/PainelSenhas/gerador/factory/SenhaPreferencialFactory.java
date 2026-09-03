package PainelSenhas.gerador.factory;

import PainelSenhas.gerador.model.Senha;
import PainelSenhas.gerador.model.TipoSenha;

public class SenhaPreferencialFactory implements SenhaFactory {

    private static final String PREFIXO = "P";

    @Override
    public Senha criarSenha(int numeroSequencial) {
        String identificador = String.format(PREFIXO + "%03d", numeroSequencial);
        return new Senha(identificador, TipoSenha.PREFERENCIAL);
    }

    @Override
    public TipoSenha getTipo() {
        return TipoSenha.PREFERENCIAL;
    }
}
