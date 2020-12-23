package Exceptions;

public class NoPrateleirasAvailableException extends Exception{
    public NoPrateleirasAvailableException(){
        super();
    }

    public NoPrateleirasAvailableException(String m){
        super(m);
    }
}
