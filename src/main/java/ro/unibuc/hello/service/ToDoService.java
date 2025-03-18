package ro.unibuc.hello.service;

import java.util.Optional;

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
    private ItemRepository itemRepository;

    @Autowired
    private ToDoListRepository toDoListRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ToDoListRepository toDoListRepository;

    @Autowired
    private final UserListRepository userListRepository;

    @Autowired
    private final RequestRepository requestRepository;


    public boolean createItem(ItemDto itemDto) {
        if (itemRepository.findByName(itemDto.getName()) == null)
        {
            return false;
        }

        try
        {
            itemRepository.save(new ItemEntity(itemDto.getName(), itemDto.getDescription(), itemDto.getTodoList()));
        }
        catch (Exception exception)
        {
            return false;
        }

        return true;
    }
    public boolean updateItem(ItemDto itemDto) {
        ItemEntity itemEntity = itemRepository.findByName(itemDto.getName());

        if (itemEntity == null)
        {
            return false;
        }

        try
        {
            itemEntity.setName(itemDto.getName());
            itemEntity.setDescription(itemDto.getDescription());
            itemEntity.setTodoList(itemDto.getTodoList());
            itemRepository.save(itemEntity);
        }
        catch (Exception exception)
        {
            return false;
        }

        return true;
    }
    public boolean deleteItem(ItemDto itemDto) {
        ItemEntity itemEntity = itemRepository.findByName(itemDto.getName());

        if (itemEntity == null)
        {
            return false;
        }

        try
        {
            itemRepository.delete(itemEntity);
        }
        catch (Exception exception)
        {
            return false;
        }

        return true;
    }

    public boolean createBind(String username, String toDoList, boolean isOwner) {

       try{

        Optional<UserListEntity> existingUserList = userListRepository.findByUsernameAndToDoListAndIsOwner(username, toDoList, isOwner);
        
        if (existingUserList.isPresent()) {

            return false;
        }

        userListRepository.save(new UserListEntity(username,toDoList,isOwner));

       }
       catch (Exception exception)
       {
        return false;
       }
       return true;
    }

    public boolean deleteBind(String username, String toDoList) {
        try {
           
            Optional<UserListEntity> existingUserList = userListRepository.findByUsernameAndToDoList(username, toDoList);
    
            if (existingUserList.isPresent()) {
               
                userListRepository.delete(existingUserList.get());
                return true;
            } else {
                
                return false;
            }
        } catch (Exception exception) {
           
            return false;
        }
    }
    
        
    public boolean createRequest(String toDoList, String text) {
        try {

            var user = userService.getAuthenticatedUser();
    
            RequestEntity newRequest = new RequestEntity(user.getUsername(), toDoList, text);
            requestRepository.save(newRequest);  
    
        } catch (Exception exception) {
            
            return false;
        }
        return true;  
    }
    

    public boolean acceptRequest(String username, String toDoList) {
        try {
           
            Optional<RequestEntity> requestOpt = requestRepository.findByUsernameAndToDoList(username, toDoList);
            
            if (requestOpt.isPresent()) {
                
                createBind(username, toDoList, false);
                
                
                requestRepository.delete(requestOpt.get());
                
                return true;  
            } else {
                return false;  
            }
        } catch (Exception exception) {
           
            System.out.println("Error accepting request: " + exception.getMessage());
            return false;
        }
    }


    public boolean denyRequest(String username, String toDoList) {
        try {
           
            Optional<RequestEntity> requestOpt = requestRepository.findByUsernameAndToDoList(username, toDoList);
            
            if (requestOpt.isPresent()) {
                
                requestRepository.delete(requestOpt.get());
                
                return true;  
            } else {
                return false;  
            }
        } catch (Exception exception) {
           
            System.out.println("Error accepting request: " + exception.getMessage());
            return false;
        }
    }

    public boolean createToDoList(String name, String description) {
        try
        {
            toDoListRepository.save(new TodoListEntity(name, description));
            UserEntity user = userService.getSelf().getUser();
            createBind(user.getName(), name, true);
        }
        catch (Exception exception)
        {
            return false;
        }
        return true;
    }
    public boolean updateToDoList(String oldName, String name, String description) {
        ToDoListEntity toDoListEntity = toDoListRepository.findByName(oldName);
        try
        {
            toDoListEntity.setName(name);
            toDoListEntity.setDescription(description);
            toDoListRepository.save(toDoListEntity);
        }
        catch (Exception exception)
        {
            return false;
        }

        return true;
    }
    public boolean deleteToDoList(String name) {
        ToDoListEntity toDoListEntity = toDoListRepository.findByName(oldName);
        try
        {
            toDoListRepository.delete(toDoListEntity);
        }
        catch (Exception exception)
        {
            return false;
        }

        return true;
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
