package application.security.ticket;

import shared.HashFunction;
import shared.ticket.Ticket;
import shared.ticket.TicketSolution;

public class TicketSolver {

    private final HashFunction hashFunction;

    public TicketSolver(HashFunction hashFunction) {
        this.hashFunction = hashFunction;
    }

    public TicketSolution solveTicket(Ticket ticket) {
        Long solution = solve(ticket);
        return new TicketSolution(ticket, Long.toString(solution));
    }

    private Long solve(Ticket ticket) {
        String puzzle = ticket.getPuzzle();
        long puzzleLong = Long.parseLong(puzzle);
        long index = 0L;
        while (true) {
            if (calculatePossibleSolution(puzzleLong, index)) return index;
            index += 1;
        }
    }

    private boolean calculatePossibleSolution(long puzzleLong, long index) {
        long sum = Long.sum(puzzleLong, index);
        String s = hashFunction.hashString(Long.toString(sum));
        return s.endsWith("000000");
    }
}
