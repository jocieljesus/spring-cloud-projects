package com.alvez.user_service.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alvez.user_service.exception.UserAlreadyExistsException;
import com.alvez.user_service.exception.UserNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {


   @ExceptionHandler(UserAlreadyExistsException.class)
   public ProblemDetail handleUserAlreadyExistsException( final UserAlreadyExistsException e){
      final  var problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
      problemDetail.setTitle("User alreadry exists");
      problemDetail.setDetail(e.getMessage());
      problemDetail.setProperty("email", e.getMessage().substring(e.getMessage().indexOf(":") + 2));
      return problemDetail;
   }

   
   @ExceptionHandler(UserNotFoundException.class)
   public ProblemDetail handleUserNotFoundException( final UserNotFoundException e){
      final  var problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
      problemDetail.setTitle("User not found");
      problemDetail.setDetail(e.getMessage());
      problemDetail.setProperty("id", e.getMessage().substring(e.getMessage().indexOf(":") + 2));
      return problemDetail;
   }
   
   @ExceptionHandler(Exception.class)
   public ProblemDetail handleUserAlreadyExistsException( final Exception e){
      final  var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
      problemDetail.setTitle("Internal Server Error");
      problemDetail.setDetail(e.getMessage());
      return problemDetail;
   }

}
