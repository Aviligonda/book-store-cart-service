package com.bridgelabz.bookstorecartservice.util;

import com.bridgelabz.bookstorecartservice.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Purpose :Return Book Status
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private int statusCode;
    private String statusMessage;
    private BookDTO object;
}
