package PainelSenhas.gerador.factory;

import PainelSenhas.gerador.model.TipoSenha;

import java.util.EnumMap;
import java.util.Map;

public final class SenhaFactoryProvider {

    private static final Map<TipoSenha, SenhaFactory> FACTORIES = new EnumMap<>(TipoSenha.class);

    static {
        registrar(new SenhaNormalFactory());
        registrar(new SenhaPreferencialFactory());
    }

    private SenhaFactoryProvider() {

    }

    private static void registrar(SenhaFactory factory) {
        FACTORIES.put(factory.getTipo(), factory);
    }

    public static SenhaFactory getFactory(TipoSenha tipo) {
        SenhaFactory factory = FACTORIES.get(tipo);

        if (factory == null) {
            throw new IllegalArgumentException("Não há factory registrada para o tipo de senha: " + tipo);
        }

        return factory;
    }
}
