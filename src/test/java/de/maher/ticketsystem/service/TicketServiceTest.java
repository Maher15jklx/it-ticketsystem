package de.maher.ticketsystem.service;

import de.maher.ticketsystem.exception.TicketNotFoundException;
import de.maher.ticketsystem.model.Prioritaet;
import de.maher.ticketsystem.model.Ticket;
import de.maher.ticketsystem.model.TicketStatus;
import de.maher.ticketsystem.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TicketServiceTest {
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        ticketService = new TicketService(new TicketRepository());
    }

    @Test
    void erstelleTicketLegtTicketMitOffenemStatusAn() {
        Ticket ticket = ticketService.erstelleTicket("Drucker defekt", "Papier wird nicht eingezogen", Prioritaet.HOCH);

        assertEquals(1, ticket.getId());
        assertEquals("Drucker defekt", ticket.getTitel());
        assertEquals(TicketStatus.OFFEN, ticket.getStatus());
        assertEquals(Prioritaet.HOCH, ticket.getPrioritaet());
        assertEquals(1, ticketService.findeAlleTickets().size());
    }

    @Test
    void loescheTicketEntferntTicket() {
        Ticket ticket = ticketService.erstelleTicket("VPN", "VPN verbindet nicht", Prioritaet.MITTEL);

        ticketService.loescheTicket(ticket.getId());

        assertEquals(0, ticketService.findeAlleTickets().size());
        assertThrows(TicketNotFoundException.class, () -> ticketService.findeTicketNachId(ticket.getId()));
    }

    @Test
    void aendereStatusAktualisiertTicketStatus() {
        Ticket ticket = ticketService.erstelleTicket("E-Mail", "Postfach voll", Prioritaet.NIEDRIG);

        Ticket aktualisiert = ticketService.aendereStatus(ticket.getId(), TicketStatus.IN_BEARBEITUNG);

        assertEquals(TicketStatus.IN_BEARBEITUNG, aktualisiert.getStatus());
    }

    @Test
    void filtereNachStatusGibtNurPassendeTicketsZurueck() {
        Ticket ticket1 = ticketService.erstelleTicket("Monitor", "Bild flackert", Prioritaet.MITTEL);
        ticketService.erstelleTicket("Maus", "Linksklick defekt", Prioritaet.NIEDRIG);
        ticketService.aendereStatus(ticket1.getId(), TicketStatus.ERLEDIGT);

        List<Ticket> erledigteTickets = ticketService.filtereNachStatus(TicketStatus.ERLEDIGT);

        assertEquals(1, erledigteTickets.size());
        assertEquals(ticket1.getId(), erledigteTickets.getFirst().getId());
    }

    @Test
    void ungueltigeIdWirftException() {
        assertThrows(TicketNotFoundException.class, () -> ticketService.findeTicketNachId(999));
        assertThrows(TicketNotFoundException.class, () -> ticketService.loescheTicket(999));
        assertThrows(TicketNotFoundException.class,
                () -> ticketService.aendereStatus(999, TicketStatus.ERLEDIGT));
    }
}
