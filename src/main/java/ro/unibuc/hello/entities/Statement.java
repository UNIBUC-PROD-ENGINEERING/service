package ro.unibuc.hello.entities;

import lombok.*;
import ro.unibuc.hello.common.EffectTypes;

import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statement {
    private EffectTypes effect;
    private String[] actions;

    @Override
    public String toString() {
        return String.format("Statement[effect='%s', actions='%s']",
                effect, Arrays.stream(actions).reduce("", (a, b) -> a +", "+ b));
    }
}
