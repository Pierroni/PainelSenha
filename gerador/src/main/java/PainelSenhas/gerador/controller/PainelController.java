package PainelSenhas.gerador.controller;

import PainelSenhas.gerador.service.GerenciadorSenhasService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PainelController {

    private final GerenciadorSenhasService gerenciador;

    public PainelController() {
        this.gerenciador = GerenciadorSenhasService.getInstancia();
    }

    @GetMapping("/painel/api")
    @ResponseBody
    public Map<String, Object> estadoPainel() {
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("senhaNormalAtual", gerenciador.getSenhaNormalAtual());
        resposta.put("senhaPreferencialAtual", gerenciador.getSenhaPreferencialAtual());
        resposta.put("historico", gerenciador.getHistorico());
        return resposta;
    }

    @GetMapping("/painel")
    public String painel(Model model) {
        model.addAttribute("senhaAtual", gerenciador.getSenhaAtual());
        model.addAttribute("senhaNormalAtual", gerenciador.getSenhaNormalAtual());
        model.addAttribute("senhaPreferencialAtual", gerenciador.getSenhaPreferencialAtual());
        model.addAttribute("historico", gerenciador.getHistorico());

        return "painel";
    }
}
