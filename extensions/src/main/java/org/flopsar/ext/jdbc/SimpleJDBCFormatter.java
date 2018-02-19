package org.flopsar.ext.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;




public class SimpleJDBCFormatter {

    private static final char PARAMETER_SEPARATOR = 0x1E;

    private static ThreadLocal<String> SQL = new ThreadLocal<String>(){
        protected synchronized String initialValue(){
            return "NA";
        }
    };


    public static String formatStatement(Object[] input){
        try {
            Statement statement = (Statement)input[0];
            return "URL"+PARAMETER_SEPARATOR+statement.getConnection().getMetaData().getURL()
                    +PARAMETER_SEPARATOR+"SQL"+PARAMETER_SEPARATOR+input[1];
        } catch (Throwable e) {
            return "Error"+PARAMETER_SEPARATOR+e.getMessage();
        }
    }


    public static String formatConnection(Object[] input){
        try {
            Connection conn = (Connection)input[0];
            String ssql = (String)input[1];
            SQL.set(ssql);

            return "URL"+PARAMETER_SEPARATOR+conn.getMetaData().getURL()
                    +PARAMETER_SEPARATOR+"SQL"+PARAMETER_SEPARATOR+ssql;
        } catch (Throwable e) {
            return "Error"+PARAMETER_SEPARATOR+e.getMessage();
        }
    }


    public static String formatPreparedStatement(Object[] input){
        try {
            PreparedStatement ps = (PreparedStatement)input[0];
            String ssql = SQL.get();

            return "URL"+PARAMETER_SEPARATOR+ps.getConnection().getMetaData().getURL()
                    +PARAMETER_SEPARATOR+"SQL"+PARAMETER_SEPARATOR+ssql;
        } catch (Throwable e) {
            return "Error"+PARAMETER_SEPARATOR+e.getMessage();
        }
    }


}
