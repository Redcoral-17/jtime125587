package it.unicam.cs.mpgc.jtime125587.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Rappresenta un'attività (Task) persistente.
 * <p>
 * Questa entità è mappata su una tabella del database tramite JPA.
 * Utilizza Lombok per generare getter, setter e il costruttore senza argomenti.
 */
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table
public class Task {
    /**
     * Identificatore univoco del task generato automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nome descrittivo del task.
     */
    private String name;
    /**
     * Data associata al task.
     */
    private LocalDate date;
    /**
     * Orario di inizio del task.
     */
    private LocalTime startTime;
    /**
     * Orario di fine del task.
     */
    private LocalTime endTime;
    /**
     * Durata precedente del task (può essere utilizzata per storicizzare modifiche).
     */
    private Duration oldDuration;
    /**
     * Durata corrente calcolata tra startTime e endTime.
     */
    private Duration duration;
    /**
     * Stato del task, persistito come stringa.
     */
    @Enumerated(EnumType.STRING)
    private Status status;
    /**
     * Progetto al quale la task appartiene (relazione many-to-one).
     */
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    /**
     * Costruisce un nuovo Task con i parametri forniti.
     *
     * La {@code duration} viene calcolata come la differenza tra {@code startTime} e {@code endTime}.
     * {@code oldDuration} viene inizializzata a {@link Duration#ZERO} e lo {@code status} è impostato su ACTIVE.
     *
     * @param name nome del task
     * @param date data del task
     * @param startTime orario di inizio
     * @param endTime orario di fine
     * @param project progetto associato al task
     */
    public Task(String name, LocalDate date, LocalTime startTime, LocalTime endTime, Project project) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.oldDuration = Duration.ZERO;
        this.duration = Duration.between(startTime, endTime);
        this.status = Status.ACTIVE;
        this.project = project;
    }
}