package g56080.atl.sortingRace.exception;

public class RepositoryException extends RuntimeException{

    public RepositoryException(String msg){
        super(msg);
    }

    public RepositoryException(String msg, Throwable cause){
        super(msg, cause);
    }

    public RepositoryException(Throwable cause){
        super(cause);
    }
}

