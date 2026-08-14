document.addEventListener("DOMContentLoaded", () => {

    if (typeof gsap === "undefined") {
        console.warn("GSAP não foi carregado.");
        return;
    }


    /*
     * ANIMAÇÃO INICIAL
     */

    const timeline = gsap.timeline({
        defaults: {
            ease: "power3.out"
        }
    });


    timeline
        .from(".topbar", {
            y: -30,
            opacity: 0,
            duration: .7
        })
        .from(".hero-badge", {
            y: 20,
            opacity: 0,
            duration: .5
        }, "-=.25")
        .from(".hero-title", {
            y: 35,
            opacity: 0,
            duration: .8
        }, "-=.3")
        .from(".hero-description", {
            y: 25,
            opacity: 0,
            duration: .6
        }, "-=.45")
        .from(".menu-card", {
            y: 40,
            opacity: 0,
            stagger: .15,
            duration: .65
        }, "-=.3")
        .from(".home-footer", {
            y: 20,
            opacity: 0,
            duration: .5
        }, "-=.25");


    /*
     * HOVER DOS CARDS
     */

    document
        .querySelectorAll(".menu-card")
        .forEach(card => {

            card.addEventListener("mouseenter", () => {

                gsap.to(card, {
                    scale: 1.015,
                    duration: .3,
                    ease: "power2.out"
                });

            });


            card.addEventListener("mouseleave", () => {

                gsap.to(card, {
                    scale: 1,
                    duration: .3,
                    ease: "power2.out"
                });

            });

        });


    /*
     * PULSO DO STATUS
     */

    gsap.to(".status-dot", {

        scale: 1.35,

        opacity: .55,

        duration: 1,

        repeat: -1,

        yoyo: true,

        ease: "sine.inOut"

    });

});