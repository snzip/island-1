package com.aviator.island.exception;

/**
 * Created by aviator_ls on 2018/8/20.
 */
public class ParamsErrorException extends HelloException {
    public ParamsErrorException() {
    }

    public ParamsErrorException(String message) {
        super(message);
    }

    public ParamsErrorException(Throwable cause) {
        super(cause);
    }
}
