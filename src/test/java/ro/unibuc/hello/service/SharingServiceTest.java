package ro.unibuc.hello.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.request.*;
import ro.unibuc.hello.dto.response.*;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.service.SharingService;
import ro.unibuc.hello.service.UserService;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SharingServiceTest {

    @Mock
    private UserListRepository userListRepository;

    @Mock
    private UserService userService;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SharingService sharingService;

    @Test
    public void testCreateBind_Success() {
        when(userListRepository.findByUsernameAndToDoListAndIsOwner("user", "list", true))
                .thenReturn(Optional.empty());

        boolean result = sharingService.createBind("user", "list", true);

        assertTrue(result);
        verify(userListRepository, times(1)).save(any(UserListEntity.class));
    }

    @Test
    public void testCreateBind_Failure() {
        when(userListRepository.findByUsernameAndToDoListAndIsOwner("user", "list", true))
                .thenReturn(Optional.of(new UserListEntity()));

        boolean result = sharingService.createBind("user", "list", true);

        assertFalse(result);
        verify(userListRepository, never()).save(any(UserListEntity.class));
    }

    @Test
    public void testDeleteBind_Success() {
        when(userListRepository.findByUsernameAndToDoList("user", "list"))
                .thenReturn(Optional.of(new UserListEntity()));

        boolean result = sharingService.deleteBind("user", "list");

        assertTrue(result);
        verify(userListRepository, times(1)).delete(any(UserListEntity.class));
    }

    @Test
    public void testDeleteBind_Failure() {
        when(userListRepository.findByUsernameAndToDoList("user", "list"))
                .thenReturn(Optional.empty());

        boolean result = sharingService.deleteBind("user", "list");

        assertFalse(result);
        verify(userListRepository, never()).delete(any(UserListEntity.class));
    }

    @Test
    public void testCreateRequest_Success() {
        // Create RequestDto and expected objects
        RequestDto requestDto = new RequestDto("creatorUser", "list", "desc");
        RequestEntity requestEntity = new RequestEntity("creatorUser", "list", "desc");
        RequestResponseDto responseDto = new RequestResponseDto();
    
        // Mock authenticated user
        UserEntity user = new UserEntity("userId", "creatorUser", "creatorEmail@example.com", "password", "USER", null, null);
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(modelMapper.map(any(RequestEntity.class), eq(RequestResponseDto.class))).thenReturn(responseDto);
    
        // Call the service method
        RequestResponseDto result = sharingService.createRequest(requestDto);
    
        // Verify the result and interactions
        assertNotNull(result);
        verify(requestRepository, times(1)).save(any(RequestEntity.class));
    }
    
    @Test
    public void testCreateRequest_ThrowsException() {
        // Create RequestDto
        RequestDto requestDto = new RequestDto("creatorUser", "list", "desc");
    
        // Mock exception when getting authenticated user
        when(userService.getAuthenticatedUser()).thenThrow(new RuntimeException());
    
        // Call the service method and verify exception
        assertThrows(EntityNotFoundException.class, () -> sharingService.createRequest(requestDto));
    }
    
    @Test
    public void testDenyRequest_Success() {
        // Create RequestDto and mock RequestEntity
        RequestDto requestDto = new RequestDto("creatorUser", "list", "desc");
        RequestEntity requestEntity = new RequestEntity("creatorUser", "list", "desc");
    
        // Mock repository behavior
        when(requestRepository.findByUsernameAndToDoList("creatorUser", "list")).thenReturn(Optional.of(requestEntity));
    
        // Call the service method
        boolean result = sharingService.denyRequest(requestDto);
    
        // Verify the result and interactions
        assertTrue(result);
        verify(requestRepository, times(1)).delete(requestEntity);
    }
    
    @Test
    public void testDenyRequest_Failure() {
        // Create RequestDto
        RequestDto requestDto = new RequestDto("creatorUser", "list", "desc");
    
        // Mock repository behavior
        when(requestRepository.findByUsernameAndToDoList("creatorUser", "list")).thenReturn(Optional.empty());
    
        // Call the service method
        boolean result = sharingService.denyRequest(requestDto);
    
        // Verify the result and interactions
        assertFalse(result);
        verify(requestRepository, never()).delete(any(RequestEntity.class));
    }

    @Test
    void testAcceptRequest_failure(){
        when(requestRepository.findByUsernameAndToDoList(any(),any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->sharingService.acceptRequest(new RequestDto()));
    }

    @Test
    void testAcceptRequest_throwsException(){
        when(requestRepository.findByUsernameAndToDoList(any(),any())).thenThrow(new RuntimeException());
        assertThrows(EntityNotFoundException.class,()->sharingService.acceptRequest(new RequestDto()));
    }

    @Test
    void denyRequest_ShouldReturnFalse_WhenExceptionOccurs() {
        when(requestRepository.findByUsernameAndToDoList(anyString(), anyString()))
                .thenThrow(new RuntimeException("Database error"));

        boolean result = sharingService.denyRequest(new RequestDto());

        assertFalse(result, "Expected denyRequest to return false when an exception occurs");
    }
    
}