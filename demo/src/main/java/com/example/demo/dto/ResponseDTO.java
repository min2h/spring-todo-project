package com.example.demo.dto;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
// http 응답을 위한 데이터 정의
public class ResponseDTO<T> {
	private String error;
	private List<T> data;
}
