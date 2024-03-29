import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class User {
    private Integer id;
    private String name;
    private List<Integer> streams;
    public User(int id, String name, List<Integer> streams) {
        this.id = id;
        this.name = name;
        this.streams = streams;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Integer> getStreams() {
        return streams;
    }
    public void setStreams(List<Integer> streams){
        this.streams = streams;
    }
    public static LinkedHashMap<Integer,User> readUsers(String[] args){
        LinkedHashMap<Integer, User> users = new LinkedHashMap<Integer,User>();
        try (BufferedReader br = new BufferedReader(new FileReader("./src/main/resources/" + (args[2])))) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] split = line.split("\"");
                int id = Integer.parseInt(split[1]);
                String name = split[3];
                String streamsString = split[5];
                String[] streamsSplit = streamsString.split(" ");
                List<Integer> streams = new ArrayList<Integer>();
                for(String s : streamsSplit){
                    streams.add(Integer.parseInt(s));
                }
                users.put(id, new User(id,name,streams));
            } }catch (IOException e) {
            System.out.print("Eroare la citirea streamerilor");
        }
        return users;
    }
}
