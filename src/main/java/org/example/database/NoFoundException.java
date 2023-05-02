package org.example.database;

import java.sql.SQLException;

public class NoFoundException extends Exception {

    public NoFoundException() {}

    public NoFoundException(String s){
        super(s);
    }
    public NoFoundException(Throwable cause){  //new RunTimeException
        super(cause);
    }
}
