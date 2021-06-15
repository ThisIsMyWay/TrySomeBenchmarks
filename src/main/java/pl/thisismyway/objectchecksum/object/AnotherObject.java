package pl.thisismyway.objectchecksum.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
public class AnotherObject {
    private String anotherObjectString;

    @Override
    public String toString() {
        return "AnotherObject{" +
                "anotherObjectString='" + anotherObjectString + '\'' +
                '}';
    }
}
