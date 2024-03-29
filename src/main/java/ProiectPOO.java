import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class ProiectPOO {
    //folosesc singleton pentru a nu avea mai multe instante ale clasei
    //deoarece este necesara doar una singura
    private static ProiectPOO instanta;

    private ProiectPOO() {
    }

    public static ProiectPOO getInstance() {
        if (instanta == null) {
            instanta = new ProiectPOO();
        }
        return instanta;
    }

    public static void main(String[] args) {

        if (args == null) {
            System.out.println("Nothing to read here");
        } else {

            LinkedHashMap<Integer, Streams> streams = Streams.readStreams(args);
            LinkedHashMap<Integer, Streamers> streamers = Streamers.readStreamers(args);
            LinkedHashMap<Integer, User> users = User.readUsers(args);

            try (BufferedReader br = new BufferedReader(new FileReader("./src/main/resources/" + (args[3])))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] split = line.split(" ");

                    if (split[1].equals("LIST")) {
                        int id = Integer.parseInt(split[0]);
                        if (users.get(id) != null) {
                            List<Integer> lista = users.get(id).getStreams();
                            if(lista.size() == 1){
                                System.out.println("[" + streams.get(lista.get(0)).toString(streamers) + "]");
                            }else{
                                System.out.print("[");
                                for(int i = 0; i < lista.size(); i++)
                                    if(i == lista.size() - 1)
                                        System.out.print(streams.get(lista.get(i)).toString(streamers));
                                    else
                                        System.out.print(streams.get(lista.get(i)).toString(streamers) + ",");
                                    System.out.println("]");
                                }
                            } else {
                                if (streamers.get(id) != null) {
                                    int contor =0;
                                    for (Streams s : streams.values()) {
                                        if(s.getStreamerId() == id){
                                            contor++;
                                        }
                                    }
                                    System.out.print("[");
                                    for(Streams s : streams.values()){
                                        if(s.getStreamerId() == id){
                                            if(contor == 1){
                                                System.out.print(s.toString(streamers));
                                            }else{
                                                System.out.print(s.toString(streamers) + ",");
                                                contor--;
                                            }
                                        }
                                    }
                                    System.out.println("]");
                                }
                            }
                    } else {
                        if (split[1].equals("ADD")) {
                            int streamerId = Integer.parseInt(split[0]);
                            int streamType = Integer.parseInt(split[2]);
                            int idStream = Integer.parseInt(split[3]);
                            int streamGenre = Integer.parseInt(split[4]);
                            long length = Long.parseLong(split[5]);
                            String name = split[6];
                            for (int i = 7; i < split.length; i++) {
                                name = name + " " + split[i];
                            }
                            int noOfStreams = 0;
                            Instant instant = Instant.now();
                            long dateAdded = instant.getEpochSecond();

                            streams.put(idStream, new Streams(streamType, idStream, streamGenre,
                                    noOfStreams, streamerId, length, dateAdded, name));
                        } else {
                            if(split[1].equals("DELETE")) {
                                int id = Integer.parseInt(split[0]);
                                for(User u : users.values()){
                                    List<Integer> listNew = new ArrayList<>();
                                    List<Integer> listOld = u.getStreams();
                                    for(int i = 0 ; i < listOld.size(); i++)
                                        if(listOld.get(i) != id)
                                            listNew.add(listOld.get(i));
                                    u.setStreams(listNew);
                                }
                                streams.remove(id);
                            }else{
                                if(split[1].equals("LISTEN")){
                                    int userId = Integer.parseInt(split[0]);
                                    int streamId = Integer.parseInt(split[2]);
                                    UserDecorator userNou = new UserDecorator(users.get(userId));
                                    StreamsDecorator streamNou = new StreamsDecorator(streams.get(streamId),userNou);
                                    userNou.listen(streamId);
                                }else{
                                    if(split[1].equals("RECOMMEND")){
                                        int userId = Integer.parseInt(split[0]);
                                        String tip = split[2];
                                        int streamType;
                                        if(tip.equals("SONG"))
                                            streamType = 1;
                                        else{
                                            if(tip.equals("PODCAST"))
                                                streamType = 2;
                                            else
                                                streamType = 3;
                                        }
                                        User user = users.get(userId);
                                        List<Integer> listaIdStream = user.getStreams();
                                        List<Streams> listaStreams = new ArrayList<>();
                                        for(Integer id: listaIdStream){
                                            Integer streamerId = streams.get(id).getStreamerId();
                                            for(Streams s : streams.values()){
                                                if(s.getStreamerId() == streamerId && s.getStreamType() == streamType
                                                        && !listaIdStream.contains(s.getId())){
                                                    listaStreams.add(s);
                                                }
                                            }
                                        }
                                        int nrElemente = listaStreams.size();
                                        Streams.sortareStreams(listaStreams);
                                        System.out.print("[");
                                        int contor = 0;
                                        for(Streams s : listaStreams){
                                            if(contor < 5){
                                                if(contor < nrElemente - 1)
                                                    System.out.print(s.toString(streamers) + ",");
                                                else
                                                    System.out.print(s.toString(streamers));
                                            }
                                                contor++;
                                        }
                                        System.out.println("]");
                                    }else{
                                        if(split[1].equals("SURPRISE")){
                                            int userId = Integer.parseInt(split[0]);
                                            String tip = split[2];
                                            int streamType;
                                            if(tip.equals("SONG"))
                                                streamType = 1;
                                            else{
                                                if(tip.equals("PODCAST"))
                                                    streamType = 2;
                                                else
                                                    streamType = 3;
                                            }
                                            User user = users.get(userId);
                                            List<Integer> listaIdStream = user.getStreams();
                                            List<Streams> listaStreams = new ArrayList<>();
                                            List<Streams> listaStreamsNoua = new ArrayList<>();
                                            for(Streams s : streams.values())
                                                if(s.getStreamType() == streamType)
                                                    listaStreams.add(s);
                                            for(int i = 0; i < listaStreams.size(); i++)
                                                if(listaIdStream.contains(listaStreams.get(i).getStreamerId())) {
                                                    listaStreamsNoua.add(listaStreams.get(i));
                                                }
                                            int nrElemente = listaStreamsNoua.size();
                                            Streams.sortareStreamsSurpriza(listaStreamsNoua);
                                            System.out.print("[");
                                            int contor = 0;
                                            for(Streams s : listaStreamsNoua){
                                                if(contor < 3){
                                                    if(contor < nrElemente - 1)
                                                        System.out.print(s.toString(streamers) + ",");
                                                    else
                                                        System.out.print(s.toString(streamers));
                                                }
                                                contor++;
                                            }
                                            System.out.println("]");
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            } catch (IOException e) {
                System.out.print("Eroare la citirea din fisier");
            }

        }
    }
}