package Exceptions;

public class NoRobotAvailableException extends Exception{
    public NoRobotAvailableException(){
        super();
    }

    public NoRobotAvailableException(String m){
        super(m);
    }
}
