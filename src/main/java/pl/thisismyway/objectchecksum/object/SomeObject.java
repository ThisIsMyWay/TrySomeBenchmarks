package pl.thisismyway.objectchecksum.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class SomeObject {
    private Boolean logicValue;
    private String stringValue;
    private Integer numericValue;
    private AnotherObject anotherObject;

    @Override
    public String toString() {
        return "SomeObject{" +
                "logicValue=" + logicValue +
                ", stringValue='" + stringValue + '\'' +
                ", numericValue=" + numericValue +
                ", anotherObject=" + anotherObject +
                '}';
    }
}
