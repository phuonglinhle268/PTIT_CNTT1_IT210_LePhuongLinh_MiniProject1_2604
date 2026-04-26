package org.example.java_web_26_4.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TodoDTO {
    private Long id;

    @NotBlank(message = "Nội dung không được để trống")
    private String content;

    @FutureOrPresent(message = "Ngày phải từ hôm nay trở đi")
    private LocalDate dueDate;

    private String status;
    private String priority;
}