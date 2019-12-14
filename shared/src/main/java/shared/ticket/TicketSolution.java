package shared.ticket;

public class TicketSolution {
    private Ticket ticket;
    private String solution;

    public TicketSolution(Ticket ticket, String solution) {
        this.ticket = ticket;
        this.solution = solution;
    }

    public TicketSolution() {
    }

    public Ticket getTicket() {
        return ticket;
    }

    public String getSolution() {
        return solution;
    }
}
