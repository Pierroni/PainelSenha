package PainelSenhas.gerador.factory;

import PainelSenhas.gerador.model.Senha;
import PainelSenhas.gerador.model.TipoSenha;


public class SenhaNormalFactory implements SenhaFactory {

    private static final String PREFIXO = "N";

    @Override
    public Senha criarSenha(int numeroSequencial) {
        String identificador = String.format(PREFIXO + "%03d", numeroSequencial);
        return new Senha(identificador, TipoSenha.NORMAL);
    }

    @Override
    public TipoSenha getTipo() {
        return TipoSenha.NORMAL;
    }
}
