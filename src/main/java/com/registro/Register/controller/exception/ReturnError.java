package com.registro.Register.controller.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.server.ResponseStatusException;


public class ReturnError {

    @JsonProperty("mensagem")
    private String msg;
    private int status;

    public ReturnError(ResponseStatusException ex) {
        this.msg = ex.getReason(); // retorna a razao do erro
        this.status = ex.getStatusCode().value(); // retorna o obj http e obtem o numero do erro
    }

    public ReturnError() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}