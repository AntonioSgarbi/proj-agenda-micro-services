package aula.agenda.msagenda.repository;

import aula.agenda.msagenda.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    Optional<Agenda> findAgendaByDataHoraReservaAndSala(LocalDateTime data, Long id);
}
