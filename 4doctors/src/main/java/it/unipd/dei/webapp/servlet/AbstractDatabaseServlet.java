package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.utils.ErrorCode;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * Gets the {@code DataSource} for managing the connection pool to the database.
 */
public abstract class AbstractDatabaseServlet extends HttpServlet {

    /**
     * The connection pool to the database.
     */
    private DataSource ds;

    /**
     * Gets the {@code DataSource} for managing the connection pool to the database.
     *
     * @param config
     *          a {@code ServletConfig} object containing the servlet's
     *          configuration and initialization parameters.
     *
     * @throws ServletException
     *          if an exception has occurred that interferes with the servlet's normal operation
     */
    public void init(ServletConfig config) throws ServletException {

        // the JNDI lookup context
        InitialContext cxt;

        try {
            cxt = new InitialContext();
            ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/4Doctors");
        } catch (NamingException e) {
            ds = null;

            throw new ServletException(
                    String.format("Impossible to access the connection pool to the database: %s",
                            e.getMessage()));
        }
    }

    /**
     * Releases the {@code DataSource} for managing the connection pool to the database.
     */
    public void destroy() {
        ds = null;
    }

    /**
     * Returns the {@code DataSource} for managing the connection pool to the database.
     *
     * @return the {@code DataSource} for managing the connection pool to the database
     */
    protected final DataSource getDataSource() {
        return ds;
    }

    public void writeError(HttpServletResponse res, ErrorCode ec) throws IOException {
        res.setStatus(ec.getHTTPCode());
        res.setContentType("application/json; charset=utf-8");
        res.getWriter().write(ec.toJSON().toString());
    }

}
