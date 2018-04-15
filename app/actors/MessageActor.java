package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FeedResponse;
import data.Message;
import data.NewsAgentResponse;
import services.FeedService;
import services.NewsAgentService;

import java.util.UUID;

import static data.Message.Sender.USER;

public class MessageActor extends UntypedActor {
    //Define another actor Reference
    private final ActorRef out;

    //Self reference the Actor
    public MessageActor(ActorRef out) {
        this.out = out;
    }

    //PROPS
    public static Props props(ActorRef out) {
        return Props.create(MessageActor.class, out);
    }

    //Object of feed service and news agent service
    private FeedService feedService = new FeedService();
    private NewsAgentService newsAgentService = new NewsAgentService();
    private NewsAgentResponse newsAgentResponse=new NewsAgentResponse();
    private FeedResponse feedResponse=new FeedResponse();

    @Override
    public void onReceive(Object message) throws Throwable {
        //send back the response
        ObjectMapper objectMapper = new ObjectMapper();
        Message messageObject = new Message();
        if (message instanceof String) {
            //Message messageObject = new Message();
            messageObject.text=(String)message;
            messageObject.sender= Message.Sender.USER;
            out.tell(objectMapper.writeValueAsString(messageObject), self());
            //newsAgentResponse=newsAgentService.getNewsAgentResponse(messageObject.text,UUID.randomUUID());
            String query=newsAgentService.getNewsAgentResponse("Find "+message,UUID.randomUUID()).query;
            feedResponse=feedService.getFeedByQuery(query);
            messageObject.text=(feedResponse.title==null)?"No results found":"Showing results for : "+query;
            messageObject.feedResponse=feedResponse;
            messageObject.sender=Message.Sender.BOT;
            out.tell(objectMapper.writeValueAsString(messageObject),self());
        }
        else
        {
            messageObject.text="No results found";
            messageObject.sender= Message.Sender.BOT;
            out.tell(objectMapper.writeValueAsString(messageObject), self());
        }
    }
}
