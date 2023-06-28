package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;

@RestController
@RequestMapping("test")
public class TestController {
	public String testController() {
		return "hello";
	}
	@GetMapping("/testGetMapping")
	public String testControllerWithPath() {
		return "GetMapping";
	}
	@GetMapping("/{id}")
	public String testControllerWithPathVariables(@PathVariable(required=false)int id) {
		return "PathVariable ID: " +id;
	}
	@GetMapping("/testRequestParam")
	public String testControllerRequestParam(@RequestParam(required =false)int id) {
		return "RequestParam ID: "+id;
	}
	@GetMapping("/testRequestBody")
	public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
		return "RequestBody ID: "+testRequestBodyDTO.getId()+" Message: "+ testRequestBodyDTO.getMessage();
	}
	@GetMapping("/testRequestEntity")
	public ResponseEntity<?> testControllerResponseEntity() {
		List<String> list = new ArrayList<>();
		list.add("ResponseEntity. you got 400 ! ");
		ResponseEntity<String> response = ResponseDTO.<String>builder()
				.data(list)
				.build();
		// http status 400으로 설정
		return ResponseEntity.badRequest().body(response);
	}
}
