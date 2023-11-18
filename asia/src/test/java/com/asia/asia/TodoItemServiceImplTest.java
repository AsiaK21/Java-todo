package com.asia.asia;

import com.asia.asia.entities.Priority;
import com.asia.asia.entities.TodoItem;
import com.asia.asia.entities.User;
import com.asia.asia.repositories.TodoItemRepository;
import com.asia.asia.services.impl.TodoItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TodoItemServiceImplTest {

    @Mock
    private TodoItemRepository todoItemRepository;

    @InjectMocks
    private TodoItemServiceImpl todoItemService;

    @Test
    void getById_ReturnsTodoItem() {
        Long itemId = 1L;
        TodoItem testTodo = new TodoItem();
        testTodo.setId(itemId);
        when(todoItemRepository.findById(itemId)).thenReturn(Optional.of(testTodo));
        Optional<TodoItem> result = todoItemService.getById(itemId);
        assertNotNull(result);
        assertEquals(testTodo, result.orElse(null));
    }

    @Test
    void getAll_ReturnsAllTodoItems() {
        TodoItem testTodo1 = new TodoItem();
        TodoItem testTodo2 = new TodoItem();
        when(todoItemRepository.findAll()).thenReturn(List.of(testTodo1, testTodo2));
        Iterable<TodoItem> result = todoItemService.getAll();
        assertNotNull(result);
        assertEquals(2, ((List<TodoItem>) result).size());
    }

    @Test
    void getAllFromUser_ReturnsTodoItemsForUser() {
        Long userId = 1L;
        TodoItem testItem1 = new TodoItem();
        TodoItem testItem2 = new TodoItem();
        when(todoItemRepository.findByUserId(userId)).thenReturn(List.of(testItem1, testItem2));
        Iterable<TodoItem> result = todoItemService.getAllFromUser(userId);
        assertNotNull(result);
        assertEquals(2, ((List<TodoItem>) result).size());
    }

    @Test
    void save_NewTodoItem_ReturnsSavedTodoItem() {
        TodoItem unsavedTodo = new TodoItem();
        when(todoItemRepository.save(unsavedTodo)).thenReturn(unsavedTodo);
        TodoItem savedTodo = todoItemService.save(unsavedTodo);
        assertNotNull(savedTodo);
        assertEquals(unsavedTodo, savedTodo);
        verify(todoItemRepository, times(1)).save(unsavedTodo);
    }

    @Test
    void save_ExistingTodoItem_ReturnsSavedTodoItem() {
        TodoItem existingTodo = new TodoItem();
        existingTodo.setId(1L);
        existingTodo.setCreatedAt(LocalDateTime.now().minusDays(1));
        when(todoItemRepository.findById(existingTodo.getId())).thenReturn(Optional.of(existingTodo));
        when(todoItemRepository.save(existingTodo)).thenReturn(existingTodo);
        TodoItem savedTodo = todoItemService.save(existingTodo);
        assertNotNull(savedTodo);
        assertEquals(existingTodo, savedTodo);
        verify(todoItemRepository, times(1)).save(existingTodo);
    }

    @Test
    void delete_DeletesTodoItem() {
        TodoItem TodoToDelete = new TodoItem();
        doNothing().when(todoItemRepository).delete(TodoToDelete);
        todoItemService.delete(TodoToDelete);
        verify(todoItemRepository, times(1)).delete(TodoToDelete);
    }

    @Test
    void exportToXml_ReturnsXmlString() {
        Long itemId = 1L;
        TodoItem testTodo = new TodoItem();
        testTodo.setId(itemId);
        testTodo.setDescription("Test description");
        testTodo.setPriority(Priority.HIGH);
        List<TodoItem> todoItems = Collections.singletonList(testTodo);
        String xmlResult = todoItemService.exportToXml(todoItems);
        assertNotNull(xmlResult);
        assertTrue(xmlResult.contains("<item>"));
        assertTrue(xmlResult.contains("<description>Test description</description>"));
        assertTrue(xmlResult.contains("<priority>HIGH</priority>"));
        assertTrue(xmlResult.contains("<description>Test description</description>"));
        assertTrue(xmlResult.contains("<priority>HIGH</priority>"));
    }

    @Test
    void exportToJson_ReturnsJsonString() {
        User testUser = new User();
        testUser.setUsername("testUser");
        Long itemId = 1L;
        TodoItem testTodo = new TodoItem();
        testTodo.setId(itemId);
        testTodo.setDescription("Test description");
        testTodo.setPriority(Priority.HIGH);
        testTodo.setUser(testUser);
        List<TodoItem> todoItems = Collections.singletonList(testTodo);
        String jsonResult = todoItemService.exportToJson(todoItems);
        assertNotNull(jsonResult);
    }

    @Test
    void findByPriority_ReturnsTodoItemsWithPriorityForUser() {
        Long userId = 1L;
        Priority priority = Priority.HIGH;
        TodoItem testTodo1 = new TodoItem();
        testTodo1.setPriority(priority);
        TodoItem testTodo2 = new TodoItem();
        testTodo2.setPriority(Priority.LOW);
        when(todoItemRepository.findByPriorityAndUserId(priority, userId)).thenReturn(List.of(testTodo1));
        Iterable<TodoItem> result = todoItemService.findByPriority(priority, userId);
        assertNotNull(result);
        assertEquals(1, ((List<TodoItem>) result).size());
        assertEquals(priority, ((List<TodoItem>) result).get(0).getPriority());
    }
}
