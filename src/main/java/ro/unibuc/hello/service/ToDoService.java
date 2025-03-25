package ro.unibuc.hello.service;

import java.util.Optional;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.service.SharingService;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.request.*;
import ro.unibuc.hello.dto.response.*;
import ro.unibuc.hello.exception.EntityNotFoundException;


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
    private final UserListRepository userListRepository;

    @Autowired
    private final RequestRepository requestRepository;

    private final ModelMapper modelMapper;

    @Autowired
    private final SharingService sharingService;


    public ItemResponseDto createItem(ItemDto itemDto) {
        if (itemRepository.findByName(itemDto.getName()) == null)
        {
            throw new EntityNotFoundException("item");
        }

        try
        {
            var itemEntity = new ItemEntity(itemDto.getName(), itemDto.getDescription(), itemDto.getTodoList());
            itemRepository.save(itemEntity);
            return modelMapper.map(itemEntity,ItemResponseDto.class);
        }
        catch (Exception exception)
        {
            throw new EntityNotFoundException("item");
        }

    }
    public ItemResponseDto updateItem(ItemDto itemDto, String itemName) {
        ItemEntity itemEntity = itemRepository.findByName(itemName);

        if (itemEntity == null)
        {
            throw new EntityNotFoundException("item");
        }

        try
        {
            itemEntity.setName(itemDto.getName());
            itemEntity.setDescription(itemDto.getDescription());
            itemEntity.setTodoList(itemDto.getTodoList());
            itemRepository.save(itemEntity);
            return modelMapper.map(itemEntity,ItemResponseDto.class);
        }
        catch (Exception exception)
        {
            throw new EntityNotFoundException("item");
        }

    }
    public boolean deleteItem(ItemDto itemDto) {
        ItemEntity itemEntity = itemRepository.findByName(itemDto.getName());

        if (itemEntity == null)
        {
            throw new EntityNotFoundException("item");
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


    public ToDoListResponseDto createToDoList(ToDoListResponseDto ToDoListResponseDto) {
        try
        {
            var toDoList = modelMapper.map(ToDoListResponseDto, ToDoListEntity.class);
            toDoListRepository.save(toDoList);
            UserEntity user = userService.getAuthenticatedUser();
            sharingService.createBind(user.getUsername(), toDoList.getName(), true);
            return modelMapper.map(toDoList, ToDoListResponseDto.class);
        }
        catch (Exception exception)
        {
            throw new EntityNotFoundException("todolist");
        }
    }
    public ToDoListResponseDto updateToDoList(ToDoListResponseDto ToDoListResponseDto, String toDoListName) {
        ToDoListEntity toDoListEntity = toDoListRepository.findByName(toDoListName);
        try
        {
            toDoListEntity.setName(ToDoListResponseDto.getName());
            toDoListEntity.setDescription(ToDoListResponseDto.getDescription());
            toDoListRepository.save(toDoListEntity);
            return modelMapper.map(toDoListEntity,ToDoListResponseDto.class);
        }
        catch (Exception exception)
        {
            throw new EntityNotFoundException("toDoList");
        }

    }
    public boolean deleteToDoList(String name) {
        ToDoListEntity toDoListEntity = toDoListRepository.findByName(name);
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
        return new ToDoListCollectionDto(
            userListRepository.findAll().stream().
            filter(userList ->  userList.getIsOwner() && userList.getUsername() == userService.getSelf().getUsername()).
            map(userList -> new ToDoListResponseDto(userList.getToDoList(), toDoListRepository.findByName(userList.getToDoList()).getDescription())).
            toList()
        );
    }
    public UserListDto getMembersToDoList(String name) {
        return new UserListDto(
            userListRepository.findAll().
            stream().
            filter(userList -> userList.getToDoList() == name).
            map(userList -> new UserDto(userList.getUsername(), null, null)).
            toList()
        );
    }
    public RequestListDto getRequestsToDoList(String name) {
        return new RequestListDto(
            requestRepository.findAll().
            stream().
            filter(request -> request.getToDoList() == name).
            map(request -> new RequestResponseDto(request.getUsername(), request.getToDoList(), request.getText())).
            toList()
        );
    }
    public ItemListDto getItemsToDoList(String name) {
        return new ItemListDto(
            itemRepository.findAll().
            stream().
            filter(item -> item.getTodoList() == name).
            map(item -> new ItemResponseDto(item.getName(), item.getDescription(), name)).
            toList()
        );
    }
    public boolean leaveToDoList(String name) {
        List<UserListEntity> userLists = userListRepository.findAll().stream().filter(userList -> userList.getToDoList() == name && userList.getUsername() == userService.getSelf().getUsername()).toList();
        userListRepository.delete(userLists.get(0));
        return true;
    }
}
