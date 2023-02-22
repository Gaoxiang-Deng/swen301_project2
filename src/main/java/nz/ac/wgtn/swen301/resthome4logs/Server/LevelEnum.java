package nz.ac.wgtn.swen301.resthome4logs.Server;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LevelEnum {
    ALL("ALL"),
    DEBUG("DEBUG"),
    INFO("INFO"),
    WARN("WARN"),
    ERROR("ERROR"),
    FATAL("FATAL"),
    TRACE("TRACE"),
    OFF("OFF");


    private final String value;

    LevelEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }


    public boolean isGreaterOrEqual(LevelEnum r) {
        return this.compareTo(r) >= 0;
    }

}
