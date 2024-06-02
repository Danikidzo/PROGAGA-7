package DataBase;

import java.util.HashSet;
import java.util.Set;

public class IdManager {
    public static Set<Long> ListID;

    static long current_id = 1L;

    static {
        ListID = new HashSet<>();
    }

    public static void AddId(Long id) {
        ListID.add(id);
    }

    public static void RemoveId(Long id) {
        ListID.remove(id);
    }

    public static Long GetNewId() {
        current_id = 1L;
        while (ListID.contains(Long.valueOf(current_id)))
            current_id++;
        return Long.valueOf(current_id);
    }
}