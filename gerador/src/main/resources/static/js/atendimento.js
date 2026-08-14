document.addEventListener("DOMContentLoaded", () => {

    const guiche = document.getElementById("guiche");
    const buttons = document.querySelectorAll(".btn-call[data-tipo]");
    const senhaNormal = document.getElementById("senhaNormal");
    const senhaPreferencial = document.getElementById("senhaPreferencial");
    const statusNormal = document.getElementById("statusNormal");
    const statusPreferencial = document.getElementById("statusPreferencial");
    const historyList = document.getElementById("historyList");
    const historyCount = document.querySelector(".history-count span");

    if (!guiche || !buttons.length) {
        return;
    }

    function animarSenha(elemento) {
        if (!elemento) return;

        if (typeof gsap !== "undefined") {
            gsap.fromTo(
                elemento,
                { scale: .65, opacity: .35 },
                {
                    scale: 1,
                    opacity: 1,
                    duration: .65,
                    ease: "back.out(1.7)"
                }
            );
        } else {
            elemento.classList.remove("ticket-updated");
            void elemento.offsetWidth;
            elemento.classList.add("ticket-updated");
        }
    }

    function atualizarStatus(elemento, tipo, existe) {
        if (!elemento) return;

        elemento.classList.toggle("muted", !existe);

        if (tipo === "NORMAL") {
            elemento.innerHTML = existe
                ? '<span class="ticket-status-dot"></span><span>Em atendimento</span>'
                : '<span>Nenhuma senha normal em atendimento</span>';
        } else {
            elemento.innerHTML = existe
                ? '<span class="preferential-dot"></span><span>Atendimento prioritário</span>'
                : '<span>Nenhuma senha preferencial em atendimento</span>';
        }
    }

    function atualizarHistorico(historico) {
        if (!historyList) return;

        historyList.innerHTML = "";

        if (!historico || historico.length === 0) {
            historyList.innerHTML = `
                <div class="empty-history">
                    <div class="empty-icon">—</div>
                    <h3>Nenhuma chamada</h3>
                    <p>O histórico aparecerá aqui após a primeira chamada.</p>
                </div>
            `;
        } else {
            historico.forEach((senha, index) => {
                const item = document.createElement("div");
                item.className = "history-item";

                const position = document.createElement("div");
                position.className = "history-position";
                position.textContent = index + 1;

                const ticket = document.createElement("div");
                ticket.className = "history-ticket";
                ticket.textContent = senha.numero;

                const type = document.createElement("div");
                type.className = "history-type";
                if (senha.tipo === "PREFERENCIAL") {
                    type.classList.add("history-preferential");
                }
                type.textContent = senha.tipo;

                const guicheElement = document.createElement("div");
                guicheElement.className = "history-guiche";
                guicheElement.innerHTML = "<span>Guichê</span>";

                const guicheStrong = document.createElement("strong");
                guicheStrong.textContent = senha.guiche;
                guicheElement.appendChild(guicheStrong);

                item.append(position, ticket, type, guicheElement);
                historyList.appendChild(item);
            });
        }

        if (historyCount) {
            historyCount.textContent = historico ? historico.length : 0;
        }
    }

    // Animação inicial da página.
    if (typeof gsap !== "undefined") {
        const intro = gsap.timeline({
            defaults: { ease: "power3.out" }
        });

        intro
            .from(".dashboard-header", { y: -25, opacity: 0, duration: .7 })
            .from(".current-ticket-card", {
                x: -30,
                opacity: 0,
                duration: .6,
                stagger: .12
            }, "-=.35")
            .from(".history-card", {
                x: 40,
                opacity: 0,
                duration: .7
            }, "-=.5")
            .from(".quick-info .info-item", {
                y: 20,
                opacity: 0,
                stagger: .1,
                duration: .4
            }, "-=.35");

        gsap.from(".history-item", {
            x: 25,
            opacity: 0,
            duration: .45,
            stagger: .08,
            delay: .55,
            ease: "power2.out"
        });

        document.querySelectorAll(".ticket-number").forEach(ticket => {
            if (ticket.textContent.trim() !== "--") {
                gsap.fromTo(ticket,
                    { scale: .75, opacity: 0 },
                    { scale: 1, opacity: 1, duration: 1, delay: .4, ease: "back.out(1.7)" }
                );
            }
        });
    }

    buttons.forEach(button => {
        button.addEventListener("click", async () => {
            if (button.disabled) return;

            const tipo = button.dataset.tipo;
            const numeroGuiche = guiche.value;

            button.disabled = true;

            if (typeof gsap !== "undefined") {
                gsap.to(button, { scale: .97, duration: .12 });
            }

            try {
                const response = await fetch(
                    `/atendimento/chamar?guiche=${encodeURIComponent(numeroGuiche)}&tipo=${encodeURIComponent(tipo)}`,
                    {
                        method: "POST",
                        headers: {
                            "Accept": "application/json"
                        }
                    }
                );

                if (!response.ok) {
                    throw new Error(`Erro HTTP: ${response.status}`);
                }

                const dados = await response.json();

                // Atualiza somente a senha normal.
                if (dados.senhaNormalAtual) {
                    senhaNormal.textContent = dados.senhaNormalAtual.numero;
                    animarSenha(senhaNormal);
                    atualizarStatus(statusNormal, "NORMAL", true);
                }

                // Atualiza somente a senha preferencial.
                if (dados.senhaPreferencialAtual) {
                    senhaPreferencial.textContent = dados.senhaPreferencialAtual.numero;
                    animarSenha(senhaPreferencial);
                    atualizarStatus(statusPreferencial, "PREFERENCIAL", true);
                }

                // Atualiza o histórico sem recarregar a página.
                atualizarHistorico(dados.historico);

                if (typeof gsap !== "undefined") {
                    gsap.to(button, { scale: 1, duration: .2 });
                }

                // Libera o botão novamente para a próxima chamada.
                button.disabled = false;

            } catch (error) {
                console.error(error);
                button.disabled = false;

                if (typeof gsap !== "undefined") {
                    gsap.to(button, { scale: 1, duration: .2 });
                    gsap.fromTo(button,
                        { x: -8 },
                        {
                            x: 8,
                            duration: .08,
                            repeat: 5,
                            yoyo: true,
                            ease: "power1.inOut",
                            clearProps: "x"
                        }
                    );
                }

                alert("Não foi possível chamar a senha. Tente novamente.");
            }
        });

        if (typeof gsap !== "undefined") {
            button.addEventListener("mouseenter", () => {
                if (!button.disabled) gsap.to(button, { y: -2, duration: .2 });
            });

            button.addEventListener("mouseleave", () => {
                gsap.to(button, { y: 0, duration: .2 });
            });
        }
    });
});
