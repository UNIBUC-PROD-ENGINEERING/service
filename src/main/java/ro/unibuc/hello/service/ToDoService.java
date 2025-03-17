package ro.unibuc.hello.service;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.request.*;
import ro.unibuc.hello.dto.response.*;
import ro.unibuc.hello.exception.*;

@AllArgsConstructor
@Service
public class ToDoService {
    public boolean createItem(ItemDto itemDto) {
        return false;
    }
    public boolean updateItem(ItemDto itemDto) {
        return false;
    }
    public boolean deleteItem(ItemDto itemDto) {
        return false;
    }

    public boolean createBind(String username, String toDoList, boolean isOwner) {
        return false;
    }
    public boolean deleteBind(String username, String toDoList) {
        return false;
    }
}
