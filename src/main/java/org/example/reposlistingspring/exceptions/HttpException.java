package org.example.reposlistingspring.exceptions;

import java.io.Serializable;

public class HttpException extends RuntimeException implements Serializable {
  private static final long serialVersionUID = 1L;
  private final int statusCode;

    public HttpException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpException(int statusCode){
      super();
      this.statusCode = statusCode;
    }
    public int getStatusCode() {
      return statusCode;
    }
}
