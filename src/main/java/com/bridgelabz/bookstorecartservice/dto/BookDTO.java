package com.bridgelabz.bookstorecartservice.dto;

import lombok.Data;

import javax.persistence.Lob;
import java.time.LocalDateTime;
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
