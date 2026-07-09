package de.maher.ticketsystem.service;

import de.maher.ticketsystem.exception.TicketNotFoundException;
import de.maher.ticketsystem.model.Prioritaet;
import de.maher.ticketsystem.model.Ticket;
import de.maher.ticketsystem.model.TicketStatus;
import de.maher.ticketsystem.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = Objects.requireNonNull(ticketRepository);
    }

    public Ticket erstelleTicket(String titel, String beschreibung, Prioritaet prioritaet) {
        validiereText(titel, "Titel");
        validiereText(beschreibung, "Beschreibung");
        Objects.requireNonNull(prioritaet, "Prioritaet darf nicht null sein.");

        LocalDateTime jetzt = LocalDateTime.now();
        Ticket ticket = new Ticket(
                ticketRepository.generateId(),
                titel.trim(),
                beschreibung.trim(),
                TicketStatus.OFFEN,
                prioritaet,
                jetzt,
                jetzt
        );

        return ticketRepository.save(ticket);
    }

    public List<Ticket> findeAlleTickets() {
        return ticketRepository.findAll();
    }

    public Ticket findeTicketNachId(int id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    public Ticket bearbeiteTicket(int id, String titel, String beschreibung, Prioritaet prioritaet) {
        validiereText(titel, "Titel");
        validiereText(beschreibung, "Beschreibung");
        Objects.requireNonNull(prioritaet, "Prioritaet darf nicht null sein.");

        Ticket ticket = findeTicketNachId(id);
        ticket.setTitel(titel.trim());
        ticket.setBeschreibung(beschreibung.trim());
        ticket.setPrioritaet(prioritaet);
        ticket.setAktualisiertAm(LocalDateTime.now());
        return ticket;
    }

    public void loescheTicket(int id) {
        if (!ticketRepository.deleteById(id)) {
            throw new TicketNotFoundException(id);
        }
    }

    public Ticket aendereStatus(int id, TicketStatus neuerStatus) {
        Objects.requireNonNull(neuerStatus, "Status darf nicht null sein.");

        Ticket ticket = findeTicketNachId(id);
        ticket.setStatus(neuerStatus);
        ticket.setAktualisiertAm(LocalDateTime.now());
        return ticket;
    }

    public List<Ticket> filtereNachStatus(TicketStatus status) {
        Objects.requireNonNull(status, "Status darf nicht null sein.");

        return ticketRepository.findAll().stream()
                .filter(ticket -> ticket.getStatus() == status)
                .toList();
    }

    public List<Ticket> filtereNachPrioritaet(Prioritaet prioritaet) {
        Objects.requireNonNull(prioritaet, "Prioritaet darf nicht null sein.");

        return ticketRepository.findAll().stream()
                .filter(ticket -> ticket.getPrioritaet() == prioritaet)
                .toList();
    }

    public long zaehleOffeneTickets() {
        return ticketRepository.findAll().stream()
                .filter(ticket -> ticket.getStatus() == TicketStatus.OFFEN)
                .count();
    }

    private void validiereText(String wert, String feldname) {
        if (wert == null || wert.trim().isEmpty()) {
            throw new IllegalArgumentException(feldname + " darf nicht leer sein.");
        }
    }
}
