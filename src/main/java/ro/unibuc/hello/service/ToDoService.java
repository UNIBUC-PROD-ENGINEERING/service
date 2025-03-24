package ro.unibuc.hello.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.service.SharingService;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.request.*;
import ro.unibuc.hello.dto.request.ToDoListDto;
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


    public ToDoListResponseDto createToDoList(ToDoListDto toDoListDto) {
        try
        {
            var toDoList = modelMapper.map(toDoListDto, ToDoListEntity.class);
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
    public ToDoListResponseDto updateToDoList(ToDoListDto toDoListDto, String toDoListName) {
        ToDoListEntity toDoListEntity = toDoListRepository.findByName(toDoListName);
        try
        {
            toDoListEntity.setName(toDoListDto.getName());
            toDoListEntity.setDescription(toDoListDto.getDescription());
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
