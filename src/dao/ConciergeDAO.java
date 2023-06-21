package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import model.Concierge;

public class ConciergeDAO {

    private ArrayList<Concierge> concierges = new ArrayList<>();
    private final String path = "resources/concierges.json";
    private final ObjectMapper objMapper = new ObjectMapper();
    private final File file = new File(path);

    public ConciergeDAO() {
    }
    
    
}
