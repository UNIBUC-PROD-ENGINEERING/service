package ro.unibuc.prodeng.response;

public record LoginResponse(
    String token,
    String name,
    String email,
    Boolean isAdmin
) {}
