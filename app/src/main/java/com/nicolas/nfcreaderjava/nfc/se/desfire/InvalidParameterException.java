package com.nicolas.nfcreaderjava.nfc.se.desfire;

// https://youtrack.jetbrains.com/issue/IDEA-209050
@SuppressWarnings("WeakerAccess")
public class InvalidParameterException extends Exception {
    public InvalidParameterException(String message) {
        super(message);
    }
}
