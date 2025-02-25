package com.cryptowallet.demo.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(example = "user")
    @NotBlank
    private String username;

    @Schema(example = "user")
    @NotBlank
    private String password;
}
