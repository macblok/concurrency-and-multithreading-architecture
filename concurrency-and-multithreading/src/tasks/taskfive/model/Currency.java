package tasks.taskfive.model;

import java.io.Serializable;

public class Currency implements Serializable {
    private String code; // e.g., USD, EUR

    public Currency(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
