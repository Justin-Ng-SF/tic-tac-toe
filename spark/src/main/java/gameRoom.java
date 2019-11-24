import org.eclipse.jetty.websocket.api.Session;



import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class gameRoom {
     static Queue<PlayerDto> matchmakingQueue = new LinkedList<>();


     public void kickPlayer(Session client){

          Iterator<PlayerDto> target = matchmakingQueue.iterator();
          while (target.hasNext()){
               if(target.next().client == client){

                    target.remove();

                    System.out.println("Player has been kicked, Player in gameRoom: " + matchmakingQueue.size() );
               }
          }
     }




}
