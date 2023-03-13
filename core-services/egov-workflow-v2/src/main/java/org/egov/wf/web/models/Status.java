package org.egov.wf.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Status {
    STARTED, FINISHED, FAILED;

    @JsonCreator
    public static Status fromValue(String text) {
        for (Status status : Status.values()) {
            if (String.valueOf(status).equalsIgnoreCase(text)) {
                return status;
            }
        }
        return null;
    }
}
