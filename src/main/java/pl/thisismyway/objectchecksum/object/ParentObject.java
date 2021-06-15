package pl.thisismyway.objectchecksum.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@Getter
@Setter
public class ParentObject {
    private SomeObject someObject;
    private List<SomeObject> someObjectList;
    private Map<String, SomeObject> stringSomeObjectMap;

    @Override
    public String toString() {
        return "ParentObject{" +
                "someObject=" + someObject +
                ", someObjectList=" + someObjectList +
                ", stringSomeObjectMap=" + stringSomeObjectMap +
                '}';
    }
}
