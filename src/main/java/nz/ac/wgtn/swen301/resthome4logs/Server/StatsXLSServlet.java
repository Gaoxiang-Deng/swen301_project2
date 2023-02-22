package nz.ac.wgtn.swen301.resthome4logs.Server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatsXLSServlet extends HttpServlet {
    public StatsXLSServlet() {
        //All classes must have a default constructor (public, no parameters).
    }

    static final String SHEET_NAME = "stats";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/vnd.ms-excel");
        TableGenerator.generateWorkbook().write(resp.getOutputStream());
    }
}
