package org.example.database;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

public class Statement {
    private final String procedureNotePets = "insert into notes_pets values(?,?)";

    public void createProcedure(){
        try(CallableStatement cs = DbUtil.connect().prepareCall(procedureNotePets)){

            cs.registerOutParameter(1, Types.INTEGER);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();

        } catch(SQLException e){
            e.getMessage();
        }
    }



//    CallableStatement cs
}
