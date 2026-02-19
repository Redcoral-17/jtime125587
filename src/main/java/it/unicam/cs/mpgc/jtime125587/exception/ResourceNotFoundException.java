package it.unicam.cs.mpgc.jtime125587.exception;

/**
 * Eccezione lanciata quando una risorsa richiesta non viene trovata.
 * Mappata a HTTP 404 NOT FOUND dal GlobalExceptionHandler.
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Costruttore con messaggio di errore.
     *
     * @param message il messaggio che descrive la risorsa non trovata
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

