package nz.ac.wgtn.swen301.resthome4logs.Server;

import java.util.*;
import java.util.stream.Collectors;

public class FormatList {

    public static List<List<String>> generateListOfList() {
        var listOfList = new ArrayList<List<String>>();
        ArrayList<String> headerList = new ArrayList<>(List.of("logger"));
        headerList.addAll(Arrays.stream(LevelEnum.values()).map(LevelEnum::toString).collect(Collectors.toUnmodifiableList()));
        listOfList.add(headerList);
        var groupByLogger = Persistency.getDB().stream().collect(Collectors.groupingBy(LogEvent::getLogger));
        for(Map.Entry<String, List<LogEvent>> entry : groupByLogger.entrySet()){
            String Name = entry.getKey();
            List<LogEvent> logEvents = entry.getValue();
            var groupByLevel = logEvents.stream().collect(Collectors.groupingBy(LogEvent::getLevel));
            listOfList.add(generateList(Name, groupByLevel));
        }

        return listOfList;
    }

    private static List<String> generateList(String loggerName, Map<LevelEnum, List<LogEvent>> groupByLevel) {
        var list = new ArrayList<>(List.of(loggerName));
        for (var i = 0; i < LevelEnum.values().length; i++) {
            LevelEnum currentLevel = LevelEnum.values()[i];
            if (groupByLevel.containsKey(currentLevel)) {
                list.add(String.valueOf(groupByLevel.get(currentLevel).size()));
            } else {
                list.add("0");
            }
        }
        return Collections.unmodifiableList(list);
    }
}
