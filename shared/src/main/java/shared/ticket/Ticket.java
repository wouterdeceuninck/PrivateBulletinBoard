package shared.ticket;

import java.util.Objects;

public class Ticket {
    private String timeStamp;
    private String puzzle;

    public Ticket(String timeStamp, String puzzle) {
        this.timeStamp = timeStamp;
        this.puzzle = puzzle;
    }

    public Ticket() {
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getPuzzle() {
        return puzzle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return timeStamp.equals(ticket.timeStamp) &&
                puzzle.equals(ticket.puzzle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStamp, puzzle);
    }
}
