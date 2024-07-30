package com.example.gemm_server.exception;

import com.example.gemm_server.common.code.error.GemErrorCode;

public class GemException extends CustomException {

    public GemException() {
        super();
    }

    public GemException(String message) {
        super(message);
    }

    public GemException(String message, Throwable cause) {
        super(message, cause);
    }

    public GemException(GemErrorCode errorCode) {
        super(errorCode);
    }

    public GemException(GemErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
