package nz.ac.wgtn.swen301.resthome4logs.Server;

import java.util.List;
import java.util.Map;



public class HTMLBuilder {
    private final StringBuilder table = new StringBuilder();
    private static final String DOCTYPE = "<!DOCTYPE html>";
    private static final String HEAD = "<head>";
    private static final String TITLE = "<h1>Log Statistics</h1>";
    private static final String BODY_START = "<body>";
    private static final String BODY_END = "</body>";
    private static final String HTML_START = "<html lang=\"en\">";
    private static final String HTML_END = "</html>";
    private static final String TABLE_START_BORDER = "<table border=\"1\">";
    private static final String TABLE_START = "<table>";
    private static final String TABLE_END = "</table>";
    private static final String HEADER_START = "<th>";
    private static final String HEADER_END = "</th>";
    private static final String ROW_START = "<tr>";
    private static final String ROW_END = "</tr>";
    private static final String COLUMN_START = "<td>";
    private static final String COLUMN_END = "</td>";



    public HTMLBuilder(List<String> headerList, boolean border) {
        table.append(DOCTYPE);
        table.append(HTML_START);
        table.append(HEAD);
        table.append(BODY_START);
        table.append(TITLE);
        if (border) { table.append(TABLE_START_BORDER);} else { table.append(TABLE_START);}
        table.append(TABLE_END);
        this.addTableHeader(headerList);
        table.append(BODY_END);
        table.append(HTML_END);
    }


    private void addTableHeader(List<String> values) {
        int lastIndex = table.lastIndexOf(TABLE_END);
        if (lastIndex > 0) {
            var sb = new StringBuilder();
            sb.append(ROW_START);
            for (String value : values) {
                sb.append(HEADER_START);
                sb.append(value);
                sb.append(HEADER_END);
            }
            sb.append(ROW_END);
            table.insert(lastIndex, sb);
        }
    }


    /**
     * Add log stats.
     *
     * @param loggerName   the logger name
     * @param groupByLevel the group by level
     */
    public void addLogStats(String loggerName, Map<LevelEnum, List<LogEvent>> groupByLevel) {
        int lastIndex = table.lastIndexOf(ROW_END);
        if (lastIndex > 0) {
            int index = lastIndex + ROW_END.length();
            var sb = new StringBuilder();
            sb.append(ROW_START);
            sb.append(COLUMN_START);
            sb.append(loggerName);
            sb.append(COLUMN_END);
            for (var i = 0; i < LevelEnum.values().length; i++) {
                sb.append(COLUMN_START);
                LevelEnum currentLevel = LevelEnum.values()[i];
                if (groupByLevel.containsKey(currentLevel)) {
                    sb.append(groupByLevel.get(currentLevel).size());
                } else {
                    sb.append("0");
                }
                sb.append(COLUMN_END);
            }
            sb.append(ROW_END);
            table.insert(index, sb);
        }
    }


    public String build() {
        return table.toString();
    }


}