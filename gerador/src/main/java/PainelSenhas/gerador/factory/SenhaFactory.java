package PainelSenhas.gerador.factory;

import PainelSenhas.gerador.model.Senha;
import PainelSenhas.gerador.model.TipoSenha;

public interface SenhaFactory {

    Senha criarSenha(int numeroSequencial);

    TipoSenha getTipo();
}
