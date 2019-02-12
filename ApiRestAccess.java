package py.com.konecta.restclientapp;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
//import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBElement;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicNameValuePair;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class ApiRestAccess {
    
    private String server;
    private int port;
    
    OkHttpClient client = new OkHttpClient();
    
    public ApiRestAccess(String server, int port) {
        this.server = server;
        this.port = port;
    }
    
    public String callGet(String urlCompleta, String token) throws IOException, JSONException {
       String rta = get(urlCompleta, token);
       return rta;
        
    }
    
    public String get(String url, String token) throws IOException, JSONException {

        Request request = null;
        try {
            request = new Request.Builder()
                    .url(url)
                    .header("client_id", "crm")
                    .addHeader("Authorization", "bearer " + token)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Response response = client.newCall(request).execute();

        if (response != null) {

            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                if (response.body() != null) {
                    return response.body().string();
                }
            }
        }
        return null;
    }
    
    public int getStatus(String urlServicio, String token) {
        
        InputStream is = null;
                
        try {
            
            HttpClient client = HttpClientBuilder.create().build();
            HttpHost host = new HttpHost(server, port);
            
            HttpEntityEnclosingRequest request;
            request = new BasicHttpEntityEnclosingRequest("GET", urlServicio);
            request.setHeader("Authorization", "bearer " + token);
            request.setHeader("client_id", "crm");
            
            HttpResponse res = client.execute(host, request);
            if (res != null) {
                return res.getStatusLine().getStatusCode();
            }
            return 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ocurrio un error : " + e.getMessage());
            return -1;
        } finally {
            try {
                if (is != null) is.close(); 
            } catch (Exception e) {
                System.out.println("Error al cerrar InputStream : " + e.getMessage());
            }
        }
    }
    
    public String getDatos(String urlServicio) {
        
        InputStream is = null;
                
        try {
            
            HttpClient client = HttpClientBuilder.create().build();
            HttpHost host = new HttpHost(server, port);
            //String token = getToken();
            
            HttpEntityEnclosingRequest request;
            request = new BasicHttpEntityEnclosingRequest("GET", urlServicio);
            /*if (token != null) {
                request.setHeader("Authorization", "bearer " + token);
                request.setHeader("client_id", "crm");
            }*/
            HttpResponse res = client.execute(host, request);
            HttpEntity entityResponse = res.getEntity();
            is = entityResponse.getContent();
            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer);
            String str = writer.toString();
            return str;
            /*JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(str);
            return obj;*/
            
        } catch (Exception e) {
            System.out.println("Ocurrio un error : " + e.getMessage());
            return null;
        } finally {
            try {
                if (is != null) is.close(); 
            } catch (Exception e) {
                System.out.println("Error al cerrar InputStream : " + e.getMessage());
            }
        }
    }
    
    public String getDatos(String urlServicio, String token) {
        
        InputStream is = null;
                
        try {
            
            HttpClient client = HttpClientBuilder.create().build();
            HttpHost host = new HttpHost(server, port);
            //String token = getToken();
            
            HttpEntityEnclosingRequest request;
            request = new BasicHttpEntityEnclosingRequest("GET", urlServicio);
            if (token != null) {
                request.setHeader("Authorization", "bearer " + token);
                request.setHeader("client_id", "crm");
            }
            HttpResponse res = client.execute(host, request);
            HttpEntity entityResponse = res.getEntity();
            is = entityResponse.getContent();
            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer);
            String str = writer.toString();
            return str;
            /*JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(str);
            return obj;*/
            
        } catch (Exception e) {
            System.out.println("Ocurrio un error : " + e.getMessage());
            return null;
        } finally {
            try {
                if (is != null) is.close(); 
            } catch (Exception e) {
                System.out.println("Error al cerrar InputStream : " + e.getMessage());
            }
        }
    }
    
    public JSONArray getLista(String urlServicio) {
        
        InputStream is = null;
                
        try {
            
            HttpClient client = HttpClientBuilder.create().build();
            HttpHost host = new HttpHost(server, port);
            String token = getToken();
            
            HttpEntityEnclosingRequest request;
            request = new BasicHttpEntityEnclosingRequest("GET", urlServicio);
            if (token != null) {
                request.setHeader("Authorization", "bearer " + token);
                request.setHeader("client_id", "crm");
            }
            HttpResponse res = client.execute(host, request);
            HttpEntity entityResponse = res.getEntity();
            is = entityResponse.getContent();
            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer);
            String str = writer.toString();
            
            JSONParser parser = new JSONParser();
            JSONArray obj = (JSONArray) parser.parse(str);
            return obj;
            
        } catch (Exception e) {
            System.out.println("Ocurrio un error : " + e.getMessage());
            return null;
        } finally {
            try {
                if (is != null) is.close(); 
            } catch (Exception e) {
                System.out.println("Error al cerrar InputStream : " + e.getMessage());
            }
        }
    }
    
    public JSONObject post(String url, String token, String json) {
        
        InputStream is = null;
        
        try {
            
            HttpClient client = HttpClientBuilder.create().build();
            HttpHost host = new HttpHost(server, port);
            StringEntity requestEntity = new StringEntity(json);

            HttpEntityEnclosingRequest request;
            request = new BasicHttpEntityEnclosingRequest("POST", url);
            request.setHeader("Authorization", "bearer " + token);
            request.setHeader("Content-Type","application/json");
            request.setEntity(requestEntity);
            
            HttpResponse res = client.execute(host, request);
            HttpEntity entityResponse = res.getEntity();
            is = entityResponse.getContent();
            System.out.println("is: "+ is);
            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer);
            String str = writer.toString();
            if (str != null && !str.isEmpty()) {
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(str);
                return obj;
            }
            
            return new JSONObject();
            
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (is != null) is.close();
            } catch (Exception e) {
                ;
            }
        }
    }
    
    public JSONArray getLista(JSONObject data) {
        
        if (data.containsKey("lista")) {
            JSONArray lista = (JSONArray) data.get("lista");
            if (lista != null) {
                return lista;
            }
        }
        return new JSONArray();
    }
    
    public JSONObject getItemLista(JSONObject data, int index) {
        
        JSONArray lista = getLista(data);
        if (lista.size() > 0) {
            JSONObject item = (JSONObject) lista.get(index);
            return item;
        }
        return new JSONObject();
    }
    
    public String getString(JSONObject data, String atributo) {
        
        if (data != null && data.containsKey(atributo)) {
            return (String) data.get(atributo);
        }
        return "";
    }
    
    public Long getLong(JSONObject data, String atributo) {
        
        if (data != null && data.containsKey(atributo)) {
            return (Long) data.get(atributo);
        }
        return 0L;
    }
    
     public String getToken() {
        
        InputStream is = null;
        String hostname = "crm7des-jb2.sis.personal.net.py"; 
        int port = 8380;
        String urlServicio = "/auth/realms/nucleo/protocol/openid-connect/token";
        
        try {
            
            HttpClient client = HttpClientBuilder.create().build();
            HttpHost host = new HttpHost(hostname, port);
            
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "password"));
            params.add(new BasicNameValuePair("client_id", "crm"));
            params.add(new BasicNameValuePair("username", "wikivas"));
            params.add(new BasicNameValuePair("password", "123"));
            params.add(new BasicNameValuePair("response_type", "token"));
            
            HttpEntityEnclosingRequest request;
            request = new BasicHttpEntityEnclosingRequest("POST", urlServicio);
            request.setEntity(new UrlEncodedFormEntity(params));
            
            HttpResponse res = client.execute(host, request);
            HttpEntity entityResponse = res.getEntity();
            is = entityResponse.getContent();
            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer);
            String str = writer.toString();
            
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(str);
            //return obj;
            if (obj.containsKey("access_token")) {
                String token = (String) obj.get("access_token");
                return token;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (Exception e) {
            }
        }
        
        return null;
    }
    
}
