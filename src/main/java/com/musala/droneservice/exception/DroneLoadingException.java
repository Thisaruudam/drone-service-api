package com.musala.droneservice.exception;

public class DroneLoadingException extends RuntimeException {

    public DroneLoadingException(String message) {
        super(message);
    }

    public DroneLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
