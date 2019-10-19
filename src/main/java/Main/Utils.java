package Main;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import poms.Record;
import poms.RouteDTO;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static String defaultUrl = "http://localhost:5000";
    public static String homeUrl = "http://localhost:5000/home";
    public static String xAccessToken = "X-Access-Token";
    public static List<String> allRoutes = new ArrayList<>();
    public static String tokenValue = null;
    public static ArrayList<RouteDTO> allObjects = new ArrayList<>();
    public static ArrayList<Record> allRecords = new ArrayList<>();



    public static void getXAccessToken() throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://localhost:5000/register");
        org.apache.http.HttpResponse response = client.execute(request);
        ResponseHandler<String> handler = new BasicResponseHandler();
        String body = handler.handleResponse(response);
       // System.out.println(body);
        String[] trimmed = body.split("\"");
        if(Utils.tokenValue == null){
            Utils.tokenValue = trimmed[3];
        }
    }







    public static String getAllTextFromPage(String url) throws IOException {
        ResponseHandler<String> handler = new BasicResponseHandler();
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        request.addHeader(xAccessToken, tokenValue);
        HttpResponse response = client.execute(request);
        String text = handler.handleResponse(response);
        ((CloseableHttpClient) client).close();
        return text;
    }

public static List<Record> getDatasetFromCsvText(RouteDTO car){
    List<String> l  = Arrays.asList( car.getData().split("\n"));
    String allData = car.getData();
    int numberOfRecods = l.size()-1;
    List<Record> rList = new ArrayList<>();
    int b;
    if(allData.contains("username")){
        b = 0;
    }else {
        b = 1;
    }
    for(int i = 0; i< numberOfRecods; i++){
        String[] arr = l.get(i+1).split(",");
        switch (b){
            case 0:
                rList.add(new Record());
                rList.get(i).setId(arr[0]);
                rList.get(i).setUsername(arr[1]);
                rList.get(i).setCreated_account_data(arr[2]);
                break;
            case 1:
                rList.add(new Record());
                rList.get(i).setId(arr[0]);
                rList.get(i).setFirst_name(arr[1]);
                rList.get(i).setLast_name(arr[2]);
                rList.get(i).setEmail(arr[3]);
                rList.get(i).setGender(arr[4]);
                rList.get(i).setIp_address(arr[5]);

                break;
        }


    }
    return rList;
}

    public static List<Record> getDatasetFromYAMLText(RouteDTO car) throws JsonProcessingException {
        YAMLMapper ym = new YAMLMapper();
        ArrayList<Record> carYaml = ym.readValue(car.getData(), new TypeReference<ArrayList<Record>>(){});
        return carYaml;
    }
    public static List<Record> getDatasetFromXMLText(RouteDTO car) throws IOException {
        XmlMapper xm = new XmlMapper();
        ArrayList<Record> carXml = xm.readValue(car.getData(), new TypeReference<ArrayList<Record>>(){});
        return carXml;
    }
    public static List<Record> getDatasetFromJSONText(RouteDTO car) throws IOException {
        ObjectMapper xm = new ObjectMapper();
        ArrayList<Record> carXml = xm.readValue(car.getData().replaceAll(",]","]"), new TypeReference<ArrayList<Record>>(){});
        return carXml;
    }

    public static void completeListRecords() throws IOException {
        if(allObjects!= null && allObjects.size()>0){
            for(int i = 0; i < allObjects.size(); i++) {
                if(allObjects.get(i).getMime_type()==null){
                     allRecords.addAll(Utils.getDatasetFromJSONText(allObjects.get(i)));
                }else if(allObjects.get(i).getMime_type().equals("application/xml")){
                    allRecords.addAll(Utils.getDatasetFromXMLText(allObjects.get(i)));
                }else if(allObjects.get(i).getMime_type().equals("application/x-yaml")){
                    allRecords.addAll(Utils.getDatasetFromYAMLText(allObjects.get(i)));
                }else if(allObjects.get(i).getMime_type().equals("text/csv")) {
                    allRecords.addAll(Utils.getDatasetFromCsvText(allObjects.get(i)));
                }else{
                    System.err.println("What is this mime type? "+allObjects.get(i).getMime_type());
                }
            }
        }
    }

    public static Record getRandomRecord(){
        return allRecords.get(new Random().nextInt(allRecords.size()-1));
    }
    public String id;
    public String first_name;
    public String last_name;
    public String bitcoin_address;
    public String email;
    public String gender;
    public String ip_address;
    public String card_number;
    public String card_balance;
    public String card_currency;
    public String organization;
    public String full_name;
    public String employee_id;
    public String username;
    public String created_account_data;
    public static List<Record> getRecordById(String columnname, String value){
//        System.out.println(id);
        List<Record> resp = new ArrayList<>();
        boolean areResults = false;
        switch(columnname){
            case "id":
                for(Record r : allRecords){
                    if(r.id != null){
                        if (r.id.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;

            case "first_name":
                for(Record r : allRecords){
                    if(r.first_name != null){
                        if (r.first_name.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;

            case "last_name":
                for(Record r : allRecords){
                    if(r.last_name != null){
                        if (r.last_name.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;

            case "bitcoin_address":
                for(Record r : allRecords){
                    if(r.bitcoin_address != null){
                        if (r.bitcoin_address.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;

            case "email":
                for(Record r : allRecords){
                    if(r.email != null){
                        if (r.email.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;

            case "gender":
                for(Record r : allRecords){
                    if(r.gender != null){
                        if (r.gender.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;

            case "ip_address":
                for(Record r : allRecords){
                    if(r.ip_address != null){
                        if (r.ip_address.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;

            case "card_number":
                for(Record r : allRecords){
                    if(r.card_number != null){
                        if (r.card_number.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;

            case "card_balance":
                for(Record r : allRecords){
                    if(r.card_balance != null){
                        if (r.card_balance.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;

            case "card_currency":
                for(Record r : allRecords){
                    if(r.card_currency != null){
                        if (r.card_currency.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;

            case "organization":
                for(Record r : allRecords){
                    if(r.organization != null){
                        if (r.organization.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;

            case "full_name":
                for(Record r : allRecords){
                    if(r.full_name != null){
                        if (r.full_name.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;

            case "employee_id":
                for(Record r : allRecords){
                    if(r.employee_id != null){
                        if (r.employee_id.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;

            case "created_account_data":
                for(Record r : allRecords){
                    if(r.created_account_data != null){
                        if (r.created_account_data.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;
            case "username":
                for(Record r : allRecords){
                    if(r.username != null){
                        if (r.username.equals(value)){
                            resp.add(r);
                            areResults = true;
                        }
                    }
                }

                break;
            default:
                Record qwe = new Record();
                if(areResults==true){

                }else{
                    qwe.setId("NO SUCH RECORD APPARENTLY");
                }

                qwe.setId("NO SUCH COLUMN NAME APPARENTLY");
                resp.add(qwe);


        }



        return resp;
    }

    public static List<Record> getAllNotNullId() {
        List<Record> rl = new ArrayList<>();

        for (Record r : allRecords) {
            if (r.id != null) {
                rl.add(r);
            }
        }
        return rl;
    }






    public static void getAllLinks(List<String> givenRoutes) throws InterruptedException {
        if(givenRoutes != null && givenRoutes.size()>0){
            for(int i = 0; i < givenRoutes.size(); i++) {
                int finalI = i;
                new Thread(() -> {
                    try {
                        ObjectMapper om = new ObjectMapper();
                        String url = givenRoutes.get(finalI);
                        long start = new Date().getTime();
                        RouteDTO car = om.readValue(Utils.getAllTextFromPage(url), RouteDTO.class);
                        if(!homeUrl.equals(url)){
                                allObjects.add(car);
                        }
                        System.out.println("Thread execution time : "+(new Date().getTime()-start)+"; link : "+url);
                        if(car.getLink() != null && car.getLink().size()>0){
                            List<String> l = new ArrayList<String>(car.getLink().values());
                            allRoutes.addAll(l.stream().map(s -> s = defaultUrl + s).collect(Collectors.toList()));
                            getAllLinks(l.stream().map(s -> s = defaultUrl + s).collect(Collectors.toList()));
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

        }else{
            System.err.println("empty route list / something went really wrong");
        }


    }

}
