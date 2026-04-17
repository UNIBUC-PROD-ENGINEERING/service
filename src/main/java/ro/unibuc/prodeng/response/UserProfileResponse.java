package ro.unibuc.prodeng.response;

public record UserProfileResponse(
    String id,
    String name,
    String email,
    String group,
    Boolean isAdmin
) {}
