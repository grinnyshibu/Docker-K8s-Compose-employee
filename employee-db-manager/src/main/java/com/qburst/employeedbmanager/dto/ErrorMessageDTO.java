package com.qburst.employeedbmanager.dto;

import java.util.Date;

public class ErrorMessageDTO {
    Date date;
    String errorMessage;

    public ErrorMessageDTO() {
    }

    public ErrorMessageDTO(Date date, String errorMessage) {
        this.date = date;
        this.errorMessage = errorMessage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
