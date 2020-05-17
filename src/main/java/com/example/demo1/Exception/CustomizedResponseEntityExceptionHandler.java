package com.example.demo1.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sun.misc.Request;

import java.util.Date;

@RestController
@ControllerAdvice // 모든 컨트롤러 가 작동될 경우 작동되도록 Advice셋팅
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    //모든 컨트롤러가 실행 될때 , Exception.class가 발현되게 된다면, 아래 함수를 타게된다.
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handlerAllExceptions(Exception e, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),e.getMessage(),request.getDescription(false));
    //에러로부터 메세지받고, 클라이언트로 부터 받은 데이터 description은 없음
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);//서버에러
    }

    //유저가 없는경우 아래 함수를 탄다.
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handlerUserNotFoundException(Exception e, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(),e.getMessage(),request.getDescription(false));
        //에러로부터 메세지받고, 클라이언트로 부터 받은 데이터 description은 없음
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);//클라이언트 의 잘못이므로 400번 코드를 준다.
    }
    /*@Valid의 조건에 맞지 않는 매개변수가 들어올경우*/
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request){
        ExceptionResponse exceptionResponse =new ExceptionResponse(new Date(),ex.getMessage(),ex.getBindingResult().toString());
        return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);
    }

}
