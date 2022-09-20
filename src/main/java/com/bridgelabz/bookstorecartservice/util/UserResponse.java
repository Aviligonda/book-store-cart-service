package com.bridgelabz.bookstorecartservice.util;

import com.bridgelabz.bookstorecartservice.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private int statusCode;
    private String statusMessage;
    private UserDTO object;
}
