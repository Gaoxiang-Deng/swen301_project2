package nz.ac.wgtn.swen301.resthome4logs.Server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsServlet extends HttpServlet {

    public StatsServlet() {
        //All classes must have a default constructor (public, no parameters).
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        ArrayList<String> headerList = new ArrayList<>(List.of("logger"));
        headerList.addAll(Arrays.stream(LevelEnum.values()).map(LevelEnum::toString).collect(Collectors.toUnmodifiableList()));
        HTMLBuilder htmlBuilder = new HTMLBuilder(headerList, false);
        Map<String,List<LogEvent>> groupByLogger = Persistency.getDB().stream().collect(Collectors.groupingBy(LogEvent::getLogger));
        for(Map.Entry<String, List<LogEvent>> entry : groupByLogger.entrySet()){
            String Name = entry.getKey();
            List<LogEvent> logEvents = entry.getValue();
            var groupByLevel = logEvents.stream().collect(Collectors.groupingBy(LogEvent::getLevel));
            htmlBuilder.addLogStats(Name, groupByLevel);
        }
        out.println(htmlBuilder.build());
        out.close();
    }
}
