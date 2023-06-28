package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {
	@Autowired
	private TodoRepository repository;
	
	public String testService() {
		// Todo 엔티티 생성
		TodoEntity entity = TodoEntity.builder().title("todo first item").build();
		// 저장
		repository.save(entity);
		// 검색
		TodoEntity savedEntity = repository.findById(entity.getId()).get();
		return savedEntity.getTitle();
	}
	
	public List<TodoEntity> create(final TodoEntity entity){
		//Validations
		validate(entity);
		repository.save(entity);
		log.info("Entity Id : {} is saved. ",entity.getId());
		return repository.findByUserId(entity.getUserId());
	}
	private void validate(final TodoEntity entity) {
		if(entity==null) {
			log.warn("entity canoot be null. ");
			throw new RuntimeException("entity canoot be null. ");
		}
		if(entity.getUserId()==null) {
			log.warn("Unknown user. ");
			throw new RuntimeException("Unknown user. ");
		}
	}
	public List<TodoEntity> retrieve(final String userId){
		return repository.findByUserId(userId);
	}
	
	
	public List<TodoEntity> update(final TodoEntity entity){
		// 1. 저장할 엔티티가 유효한지 확인
		validate(entity);
		// 2. 넘겨받은 엔티티 id를 이용해 TodoEntity를 가져온다. (존재하지 않으면 업데이트 불가)
		final Optional<TodoEntity> original= repository.findById(entity.getId());
		// 3. 반환된 TodoEntity가 존재하면 값을 새 entity 값으로 덮어 씌운다.
		original.ifPresent(todo ->{
			todo.setTitle(entity.getTitle());
			todo.setDone(entity.isDone());
		
			// 4. 데이터베이스에 새 값을 저장
			repository.save(todo);
			
		});
		
		return retrieve(entity.getUserId());
	}
	
	public List<TodoEntity> delete(final TodoEntity entity){
		validate(entity);

		try {
			repository.delete(entity);
		} catch (Exception e) {
			log.error("error deleting entity. ", entity.getId(), e);
			throw new RuntimeException("error deleting entity. "+entity.getId());
		}
		return retrieve(entity.getUserId());
	}
	
}
