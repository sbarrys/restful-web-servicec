package com.example.demo1.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse { //오류정보를 담은 객체
    //예외 처리하는 Handler를 AOP형태로 위빙할것이다.
    private Date timestamp;
    private String message;
    private String details;
}
