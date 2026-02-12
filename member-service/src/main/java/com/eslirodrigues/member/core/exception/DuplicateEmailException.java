package com.eslirodrigues.member.core.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException() {
        super("Member creation failed due to a conflict");
    }
}
