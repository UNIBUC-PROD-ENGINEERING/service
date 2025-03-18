package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.SupportTicketDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupportTicketService {
    
    @Autowired
    private SupportTicketRepository supportTicketRepository;
    
    public List<SupportTicketDTO> getAllSupportTickets() {
        return supportTicketRepository.findAll().stream()
                .map(ticket -> new SupportTicketDTO(ticket.getId(), ticket.getOrder().getId(), ticket.getComplaint(), ticket.getCreatedAt()))
                .collect(Collectors.toList());
    }
    
    public SupportTicketDTO createSupportTicket(String orderId, String complaint) {
        OrderEntity order = new OrderEntity(); // Trebuie înlocuit cu un OrderRepository pentru a găsi comanda reală
        order.setId(orderId);
        
        SupportTicketEntity ticket = new SupportTicketEntity(null, order, complaint, LocalDateTime.now());
        supportTicketRepository.save(ticket);
        
        return new SupportTicketDTO(ticket.getId(), ticket.getOrder().getId(), ticket.getComplaint(), ticket.getCreatedAt());
    }
    
    public void deleteSupportTicket(String id) {
        supportTicketRepository.deleteById(id);
    }
}


