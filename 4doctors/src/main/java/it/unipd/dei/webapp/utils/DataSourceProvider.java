package it.unipd.dei.webapp.utils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Gets the {@code DataSource} for managing the connection pool to the database.
 */
public class DataSourceProvider {

    //static datasource
    private static DataSource ds = null;

    //constructor method
    public static synchronized DataSource getDataSource() throws NamingException {

        // we don't want to initialize a new datasource everytime, so, we check first that ds is null
        if (ds == null) {

            InitialContext ctx = new InitialContext();

            //and use the proper resource to initialize the datasource
            ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/4Doctors");
        }
        return ds;
    }
}
