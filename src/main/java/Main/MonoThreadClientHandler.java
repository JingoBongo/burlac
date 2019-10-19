package Main;

import poms.Record;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable {

    private static Socket clientDialog;

    public MonoThreadClientHandler(Socket client) {
        MonoThreadClientHandler.clientDialog = client;
    }

    @Override
    public void run() {

        try {

            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            System.out.println("DataInputStream created");
            System.out.println("DataOutputStream  created");

            while (!clientDialog.isClosed()) {
                System.out.println("Server reading from channel");
                String entry = in.readUTF();
                System.out.println("READ from clientDialog message - " + entry);

                if (entry.equalsIgnoreCase("quit")) {
                    System.out.println("Client initialize connections suicide ...");
                    out.writeUTF("Server reply - " + entry + " - OK");
                    break;
                }



                if(entry.equals("get random")){
                    System.out.println("Server try writing to channel");
                    out.writeUTF("Server reply - " + Utils.getRandomRecord().toString() + " - OK");
                    System.out.println("Server Wrote message to clientDialog.");
                }else if(entry.contains("get record by ")){   // get0record1by2_columnname_3_value_4
                    String[] idarr = entry.split(" ");
                    String columnname = idarr[3];
                    String value = idarr[4];
                    StringBuilder st = new StringBuilder();
                    for(Record ree : Utils.getRecordBy(columnname, value)){
                        st.append(ree.toString()+"\n");
                    }
                    System.out.println("Server try writing to channel");
                    out.writeUTF("Server reply - " + st.toString() + " - OK");
                    System.out.println("Server Wrote message to clientDialog.");
                }else if(entry.equals("get all not null")){
                    System.out.println("Server try writing to channel");
                    StringBuilder str = new StringBuilder();
                    for(Record ree : Utils.getAllNotNullId()){
                        str.append(ree.toString()+"\n");
                    }
                    out.writeUTF("Server reply - " + str.toString() + " - OK");
                    System.out.println("Server Wrote message to clientDialog.");
                }else{
                    System.out.println("Server try writing to channel");
                    out.writeUTF("Server reply - " + entry + " - OK. Command is not recognized tho.");
                    System.out.println("Server Wrote message to clientDialog.");
                }

                out.flush();

            }


            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");
            in.close();
            out.close();
            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
