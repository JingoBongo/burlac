Ну, привет. 

Описание лабы будет на английском ниже этого текста, однако, я не хотел бы, чтобы ты игнорировал то, что я напишу.

Поскольку сейчас я никуда не опаздываю и за моей спиной нет очереди одногруппников, хочу пригласить тебя на очередную беседу  про этот способ сказать "Не верю" и забить на все аргументы студента, про похожесть кода (на код Эда, видел бы ты его лицо, 
когда я рассказал ему. Что-то вроде:"И че? Задание похожее, условия те же" ). Давай еще раз поговорим про это? Покажи 
похожий код, спроси Эда, лез ли я в его код. Поговорим про то, что раз у меня с Димой похожий код и мы часть вопросов решали 
сообща, то это - резкая проблема и вообще а-та-та. Поспрашивай у меня как работает мой код и убедись, что я в нем разбираюсь? 
Ведь внаглую чужой код ни один из нас не брал, не копировал, и, по-хорошему, снижать из-за этого оценку, еще и без пруфов,
некрасиво.



=/=/=/=/=/=/=/==/=/=/=/=/=/=/==/=/=/=/=/=/=/==/=/=/=/=/=/=/==/=/=/=/=/=/=/==/=/=/=/=/=/=/==/=/=/=/=/=/=/==/=/=/=/=/=/=/=

Now, back to Java.
Let's assume I am already connected to server and I want to get all routes/data from it.

I launch MultiTheradServer, because in main it has following lines:

        List<String> arlist = new ArrayList<>();
        arlist.add(Utils.homeUrl);
        getXAccessToken();
        Utils.getAllLinks(arlist);
        
My method of taking all the routes and data from server is recursive and as argument it needs a list with already known 
routes. So first route is Home. Using list with 1 element lets me start the recursive method to get all routes and data. 
I'll talk about this method later. 
Also in main getXAccessToken method is launched, it gets needed auth token one time.
next.

        while(Thread.activeCount() > 2){}
        Utils.completeListRecords();
 
~When you launched MultiThreadServer, waited to get all routes (it will print time spent and you are ready to go)
        then just launch Client/Main class to get a client as well.
        As commands you can use "get record by column_name value" or "get random", "get all not null"
        
Then there is a while loop that lets main wait untill all data in additional threads is taken.
completeListRecords method simply converts all plain text that I got from routes to list of Records. (as all data about 1 
person is a Record.). It sees what mime type each route had to convert the data properly. (Also forgot to tell you, there is 
at least one route with an extra comma, so object mapper with no extra code gets an error). Once divided data to mime type, 
method uses object mappers.
next.

       try (ServerSocket server = new ServerSocket(3345)) {
                  System.out.println("Server socket created, command console reader for listen to server commands");

                  while (!server.isClosed()) {
                      Socket client = server.accept();
                      executeIt.execute(new MonoThreadClientHandler(client));
                      System.out.print("Connection accepted.");
                  }

                  executeIt.shutdown();
              }
*If this server-client code appears similar, I will provide you a link in the end with the source guide, which basically 
became the core of this server-client part. Maybe other students used it too. Noone will blame them just because I found this
webpage doesn't mean others should not use it.*
              
Having in mind that MultiThreadServer has static Thread pool already initialized,
it tries to create a server socket and runs a while loop. If server accepts some client connection it will send this client
to a MonoThreadClientHandler in a threadpool.
And Once server is closed, threadpool is shutdown as well.

To run client connection you might run main in Client or Main class, as they both initialize a Client object, which connects 
to the server.

So, now when I described MultiThreadServer let's move to Utils(1) with all handy methods, MonoThreadClientHandler(2) and 
Client(3)


(1) Utils. 
  1) getXAccessToken. It simply executes a request to /register and puts the auth token value to a static variable.
  2) getAllTextFromPage. It returns all text from page in a string without changing it. It uses Http request and ResponseHanler
      in general. 
  3) GetDataset from csv/json/yamx/xml. It needs a DTO obejct, then it maps it and gets a list of Records. I hope there will 
      be no questions about what is a Data Transfer Object, why are they needed and what is de/serialization. It just lets me
      have data from page in a structured way. For example a Record has someone's personal data like id, email etc.
  4) completeListRecords method just divides data from routes by mime type and uses getDataSet methods to put Records in 
      one big static list.
  5) getRandomRecord basically picks one random record from that one big list mentioned above
  6) getRecordBy (which I changed by your request to get record by any column name) Lets me do exactly what it says.
      It needs column name and value as arguments, then in a switch it finds and returns needed record. Or a record with
      "NO SUCH THING" or whatever message if it fails to find something. Not a very optimized method code-size-wize because 
      I had to do it when I was responding to you.
  7) getAllNotNullId returns all Records with id != null.
  8) getAllLinks is the most interesting method here. It needs a list of routes as argument and it gets all routes and data
      from webpage, also launching itself recursively in threads.
      simplified pseudo-code here:
      
      
      public static void getAllLinks(List<String> givenRoutes) {
            for(int i = 0; i < givenRoutes.size(); i++) {
                new Thread(() -> {
                    try {
                        getAllText from route i;
                        map it's text to a DTO object;
                        add result to allObjects static list;
                        
                        if(found any links on the webpage){
                           launch getAllLinks() with newly found routes as list argument;
                        }
                    } catch (some exceptions) {
                       print stack trace;
                    }
                }).start() the anonymous thread;
            }
        }else{
            Print("empty route list / something went really wrong");
        }
    }
      
      
      
      
 (2)MonoThreadClientHandler handles 1 connection with client. First it gets client isntance from it's constructor.
    Then it Tries to create Data I/O streams from client socket.
    While cliendDialog is not closed it reads data from input, handles it throught methods like getRandomRecord from 
    Utils.
    On "quit" commands it closes connection, and it has a flush() method to force response to be written out.
    
 (3)Client connects to local throught a given port. It does basically the same thing as MonoThreadClientHandler, except it 
    gets text to send to the server from System.in (keyboard), does not handle any commands, only gets response from server 
    on what I requested. 
      
      
