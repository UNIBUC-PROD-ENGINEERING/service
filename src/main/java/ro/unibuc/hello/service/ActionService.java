package ro.unibuc.hello.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.ActionEntity;
import ro.unibuc.hello.data.ActionRepository;
import ro.unibuc.hello.dto.Action;
import ro.unibuc.hello.exception.EntityAlreadyExistsException;
import ro.unibuc.hello.exception.EntityNotFoundException;

@Component
public class ActionService {

    @Autowired
    private ActionRepository actionRepository;

    public Action addAction(String code, String description) throws EntityAlreadyExistsException{

        var existingAction = actionRepository.findById(code);
        if (existingAction.isPresent()) {
            throw new EntityAlreadyExistsException("Action already exists.");
        }

        var action = new ActionEntity(code, description);
        actionRepository.save(action);
        return new Action(action.actionCode, action.actionDescription);
    }

    public List<Action> getActions() {
        List<ActionEntity> actionEntities = actionRepository.findAll();
        return actionEntities.stream().map(action -> new Action(action.actionCode, action.actionDescription)).collect(Collectors.toList());
    }

    public Action getActionById (String code) throws EntityNotFoundException {
        ActionEntity action = actionRepository.findById(code).orElseThrow(
                () -> new EntityNotFoundException("Action not found.")
        );
        return new Action(action.actionCode, action.actionDescription);
    }
}
