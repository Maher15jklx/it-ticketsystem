package de.maher.ticketsystem;

import de.maher.ticketsystem.exception.TicketNotFoundException;
import de.maher.ticketsystem.model.Prioritaet;
import de.maher.ticketsystem.model.Ticket;
import de.maher.ticketsystem.model.TicketStatus;
import de.maher.ticketsystem.repository.TicketRepository;
import de.maher.ticketsystem.service.TicketService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private final Scanner scanner;
    private final TicketService ticketService;

    public Main(Scanner scanner, TicketService ticketService) {
        this.scanner = scanner;
        this.ticketService = ticketService;
    }

    public static void main(String[] args) {
        TicketRepository ticketRepository = new TicketRepository();
        TicketService ticketService = new TicketService(ticketRepository);
        new Main(new Scanner(System.in), ticketService).start();
    }

    public void start() {
        boolean laeuft = true;

        while (laeuft) {
            zeigeMenue();
            int auswahl = leseInt("Auswahl: ");

            try {
                laeuft = verarbeiteAuswahl(auswahl);
            } catch (IllegalArgumentException | TicketNotFoundException exception) {
                System.out.println("Fehler: " + exception.getMessage());
            }
        }

        System.out.println("Programm beendet.");
    }

    private boolean verarbeiteAuswahl(int auswahl) {
        switch (auswahl) {
            case 1 -> ticketErstellen();
            case 2 -> ticketsAusgeben(ticketService.findeAlleTickets());
            case 3 -> einzelnesTicketAnzeigen();
            case 4 -> ticketBearbeiten();
            case 5 -> ticketLoeschen();
            case 6 -> ticketStatusAendern();
            case 7 -> nachStatusFiltern();
            case 8 -> nachPrioritaetFiltern();
            case 9 -> offeneTicketsZaehlen();
            case 10 -> {
                return false;
            }
            default -> System.out.println("Ungueltige Menueauswahl. Bitte eine Zahl von 1 bis 10 eingeben.");
        }

        return true;
    }

    private void zeigeMenue() {
        System.out.println();
        System.out.println("=== IT-Ticketsystem ===");
        System.out.println("1. Ticket erstellen");
        System.out.println("2. Alle Tickets anzeigen");
        System.out.println("3. Einzelnes Ticket anhand der ID anzeigen");
        System.out.println("4. Ticket bearbeiten");
        System.out.println("5. Ticket loeschen");
        System.out.println("6. Ticketstatus aendern");
        System.out.println("7. Tickets nach Status filtern");
        System.out.println("8. Tickets nach Prioritaet filtern");
        System.out.println("9. Offene Tickets zaehlen");
        System.out.println("10. Programm beenden");
    }

    private void ticketErstellen() {
        String titel = lesePflichtText("Titel: ");
        String beschreibung = lesePflichtText("Beschreibung: ");
        Prioritaet prioritaet = lesePrioritaet();

        Ticket ticket = ticketService.erstelleTicket(titel, beschreibung, prioritaet);
        System.out.println("Ticket wurde erstellt:");
        ticketAusgeben(ticket);
    }

    private void einzelnesTicketAnzeigen() {
        int id = leseInt("Ticket-ID: ");
        ticketAusgeben(ticketService.findeTicketNachId(id));
    }

    private void ticketBearbeiten() {
        int id = leseInt("Ticket-ID: ");
        String titel = lesePflichtText("Neuer Titel: ");
        String beschreibung = lesePflichtText("Neue Beschreibung: ");
        Prioritaet prioritaet = lesePrioritaet();

        Ticket ticket = ticketService.bearbeiteTicket(id, titel, beschreibung, prioritaet);
        System.out.println("Ticket wurde aktualisiert:");
        ticketAusgeben(ticket);
    }

    private void ticketLoeschen() {
        int id = leseInt("Ticket-ID: ");
        ticketService.loescheTicket(id);
        System.out.println("Ticket wurde geloescht.");
    }

    private void ticketStatusAendern() {
        int id = leseInt("Ticket-ID: ");
        TicketStatus neuerStatus = leseStatus();

        Ticket ticket = ticketService.aendereStatus(id, neuerStatus);
        System.out.println("Status wurde aktualisiert:");
        ticketAusgeben(ticket);
    }

    private void nachStatusFiltern() {
        TicketStatus status = leseStatus();
        ticketsAusgeben(ticketService.filtereNachStatus(status));
    }

    private void nachPrioritaetFiltern() {
        Prioritaet prioritaet = lesePrioritaet();
        ticketsAusgeben(ticketService.filtereNachPrioritaet(prioritaet));
    }

    private void offeneTicketsZaehlen() {
        System.out.println("Offene Tickets: " + ticketService.zaehleOffeneTickets());
    }

    private int leseInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String eingabe = scanner.nextLine().trim();

            try {
                return Integer.parseInt(eingabe);
            } catch (NumberFormatException exception) {
                System.out.println("Bitte eine gueltige ganze Zahl eingeben.");
            }
        }
    }

    private String lesePflichtText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String eingabe = scanner.nextLine().trim();

            if (!eingabe.isEmpty()) {
                return eingabe;
            }

            System.out.println("Eingabe darf nicht leer sein.");
        }
    }

    private TicketStatus leseStatus() {
        return leseEnum("Status", TicketStatus.values());
    }

    private Prioritaet lesePrioritaet() {
        return leseEnum("Prioritaet", Prioritaet.values());
    }

    private <T extends Enum<T>> T leseEnum(String label, T[] werte) {
        while (true) {
            System.out.println(label + " auswaehlen:");
            for (int i = 0; i < werte.length; i++) {
                System.out.printf("%d. %s%n", i + 1, werte[i].name());
            }

            int auswahl = leseInt("Auswahl: ");
            if (auswahl >= 1 && auswahl <= werte.length) {
                return werte[auswahl - 1];
            }

            System.out.println("Ungueltige Auswahl.");
        }
    }

    private void ticketsAusgeben(List<Ticket> tickets) {
        if (tickets.isEmpty()) {
            System.out.println("Keine Tickets gefunden.");
            return;
        }

        tickets.forEach(this::ticketAusgeben);
    }

    private void ticketAusgeben(Ticket ticket) {
        System.out.println("----------------------------------------");
        System.out.println("ID: " + ticket.getId());
        System.out.println("Titel: " + ticket.getTitel());
        System.out.println("Beschreibung: " + ticket.getBeschreibung());
        System.out.println("Status: " + ticket.getStatus());
        System.out.println("Prioritaet: " + ticket.getPrioritaet());
        System.out.println("Erstellt am: " + ticket.getErstelltAm().format(DATE_FORMATTER));
        System.out.println("Aktualisiert am: " + ticket.getAktualisiertAm().format(DATE_FORMATTER));
    }
}
