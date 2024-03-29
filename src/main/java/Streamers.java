import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Streamers {
    private Integer streamerType;
    private Integer id;
    private String nume;
    public Streamers(Integer streamerType, Integer id, String nume) {
        this.streamerType = streamerType;
        this.id = id;
        this.nume = nume;
    }
    public Integer getStreamerType() {
        return streamerType;
    }
    public Integer getId() {
        return id;
    }
    public String getNume() {
        return nume;
    }
    public static LinkedHashMap<Integer,Streamers> readStreamers(String[] args){
        LinkedHashMap<Integer, Streamers> streamers = new LinkedHashMap<Integer,Streamers>();
        try (BufferedReader br = new BufferedReader(new FileReader("./src/main/resources/" + (args[0])))) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] split = line.split("\"");
                int streamerType = Integer.parseInt(split[1]);
                int id = Integer.parseInt(split[3]);
                String name = split[5];
                streamers.put(id, new Streamers(streamerType, id, name));
            } }catch (IOException e) {
            System.out.print("Eroare la citirea streamerilor");
        }
        return streamers;
    }
}
