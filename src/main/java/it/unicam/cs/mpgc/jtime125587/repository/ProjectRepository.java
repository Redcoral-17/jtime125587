package it.unicam.cs.mpgc.jtime125587.repository;

import it.unicam.cs.mpgc.jtime125587.model.Project;
import it.unicam.cs.mpgc.jtime125587.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository Spring Data JPA per l'entità Project.
 * Fornisce operazioni CRUD automatiche e query personalizzate.
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Trova tutti i progetti con uno specifico status.
     *
     * @param status lo status da cercare
     * @return lista di progetti con lo status specificato
     */
    List<Project> findByStatus(Status status);

    /**
     * Trova un progetto per nome.
     *
     * @param name il nome del progetto
     * @return il progetto con il nome specificato, se esiste
     */
    Project findByName(String name);
}

