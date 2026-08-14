package PainelSenhas.gerador.model;

import lombok.Setter;

public class Senha {

    private String numero;
    private TipoSenha tipo;
    @Setter
    private String status;
    @Setter
    private int guiche;

    public Senha(String numero, TipoSenha tipo) {
        this.numero = numero;
        this.tipo = tipo;
        this.status = "AGUARDANDO";
    }

    public String getNumero() {
        return numero;
    }

    public TipoSenha getTipo() {
        return tipo;
    }

    public String getStatus() {
        return status;
    }

    public int getGuiche() {
        return guiche;
    }

}