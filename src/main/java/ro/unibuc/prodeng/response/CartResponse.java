package ro.unibuc.prodeng.response;

import java.time.Instant;
import java.util.List;

public record CartResponse(
    String id, String userID, List<CartItemResponse> items, String status, Instant createdAt
) {}