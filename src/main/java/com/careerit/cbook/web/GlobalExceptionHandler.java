package com.careerit.cbook.web;


import com.careerit.cbook.domain.ErrorMessage;
import com.careerit.cbook.service.ContactExistsException;
import com.careerit.cbook.service.ContactNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ContactNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleContactNotFoundException(Exception e){
        ErrorMessage errorMessage=new ErrorMessage(e.getMessage(),"404");
        return ResponseEntity.status(404).body(errorMessage);
    }


    @ExceptionHandler({ContactExistsException.class})
    public ResponseEntity<ErrorMessage> handleContactExistsException(Exception e){
        ErrorMessage errorMessage=new ErrorMessage(e.getMessage(),"403");
        return ResponseEntity.status(403).body(errorMessage);
    }

}
