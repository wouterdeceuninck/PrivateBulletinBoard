package application.security.ticket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.security.HashFunctionImpl;
import shared.ticket.Ticket;
import shared.ticket.TicketSolution;
import shared.utils.DefaultObjectMapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

class TicketSolverTest {

    @Test
    void createTicketSolution() throws NoSuchAlgorithmException {
        TicketSolver ticketSolver = new TicketSolver(new HashFunctionImpl(MessageDigest.getInstance("sha-256")));
        TicketSolution ticketSolution = ticketSolver.solveTicket(new Ticket(Instant.now().toString(), "123456798789"));

        Assertions.assertNotNull(ticketSolution.getSolution());
        Assertions.assertNotNull(ticketSolution.getTicket());

        System.out.println(DefaultObjectMapper.mapToString(ticketSolution));
    }
}