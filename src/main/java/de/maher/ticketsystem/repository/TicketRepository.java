package de.maher.ticketsystem.repository;

import de.maher.ticketsystem.model.Ticket;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TicketRepository {
    private final Map<Integer, Ticket> tickets = new LinkedHashMap<>();
    private int naechsteId = 1;

    public int generateId() {
        return naechsteId++;
    }

    public Ticket save(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
        return ticket;
    }

    public List<Ticket> findAll() {
        return new ArrayList<>(tickets.values());
    }

    public Optional<Ticket> findById(int id) {
        return Optional.ofNullable(tickets.get(id));
    }

    public boolean deleteById(int id) {
        return tickets.remove(id) != null;
    }
}
