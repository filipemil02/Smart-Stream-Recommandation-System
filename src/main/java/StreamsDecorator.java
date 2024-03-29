public class StreamsDecorator extends Streams{
    UserDecorator user;
    Streams stream = this;
    public StreamsDecorator(Streams stream,UserDecorator user){
        super(stream.getStreamType(), stream.getId(), stream.getStreamGenre(), stream.getNoOfStreams(), stream.getStreamerId(), stream.getLength(), stream.getDateAdded(), stream.getName());
        this.user = user;
        this.user.atachObservator(this);
        this.stream = stream;
    }
    public void update(){
        this.stream.setNoOfStreams(this.stream.getNoOfStreams() + 1);
    }
}
