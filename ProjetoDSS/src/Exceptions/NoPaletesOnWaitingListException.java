package Exceptions;

public class NoPaletesOnWaitingListException extends Exception {
    public NoPaletesOnWaitingListException() {
        super();
    }

    public NoPaletesOnWaitingListException(String m) {
        super(m);
    }
}
