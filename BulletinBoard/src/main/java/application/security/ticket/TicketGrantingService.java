package application.security.ticket;

import com.google.common.primitives.Longs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shared.HashFunction;
import shared.ticket.Ticket;
import shared.ticket.TicketSolution;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class TicketGrantingService {
    private final Map<String, Ticket> ticketCache;
    private final SecureRandom secureRandom;
    private final HashFunction hashFunction;

    public TicketGrantingService(SecureRandom secureRandom, HashFunction hashFunction) {
        this.secureRandom = secureRandom;
        this.hashFunction = hashFunction;
        this.ticketCache = new HashMap<>();
    }


    public Ticket createTicket() {
        Ticket ticket = new Ticket(getNewTimeStamp(), getNewRandom());
        rememberInCache(ticket);
        return ticket;
    }

    public boolean verifySolution(TicketSolution solvedTicket) {
        if (!isAValidTicket(solvedTicket)) return false;
        long puzzle = Long.parseLong(solvedTicket.getTicket().getPuzzle());
        long solution = Long.parseLong(solvedTicket.getSolution());

        long sum = Long.sum(puzzle, solution);
        return hashFunction.hashString(Long.toString(sum)).endsWith("00000");
    }

    private String getNewRandom() {
        byte[] bytes = new byte[20];
        secureRandom.nextBytes(bytes);
        return Long.toString(Longs.fromByteArray(bytes));
    }

    private String getNewTimeStamp() {
        return Instant.now().toString();
    }

    private void rememberInCache(Ticket ticket) {
        ticketCache.put(ticket.getTimeStamp(), ticket);
    }

    private boolean isAValidTicket(TicketSolution solvedTicket) {
        String timeStamp = solvedTicket.getTicket().getTimeStamp();
        Ticket remove = ticketCache.remove(timeStamp);
        return solvedTicket.getTicket().equals(remove);
    }
}
