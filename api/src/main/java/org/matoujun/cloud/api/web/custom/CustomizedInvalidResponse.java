package org.matoujun.cloud.api.web.custom;

import org.matoujun.cloud.common.http.RestResponse;
import org.matoujun.cloud.common.http.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * This class is to customize all kinds of exception handlers for rest api.
 * @author matoujun

 */
@ControllerAdvice
@RestController
public class CustomizedInvalidResponse extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException
                                                                          ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                "Validation Failed",
                ex.getBindingResult().toString());
        RestResponse<ErrorDetails> invalidRespone = new RestResponse<>();
        invalidRespone.setData(errorDetails);
        invalidRespone.setErrno(400);
        invalidRespone.setErrmsg("INVALID_REQUEST_PARAMETER");
        return new ResponseEntity<Object>(invalidRespone,new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        RestResponse restResponse = new RestResponse();
        restResponse.setErrno(400);
        restResponse.setErrmsg("INVALID_REQUEST_PARAMETER");
        restResponse.setData(ex.getMessage());
        return new ResponseEntity<Object>(
                restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
