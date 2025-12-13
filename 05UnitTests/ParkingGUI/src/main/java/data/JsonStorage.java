package data;

/**
 *
 * @author Josue Carvajal, THE ART OF PROGRAMMING, @ESPE
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class JsonStorage {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> List<T> readList(File file, TypeReference<List<T>> typeRef) {
        try {
            if (!file.exists()) return new ArrayList<>();
            return MAPPER.readValue(file, typeRef);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static <T> void writeList(File file, List<T> list) {
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) parent.mkdirs();
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(file, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


