package ro.unibuc.hello.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.request.ItemDto;
import ro.unibuc.hello.dto.response.ItemResponseDto;
import org.modelmapper.ModelMapper;

class ToDoServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ToDoListRepository toDoListRepository;

    @Mock
    private UserService userService;

    @Mock
    private SharingService sharingService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ToDoService toDoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createItem_shouldCreateItem_whenItemExists() {
        // Arrange
        ItemDto itemDto = new ItemDto("itemName", "description", "todoListName");
        ItemEntity itemEntity = new ItemEntity(itemDto.getName(), itemDto.getDescription(), itemDto.getTodoList());
        when(itemRepository.findByName(itemDto.getName())).thenReturn(itemEntity);
        when(modelMapper.map(any(ItemEntity.class), eq(ItemResponseDto.class))).thenReturn(new ItemResponseDto("itemName", "description", "todoListName"));

        // Act
        ItemResponseDto result = toDoService.createItem(itemDto);

        // Assert
        assertNotNull(result);
        assertEquals("itemName", result.getName());
    }

    @Test
    void deleteItem_shouldDeleteItem_whenItemExists() {
        // Arrange
        ItemDto itemDto = new ItemDto("itemName", "description", "todoListName");
        ItemEntity itemEntity = new ItemEntity(itemDto.getName(), itemDto.getDescription(), itemDto.getTodoList());
        when(itemRepository.findByName(itemDto.getName())).thenReturn(itemEntity);

        // Act
        boolean result = toDoService.deleteItem(itemDto);

        // Assert
        assertTrue(result);
        verify(itemRepository, times(1)).delete(itemEntity);
    }
}
