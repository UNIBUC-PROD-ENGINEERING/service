package ro.unibuc.hello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ro.unibuc.hello.data.ActionEntity;
import ro.unibuc.hello.data.ActionRepository;

@Component
public class ActionService {

    @Autowired
    private ActionRepository actionRepository;

    public ActionEntity addAction(String code, String description){

        var existingAction = actionRepository.findById(code);
        if(existingAction.isEmpty()){
            try {
                throw new Exception("Action already exists.");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        var action = new ActionEntity(code, description);
        actionRepository.save(action);
        return action;
    }

    public List<ActionEntity> getActions() {
        List<ActionEntity> actions = actionRepository.findAll();
        return actions;
    }

    //ToDo: Check if action is null and create exception
    public ActionEntity getActionById(String code) {
        ActionEntity action = actionRepository.findById(code).get();
        return action;
    }
}
