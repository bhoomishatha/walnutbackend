package com.walnutclinics.billingService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalErrorHandler {

@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
public ResponseEntity<Map> handleSqlException(SQLIntegrityConstraintViolationException ex)
{
    Map<String,String> responseData = new HashMap();
    if(ex.getSQLState().equals("23000"))
    {
        responseData.put("message","The record you are trying to delete is being used, Record cannot be deleted , please change the status instead.");
        responseData.put("error","The record you are trying to delete is being used.");
    }
    return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
}


}
