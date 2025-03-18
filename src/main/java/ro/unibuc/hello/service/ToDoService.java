package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.*;
import ro.unibuc.hello.dto.request.*;
import ro.unibuc.hello.dto.response.*;
import ro.unibuc.hello.exception.*;

@AllArgsConstructor
@Service
public class ToDoService {
    @Autowired
    private final UserService userService;

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

    public boolean createRequest(String toDoList, String text) {
        return false;
    }
    public boolean acceptRequest(String username, String toDoList) {
        return false;
    }
    public boolean denyRequest(String username, String toDoList) {
        return false;
    }

    public boolean createToDoList(String name, String description) {
        // Owner
        return false;
    }
    public boolean updateToDoList(String oldName, String name, String description) {
        return false;
    }
    public boolean deleteToDoList(String name) {
        // + bind urile existente
        return false;
    }
    public ToDoListCollectionDto getMyToDoLists() {
        return new ToDoListCollectionDto();
    }
    public UserListDto getMembersToDoList(String name) {
        return new UserListDto();
    }
    public RequestListDto getRequestsToDoList(String name) {
        return new RequestListDto();
    }
    public ItemListDto getItemsToDoList(String name) {
        return new ItemListDto();
    }
    public boolean leaveToDoList(String name) {
        return false;
    }
}
