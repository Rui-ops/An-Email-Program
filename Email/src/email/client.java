package email;

import java.io.*;
import java.net.*;
import javax.net.ssl.*;

public class client{
    public static void main(String[] args){
        SSLSocketFactory sf = (SSLSocketFactory)SSLSocketFactory.getDefault();
        for(int i = 0; i < sf.getSupportedCipherSuites().length; i++)
            System.out.println("SF " + i + ":" + sf.getSupportedCipherSuites()[i]);
        HttpsURLConnection.setDefaultSSLSocketFactory(sf);
        SSLSocket socket = null;
        String host = "webmail.kth.se";
        try{
            socket = (SSLSocket)sf.createSocket(host,993);
        }
        catch(MalformedURLException e){
            System.out.println(e.getMessage());
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        for(int i = 0; i < socket.getSupportedCipherSuites().length; i++)
            System.out.println("SS " + i + ": " + socket.getSupportedCipherSuites()[i]);
//        String[] cipher = {"TLS_RSA_WITH_AES_128_GCM_SHA256"};
        String[] cipher = {"TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384"};
        //String[] cipher = {"TLS_RSA_WITH_AES_128_CBC_SHA"};
        socket.setEnabledCipherSuites(cipher);
        
        for(int i = 0; i < socket.getEnabledCipherSuites().length; i++)
            System.out.println("SE" + i + ":" + socket.getEnabledCipherSuites()[i]);
        
        PrintWriter writer = null;
        BufferedReader reader = null;
        try{
            socket.startHandshake();
        }
        catch(IOException e){
            System.out.println("*************" + e.getMessage());
        }
        try{
            //writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        writer.println("a002 login congg Gc751136120");
        writer.println("a003 select inbox");
//        writer.println("a004 fetch 1 full");
        writer.println("a005 fetch 1 body[header]");
        writer.println("a006 logout");
        writer.flush();
        try{
            String str;
            while( (str=reader.readLine()) != null)
                System.out.println(str);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}

