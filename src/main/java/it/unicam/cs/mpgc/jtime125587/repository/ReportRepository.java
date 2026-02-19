package it.unicam.cs.mpgc.jtime125587.repository;

import it.unicam.cs.mpgc.jtime125587.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository Spring Data JPA per l'entità Report.
 * Fornisce operazioni CRUD automatiche e query personalizzate.
 *
 * @author Filippo Corallini (125587), filippo.corallini@studenti.unicam.it
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    /**
     * Trova un report per nome.
     *
     * @param name il nome del report
     * @return Optional contenente il report se trovato
     */
    Optional<Report> findByName(String name);
}

