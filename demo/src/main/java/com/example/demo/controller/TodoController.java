package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

@RestController
@RequestMapping("todo")
public class TodoController {
	@Autowired
	private TodoService service;
	
	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
		try {
			String temporaryUserId = "temporary-user"; // temporary user id.
			
			// 1. TodoEntity로 변환
			TodoEntity entity = TodoDTO.toEntity(dto);
			// 2. id를 null로 초기화
			entity.setId(null);
			// 3. 임시 사용자 아이디를 설정해준다. (로그인 없이도 사용가능한 플랫폼)
			entity.setUserId(temporaryUserId);
			// 4. 서비스를 이용하여 엔티티 생성
			List<TodoEntity> entities = service.create(entity);
			// 5. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환함
			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
			// 6. 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
			// 7. ResponseDTO를 리턴
			return ResponseEntity.ok().body(response);
			
		} catch (Exception e) {
			// 8. 예외인 경우 dto 대신 error 메시지를 넣어서 리턴함.
			// TODO: handle exception
			String error=e.getMessage();
			ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	public ResponseEntity<?> retrieveTodoList(){
		String temporaryUserId = "temporary-user"; // temporary user id
		
		// 1. 서비스 메서드의 retrieve() 메서드를 사용해 Todo 리스트를 가져온다.
		List<TodoEntity> entities = service.retrieve(temporaryUserId);
		// 2. 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		// 3. 변환된 TodoDTO 리스트를 이용해 reponseDTO를 초기화
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		// 4. ResponseDTo를 리턴
		return ResponseEntity.ok().body(response);
		
	}
	
	@GetMapping
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
		String temporaryUserId = "temporary-user"; // temporary user id.
		
		TodoEntity entity = TodoDTO.toEntity(dto);
		entity.setUserId(temporaryUserId);
		
		List<TodoEntity> entities= service.update(entity);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
		
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
		return ResponseEntity.ok().body(response);
		
		
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO dto){
		try {
		String temporaryUserId = "temporary-user"; // temporary user id.
		
		TodoEntity entity = TodoDTO.toEntity(dto);
		
		entity.setUserId(temporaryUserId);
		List<TodoEntity> entities = service.delete(entity);
		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
	
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
	
		return ResponseEntity.ok().body(response);
	
	} catch (Exception e) {
		String error = e.getMessage();
		ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
		return ResponseEntity.badRequest().body(response);
	}
	
	}
}
