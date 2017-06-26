package com.vates.wifibus.backoffice.api.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vates.wifibus.backoffice.api.resource.BussinesError;
import com.vates.wifibus.backoffice.api.util.ServiceException;

/**
 * Exception Handler.
 * 
 * @author luis.stubbia
 *
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = { ServiceException.class })
    public ResponseEntity<Object> handleServiceException(ServiceException ex) {
    	HttpStatus status = HttpStatus.BAD_REQUEST;
    	if(ex.getErrors().size() == 1){
    		status = ex.getErrors().get(0).getStatus();
    	}
    	return new ResponseEntity<Object>(ex.getErrors(), status);
    }
    
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleNonServiceException(Exception ex) {
    	
    	BussinesError error = new BussinesError(ex.getMessage(), convertStackTraceFromException(ex));
    	return new ResponseEntity<Object>(error, error.getStatus());
    }
    
    /**
     * Retrieve the stackTrace of throwable sent by param as List of String.
     * @param ex - the exception from which to grab the stack trace and caused' by stack trace.
     * @return the list of stack trace elements.
     */
    private List<String> convertStackTraceFromException(Throwable ex) {
        if (ex == null) {
            return null;
        }
        List<String> trace = new ArrayList<String>();
        trace.add(ex.getClass().getName() + ": " + ex.getMessage());
        StackTraceElement[] stackTraceElements = ex.getStackTrace();
        for (int i = 0; i < stackTraceElements.length; i++) {
            trace.add("en " + stackTraceElements[i]);
        }
        Throwable ourCause = ex.getCause();
        if (ourCause != null) {
            addStackTraceAsCause(ourCause, stackTraceElements, trace);
        }
        return trace;
    }
    
    /**
     * Retrieve our stack trace as a cause for the specified stack trace.
     * @param ex
     * @param causedTrace
     * @param stackTraceElements
     */
    private void addStackTraceAsCause(Throwable ex, StackTraceElement[] causedTrace, List<String> stackTraceElements)
    {
        StackTraceElement[] trace = ex.getStackTrace();
        int m = trace.length - 1, n = causedTrace.length - 1;
        while (m >= 0 && n >= 0 && trace[m].equals(causedTrace[n])) {
            m--;
            n--;
        }
        int framesInCommon = trace.length - 1 - m;
        stackTraceElements.add("Causado por: " + ex.getClass().getName() + ":" + ex.getMessage() + " - causado por.");
        for (int i = 0; i <= m; i++) {
            stackTraceElements.add("en " + trace[i]);
        }
        if (framesInCommon != 0) {
            stackTraceElements.add("... " + framesInCommon + " mas");
        }
        Throwable ourCause = ex.getCause();
        if (ourCause != null) {
            addStackTraceAsCause(ourCause, trace, stackTraceElements);
        }
    }
}