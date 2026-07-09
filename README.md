# IT-Ticketsystem

Kleine Konsolenanwendung in Java 21, die ein einfaches IT-Ticketsystem ohne Datenbank umsetzt. Die Daten werden nur im Arbeitsspeicher gehalten und gehen beim Beenden des Programms verloren.

## Funktionen

- Ticket erstellen
- Alle Tickets anzeigen
- Einzelnes Ticket per ID anzeigen
- Ticket bearbeiten
- Ticket loeschen
- Ticketstatus aendern
- Tickets nach Status filtern
- Tickets nach Prioritaet filtern
- Offene Tickets zaehlen
- Programm ueber das Menue beenden

## Startanleitung

Voraussetzungen:

- Java 21 oder neuer
- Maven

Projekt kompilieren und Tests ausfuehren:

```bash
mvn test
```

Anwendung starten:

```bash
mvn exec:java
```

Alternativ kann die `Main`-Klasse direkt aus einer IDE gestartet werden:

```text
de.maher.ticketsystem.Main
```

## Projektstruktur

```text
src/main/java/de/maher/ticketsystem
├── Main.java                    Konsolenmenue und Benutzereingaben
├── exception
│   └── TicketNotFoundException.java
├── model
│   ├── Prioritaet.java
│   ├── Ticket.java
│   └── TicketStatus.java
├── repository
│   └── TicketRepository.java    Speicherung im Arbeitsspeicher
└── service
    └── TicketService.java       Validierung und Geschaeftslogik
```

Die UI kennt nur den `TicketService`. Der Service kapselt Validierung und Geschaeftslogik. Das Repository speichert die Tickets in einer `LinkedHashMap` und erzeugt automatisch fortlaufende IDs.

## Beispiel

```text
=== IT-Ticketsystem ===
1. Ticket erstellen
2. Alle Tickets anzeigen
...
Auswahl: 1
Titel: Laptop startet nicht
Beschreibung: Beim Einschalten bleibt der Bildschirm schwarz.
Prioritaet auswaehlen:
1. NIEDRIG
2. MITTEL
3. HOCH
Auswahl: 3

Ticket wurde erstellt:
ID: 1
Titel: Laptop startet nicht
Status: OFFEN
Prioritaet: HOCH
```

Danach kann das Ticket ueber die ID angezeigt, bearbeitet, geloescht oder in einen anderen Status gesetzt werden.
