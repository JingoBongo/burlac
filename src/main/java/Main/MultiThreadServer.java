package Main;

import poms.Record;
import poms.RouteDTO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Main.Utils.*;


public class MultiThreadServer {

    static ExecutorService executeIt = Executors.newFixedThreadPool(10);


    public static void main(String[] args) throws IOException, InterruptedException {

        long start = new Date().getTime();
        List<String> arlist = new ArrayList<>();
        arlist.add(Utils.homeUrl);
        getXAccessToken();
        Utils.getAllLinks(arlist);


        while(Thread.activeCount() > 2){}
        System.out.println(new Date().getTime()-start);
        Utils.completeListRecords();
//        System.out.println("size "+ allRecords.size());

        //
        //get random
        //get record by id
        //get all not null
        //

        try (ServerSocket server = new ServerSocket(3345)) {
            System.out.println("Server socket created, command console reader for listen to server commands");

            while (!server.isClosed()) {
                Socket client = server.accept();
                executeIt.execute(new MonoThreadClientHandler(client));
                System.out.print("Connection accepted.");
            }

            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}