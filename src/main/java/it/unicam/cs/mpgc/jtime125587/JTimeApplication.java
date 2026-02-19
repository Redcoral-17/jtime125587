package it.unicam.cs.mpgc.jtime125587;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale dell'applicazione Spring Boot per JTime.
 * Avvia il server REST API sulla porta 8080 di default.
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
@SpringBootApplication
public class JTimeApplication {

    /**
     * Punto d'ingresso dell'applicazione Spring Boot.
     *
     * @param args argomenti della linea di comando
     */
    public static void main(String[] args) {
        SpringApplication.run(JTimeApplication.class, args);
    }
}

