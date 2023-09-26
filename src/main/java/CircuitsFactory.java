import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CircuitsFactory {
    public static void main(String[] args) {
        //Solicitar peticion
        URL url = null;
        try {
            url = new URL("http://ergast.com/api/f1/2023/constructors.json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Comprobar peticion
            int responseCode = conn.getResponseCode();
            if(responseCode != 200) {
                System.out.println("Algo ha ido mal. Codigo: " + responseCode);
            } else {
                InputStream strm = conn.getInputStream();
                byte[] arrStream = strm.readAllBytes();

                String cntJson = "";

                for(byte tmp : arrStream) {
                    cntJson += (char) tmp;
                }

                JSONObject jsonObj = new JSONObject(cntJson);
                JSONObject jsonObj2 = new JSONObject(jsonObj.get("MRData").toString());
                JSONObject jsonObj3 = new JSONObject(jsonObj2.get("ConstructorTable").toString());
                JSONArray pilotsArray = new JSONArray(jsonObj3.get("Constructors").toString());
                for(Object obj : pilotsArray){
                    System.out.println(
                                    ((JSONObject)obj).get("name") + " (" +
                                    ((JSONObject)obj).get("nationality") + ")");
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
