import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Streams {
    private Integer streamType;
    private Integer id;
    private Integer streamGenre;
    private Long noOfStreams;
    private Integer streamerId;
    private Long length;
    private Long dateAdded;
    private String name;
    public Streams(Integer streamType, Integer id, Integer streamGenre, long noOfStreams, Integer streamerId, long length, long dateAdded, String name) {
        this.streamType = streamType;
        this.id = id;
        this.streamGenre = streamGenre;
        this.noOfStreams = noOfStreams;
        this.streamerId = streamerId;
        this.length = length;
        this.dateAdded = dateAdded;
        this.name = name;
    }
    public Integer getStreamType() {
        return streamType;
    }
    public Integer getId() {
        return id;
    }
    public Integer getStreamGenre() {
        return streamGenre;
    }
    public Long getNoOfStreams() {
        return noOfStreams;
    }
    public void setNoOfStreams(Long noOfStreams) {
        this.noOfStreams = noOfStreams;
    }
    public Integer getStreamerId() {
        return streamerId;
    }
    public Long getLength() {
        return length;
    }
    public Long getDateAdded() {
        return dateAdded;
    }
    public String getName() {
        return name;
    }
    public static  LinkedHashMap<Integer,Streams> readStreams(String[] args){
        LinkedHashMap<Integer, Streams> streams = new LinkedHashMap<Integer,Streams>();
        try (BufferedReader br = new BufferedReader(new FileReader("./src/main/resources/" + (args[1])))) {
            String line;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] split = line.split("\"");
                int streamerType = Integer.parseInt(split[1]);
                int id = Integer.parseInt(split[3]);
                int streamerGenre = Integer.parseInt(split[5]);
                long noOfStreams = Long.parseLong(split[7]);
                int streamerId = Integer.parseInt(split[9]);
                long length = Long.parseLong(split[11]);
                long dateAdded = Long.parseLong(split[13]);
                String name = split[15];
                streams.put(id, new Streams(streamerType, id, streamerGenre, noOfStreams, streamerId, length, dateAdded, name));
            }
        }catch (IOException e) {
            System.out.print("Eroare la citirea streamerilor");
        }
        return streams;
    }
    public String calculeazaLength(Long length){
        Duration Duration = java.time.Duration.ofSeconds(length);
        int hours = Duration.toHoursPart();
        int minutes = Duration.toMinutesPart();
        int seconds = Duration.toSecondsPart();
        if(length < 3600)
            return String.format("%02d:%02d",minutes, seconds);
        else
            return String.format("%02d:%02d:%02d",hours,minutes,seconds);
    }
    public static String calculeazaData(long dateAdded){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(dateAdded * 1000L);
        return dateFormatter.format(date);
    }
    public String toString(LinkedHashMap<Integer, Streamers> streamers){
        return "{\"id\":\""+this.id +
                "\",\"name\":\""+this.name+
                "\",\"streamerName\":\""+streamers.get(this.streamerId).getNume()+
                "\",\"noOfListenings\":\""+this.noOfStreams+
                "\",\"length\":\""+calculeazaLength(this.length)+
                "\",\"dateAdded\":\""+calculeazaData(this.dateAdded)+
                "\"}";
    }
    public static List<Streams> sortareStreams(List<Streams> streamsList){
        Collections.sort(streamsList, new Comparator<Streams>() {
            @Override
            public int compare(Streams o1, Streams o2) {
                return o2.getNoOfStreams().compareTo(o1.getNoOfStreams());
            }
        });
        return streamsList;
    }
    public static List<Streams> sortareStreamsSurpriza(List<Streams> streamsList){
        Collections.sort(streamsList, new Comparator<Streams>() {
            @Override
            public int compare(Streams o1, Streams o2) {
                String s1 = Streams.calculeazaData(o1.getDateAdded());
                String s2 = Streams.calculeazaData(o2.getDateAdded());
                if(s1.compareTo(s2) != 0)
                    return s1.compareTo(s2);
                else
                    return o2.getNoOfStreams().compareTo(o1.getNoOfStreams());
            }
        });
        return streamsList;
    }
}
