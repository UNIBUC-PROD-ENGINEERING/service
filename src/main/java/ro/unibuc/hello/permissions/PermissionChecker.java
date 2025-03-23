package ro.unibuc.hello.permissions;

public interface PermissionChecker<T> {
    void checkOwnership(String userId, String resourceId);
}
