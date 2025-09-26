package mate.academy.springlibrary.exeption;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
