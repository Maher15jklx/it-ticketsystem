package de.maher.ticketsystem.model;

import java.time.LocalDateTime;

public class Ticket {
    private final int id;
    private String titel;
    private String beschreibung;
    private TicketStatus status;
    private Prioritaet prioritaet;
    private final LocalDateTime erstelltAm;
    private LocalDateTime aktualisiertAm;

    public Ticket(int id, String titel, String beschreibung, TicketStatus status, Prioritaet prioritaet,
                  LocalDateTime erstelltAm, LocalDateTime aktualisiertAm) {
        this.id = id;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.status = status;
        this.prioritaet = prioritaet;
        this.erstelltAm = erstelltAm;
        this.aktualisiertAm = aktualisiertAm;
    }

    public int getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public Prioritaet getPrioritaet() {
        return prioritaet;
    }

    public void setPrioritaet(Prioritaet prioritaet) {
        this.prioritaet = prioritaet;
    }

    public LocalDateTime getErstelltAm() {
        return erstelltAm;
    }

    public LocalDateTime getAktualisiertAm() {
        return aktualisiertAm;
    }

    public void setAktualisiertAm(LocalDateTime aktualisiertAm) {
        this.aktualisiertAm = aktualisiertAm;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", titel='" + titel + '\'' +
                ", beschreibung='" + beschreibung + '\'' +
                ", status=" + status +
                ", prioritaet=" + prioritaet +
                ", erstelltAm=" + erstelltAm +
                ", aktualisiertAm=" + aktualisiertAm +
                '}';
    }
}
