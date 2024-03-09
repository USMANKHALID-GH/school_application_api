package com.usman.exception;

public class InvalidPasswordException  extends BusinessException{

    private static final long serialVersionUID = 1L;

    public InvalidPasswordException() {
        super("Hatalı şifre.");
    }
}
