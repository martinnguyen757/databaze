import java.io.*;
import java.util.Map;

public interface ZalohovaciStrategie {
    void uloz(Map<Integer, Zamestnanec> databaze, int idCounter, String cesta) throws IOException;
    Object[] nacti(String cesta) throws IOException, ClassNotFoundException;

    
    default void zalohujDoSql(Map<Integer, Zamestnanec> databaze) {
        System.out.println("[SQL MODULE] Připojování k databázi...");
        System.out.println("[SQL MODULE] Proveden záložní DUMP " + databaze.size() + " záznamů.");
    }
}

class BinarniZaloha implements ZalohovaciStrategie {
    @Override
    public void uloz(Map<Integer, Zamestnanec> databaze, int idCounter, String cesta) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(cesta))) {
            oos.writeObject(databaze);
            oos.writeInt(idCounter);
        }
        zalohujDoSql(databaze); 
    }

    @Override
    public Object[] nacti(String cesta) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cesta))) {
            return new Object[]{ois.readObject(), ois.readInt()};
        }
    }
}
