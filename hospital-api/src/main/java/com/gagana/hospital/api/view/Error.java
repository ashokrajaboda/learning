package com.gagana.hospital.api.view;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Error implements Serializable {
    private ErrorType errorType;
    private String errorMessage;
}
