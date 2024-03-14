package ro.unibuc.hello.dto;

import ro.unibuc.hello.common.EffectTypes;

import java.util.Arrays;

public class Statement {
    private EffectTypes effect;
    private String[] actions;

    public Statement() {
    }

    public Statement(EffectTypes effect, String[] actions) {
        this.effect = effect;
        this.actions = actions;
    }

    public EffectTypes getEffect() {
        return effect;
    }

    public void setEffect(EffectTypes effect) {
        this.effect = effect;
    }

    public String[] getActions() {
        return actions;
    }

    public void setActions(String[] actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return String.format("Statement[effect='%s', actions='%s']",
                effect, Arrays.stream(actions).reduce("", (a, b) -> a +", "+ b));
    }
}
