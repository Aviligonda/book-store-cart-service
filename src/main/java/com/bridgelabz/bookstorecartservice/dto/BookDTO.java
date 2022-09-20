package com.bridgelabz.bookstorecartservice.dto;

import lombok.Data;

import javax.persistence.Lob;
import java.time.LocalDateTime;
/**
 * Purpose : BookDTO fields are Used Retrieve the data from Book service
 * Version : 1.0
 *
 * @author : Aviligonda Sreenivasulu
 */
@Data
public class BookDTO {
    private Long id;
    private String bookName;
    private String author;
    @Lob
    private byte[] logo;
    private long bookPrice;
    private long bookQuantity;
    private LocalDateTime creationTime;
    private LocalDateTime updatedTime;
}
