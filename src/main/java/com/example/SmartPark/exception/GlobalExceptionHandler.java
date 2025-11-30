package com.example.SmartPark.exception;

import com.example.SmartPark.dto.response.Response;
import com.example.SmartPark.utils.ResponseUtil;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.example.SmartPark.constants.ResponseConstant.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Invalid Inputs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Get the first error message
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(INVALID_INPUT);

        return new ResponseEntity<>(ResponseUtil.error(errorMessage, BAD_REQUEST_CODE), HttpStatus.BAD_REQUEST);
    }

    // Handle invalid JSON / type mismatch
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response<Void>> handleInvalidType(HttpMessageNotReadableException ex) {
        String message = TYPE_MISMATCH;

        //Invalid Enums
        if (ex.getCause() instanceof InvalidFormatException invalidFormatEx) {
            String field = invalidFormatEx.getPath().get(0).getFieldName();
            String type = invalidFormatEx.getTargetType().getSimpleName();

            if(invalidFormatEx.getTargetType().isEnum()){
                Class<?> enumClass = invalidFormatEx.getTargetType();

                String allowedValues = Arrays.stream(enumClass.getEnumConstants())
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));

                message = INVALID_ENUM(field, allowedValues);

                return ResponseEntity.badRequest().body(
                        ResponseUtil.error(message, 400)
                );
            }


            message = TYPE_MISMATCH(field, type);
        }

        return new ResponseEntity<>(ResponseUtil.error(message, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }


}
