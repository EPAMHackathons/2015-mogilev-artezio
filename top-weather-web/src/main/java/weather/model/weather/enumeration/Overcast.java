package weather.model.weather.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: gcherkasov
 * Date: 20.11.14
 * Time: 10:37
 * To change this template use File | Settings | File Templates.
 */
public enum Overcast {


    OVC("Сплошная облачность"),
    BKN("Облачно с прояснениями"),
    SCT("Переменная облачность"),
    FEW("Небольшая облачность"),
    NSC("Несущественная облачность"),
    SKC("Ясно"),
    NONE("Неизвестно");

    private Map<String, Overcast> overCastName;
    private String name;

    Overcast(String name) {
        this.name = name;
    }

    public Overcast getOverCastByName(String name) {
        if (overCastName == null)
            overCastName = new HashMap<String, Overcast>();
        for (Overcast overcast : Overcast.values()) {
            overCastName.put(overcast.getName(), overcast);
        }
        Overcast overcast = overCastName.get(name);

        return overcast == null ? NONE : overcast;

    }

    public String getName() {
        return name;
    }
}
