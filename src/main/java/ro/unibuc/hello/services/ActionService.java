package ro.unibuc.hello.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.dtos.ActionDTO;
import ro.unibuc.hello.entities.Action;
import ro.unibuc.hello.repositories.ActionRepository;
import ro.unibuc.hello.exceptions.EntityAlreadyExistsException;
import ro.unibuc.hello.exceptions.EntityNotFoundException;

@Component
public class ActionService {

    @Autowired
    private ActionRepository actionRepository;

    public ActionDTO addAction(String code, String description) throws EntityAlreadyExistsException{

        var existingAction = actionRepository.findById(code);
        if (existingAction.isPresent()) {
            throw new EntityAlreadyExistsException("Action");
        }

        var action = new Action(code, description);
        actionRepository.save(action);
        return action.toDTO();
    }

    public List<ActionDTO> getActions() {
        List<Action> actions = actionRepository.findAll();
        return actions.stream().map(Action::toDTO).collect(Collectors.toList());
    }

    public ActionDTO getActionById (String code) throws EntityNotFoundException {
        Action action = actionRepository.findById(code).orElseThrow(
                () -> new EntityNotFoundException("Action")
        );
        return action.toDTO();
    }
}
