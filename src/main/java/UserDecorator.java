public class UserDecorator extends User{
    public StreamsDecorator stream;
    protected User user;
    public UserDecorator(User user){
        super(user.getId(), user.getName(),user.getStreams());
        this.user = user;
    }
    public void atachObservator(StreamsDecorator stream){
        this.stream = stream;
    }
    public void listen(int streamId){
        user.getStreams().add(streamId);
        notifyObservator();
    }
    public void notifyObservator(){
        stream.update();
    }
}
