package com.eubrunocoelho.ticketing.exception.business;

public class SelfReplyNotAllowedException extends RuntimeException {

    public SelfReplyNotAllowedException(String message) {
        super(message);
    }
}
