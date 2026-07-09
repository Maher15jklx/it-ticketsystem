package de.maher.ticketsystem.exception;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(int id) {
        super("Kein Ticket mit der ID " + id + " gefunden.");
    }
}
