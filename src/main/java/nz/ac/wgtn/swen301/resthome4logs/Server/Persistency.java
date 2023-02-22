package nz.ac.wgtn.swen301.resthome4logs.Server;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Persistency {
    public static final List<LogEvent> DB = new CopyOnWriteArrayList<>();
    public static Boolean add(LogEvent s) {
        return DB.add(s);
    }

    public Persistency() {
        //All classes must have a default constructor (public, no parameters).
    }

    public static void clear() {
        DB.clear();
    }

    public static int getSize() {
        return DB.size();
    }


    public static List<LogEvent> getDB() {
        return Collections.unmodifiableList(DB);
    }
}
