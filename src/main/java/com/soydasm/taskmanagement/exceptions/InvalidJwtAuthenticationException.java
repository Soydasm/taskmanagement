package com.soydasm.taskmanagement.exceptions;

import javax.naming.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {

    public InvalidJwtAuthenticationException(String msg) {
        super(msg);
    }
}
