package ru.mylov.ATM.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;

public class ResponseMessage {
    private final String responseMessage;
    private final String responseCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time;

    @Autowired
    public ResponseMessage(String responseMessage, String responseCode, LocalDateTime time) {
        this.responseMessage = responseMessage;
        this.responseCode = responseCode;
        this.time = time;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
