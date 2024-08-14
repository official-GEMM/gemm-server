package com.example.gemm_server.exception;

import com.example.gemm_server.common.code.error.GeneratorErrorCode;

public class GeneratorException extends CustomException {

    public GeneratorException() {
        super();
    }

    public GeneratorException(String message) {
        super(message);
    }

    public GeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneratorException(GeneratorErrorCode errorCode) {
        super(errorCode);
    }

    public GeneratorException(GeneratorErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
