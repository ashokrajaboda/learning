package com.gagana.hospital.api.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = 7085493516831497165L;
    private Boolean success;
    private HttpStatusCode httpStatus;
    private T data;
    private Errors errors;

    public static <T> ResponseDTO<T> buildResponse(Boolean success, HttpStatusCode httpStatus, T data, Errors errors) {
        return ResponseDTO.<T>builder()
                .success(success)
                .httpStatus(httpStatus)
                .data(data)
                .errors(errors)
                .build();
    }

    public static <T> ResponseDTO<T> buildSuccessResponse(HttpStatusCode httpStatus, T data) {
        return buildResponse(Boolean.TRUE, httpStatus, data, null);
    }

    public static <T> ResponseDTO<T> buildSuccessResponse(T data) {
        return buildSuccessResponse(HttpStatus.OK, data);
    }

    public static <T> ResponseDTO<T> buildFailureResponse(HttpStatusCode httpStatus, Errors errors) {
        return buildResponse(Boolean.FALSE, httpStatus, null, errors);
    }

    public static <T> ResponseDTO<T> buildFailureResponse(Errors errors) {
        return buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, errors);
    }
}
