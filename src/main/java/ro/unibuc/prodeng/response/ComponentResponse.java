package ro.unibuc.prodeng.response;

import java.time.Instant;
import java.util.List;

public record ComponentResponse(
    String id,
    String name,
    String description,
    String category,
    List<String> photoUrls,
    int quantity,
    int availableQuantity, 
    boolean isConsumable,
    List<String> tags,
    String infoMarkdown,
    Instant createdAt
) {}