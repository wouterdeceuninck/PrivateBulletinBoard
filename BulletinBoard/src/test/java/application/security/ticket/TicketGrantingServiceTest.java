package application.security.ticket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.security.HashFunctionImpl;
import shared.ticket.Ticket;
import shared.ticket.TicketSolution;
import shared.utils.DefaultObjectMapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

class TicketGrantingServiceTest {

    private TicketGrantingService ticketGrantingService;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        ticketGrantingService = new TicketGrantingService(SecureRandom.getInstance("SHA1PRNG"), new HashFunctionImpl(MessageDigest.getInstance("sha-256")));
    }

    @Test
    void createTicket() {
        Ticket ticket = ticketGrantingService.createTicket();
        Assertions.assertNotNull(ticket);
        Assertions.assertNotNull(ticket.getTimeStamp());
        Assertions.assertNotNull(ticket.getPuzzle());

        System.out.println(ticket.getTimeStamp());
        System.out.println(ticket.getPuzzle());
        System.out.println(DefaultObjectMapper.mapToString(ticket));
    }

    @Test
    void verifyTickey_AssertNotInCache() {
        Ticket ticket = new Ticket("", "123456798789");
        TicketSolution ticketSolution = new TicketSolution(ticket, "14789626");

        Assertions.assertFalse(ticketGrantingService.verifySolution(ticketSolution));
    }

    @Test
    void verifyTicketCannotBeAltered() {
        Ticket ticket = new Ticket("", "123456798789");
        TicketSolution ticketSolution = new TicketSolution(ticket, "14789626");

        Ticket ticket1 = ticketGrantingService.createTicket();
        TicketSolution forgedTicketSolution = new TicketSolution(new Ticket(ticket1.getTimeStamp(), ticket.getPuzzle()), ticketSolution.getSolution());
        Assertions.assertFalse(ticketGrantingService.verifySolution(forgedTicketSolution));
    }
}