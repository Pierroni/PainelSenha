package PainelSenhas.gerador.controller;

import PainelSenhas.gerador.model.Senha;
import PainelSenhas.gerador.model.TipoSenha;
import PainelSenhas.gerador.service.GerenciadorSenhasService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AtendimentoController {

    private final GerenciadorSenhasService gerenciador;

    public AtendimentoController() {
        this.gerenciador = GerenciadorSenhasService.getInstancia();
    }

    @GetMapping("/atendimento")
    public String atendimento(Model model) {
        model.addAttribute("senhaAtual", gerenciador.getSenhaAtual());
        model.addAttribute("senhaNormalAtual", gerenciador.getSenhaNormalAtual());
        model.addAttribute("senhaPreferencialAtual", gerenciador.getSenhaPreferencialAtual());
        model.addAttribute("historico", gerenciador.getHistorico());

        return "Atendimento";
    }

    @PostMapping("/atendimento/chamar")
    @ResponseBody
    public Map<String, Object> chamarSenha(
            @RequestParam int guiche,
            @RequestParam(defaultValue = "NORMAL") TipoSenha tipo
    ) {
        Senha senha = gerenciador.gerarSenha(tipo);
        gerenciador.chamarSenha(senha, guiche);

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("senhaChamada", senha);
        resposta.put("senhaNormalAtual", gerenciador.getSenhaNormalAtual());
        resposta.put("senhaPreferencialAtual", gerenciador.getSenhaPreferencialAtual());
        resposta.put("historico", gerenciador.getHistorico());

        return resposta;
    }
}
