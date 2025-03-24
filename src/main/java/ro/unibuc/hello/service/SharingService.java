package ro.unibuc.hello.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.request.*;
import ro.unibuc.hello.dto.response.*;
import ro.unibuc.hello.exception.EntityNotFoundException;

@AllArgsConstructor
@Service
public class SharingService {

    @Autowired
    private final UserListRepository userListRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final RequestRepository requestRepository;

    @Autowired
    private final ModelMapper modelMapper;

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

          
    public RequestResponseDto createRequest(RequestDto requestDto) {
        try {

            var user = userService.getAuthenticatedUser();
    
            RequestEntity newRequest = new RequestEntity(user.getUsername(), requestDto.getToDoList(), requestDto.getDescription());
            requestRepository.save(newRequest);
            return modelMapper.map(newRequest, RequestResponseDto.class);  
    
        } catch (Exception exception) {
            
            throw new EntityNotFoundException("request");
        }
    }
    

    public RequestResponseDto acceptRequest(RequestDto requestDto) {
        try {
           
            Optional<RequestEntity> requestOpt = requestRepository.findByUsernameAndToDoList(requestDto.getUsername(), requestDto.getToDoList());
            
            if (requestOpt.isPresent()) {
                
                createBind(requestDto.getUsername(), requestDto.getToDoList(), false);
                
                requestRepository.delete(requestOpt.get());
                
                return modelMapper.map(requestDto,RequestResponseDto.class);
            } else {
                throw new EntityNotFoundException("request"); 
            }
        } catch (Exception exception) {
           
            System.out.println("Error accepting request: " + exception.getMessage());
            throw new EntityNotFoundException("request"); 
        }
    }


    public boolean denyRequest(RequestDto requestDto) {
        try {
           
            Optional<RequestEntity> requestOpt = requestRepository.findByUsernameAndToDoList(requestDto.getUsername(), requestDto.getToDoList());
            
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

}
