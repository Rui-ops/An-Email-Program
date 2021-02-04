package email;

import java.io.*;
import java.net.*;
import java.util.Base64;
import javax.net.ssl.*;

public class Send {

    public static void main(String[] args) {
        String user ;
        String passwd ;
        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sock = null;
        Socket socket = null;

        try {
            socket = new Socket("smtp.kth.se", 587);
            InputStream inputstream = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputstream);
            BufferedReader br = new BufferedReader(isr);

            OutputStream outputstream = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(outputstream, true);
            System.out.println(br.readLine());

            //hello
            pw.println("EHLO smtp.kth.se");
            String str;
            for (int i = 0; i < 8; i++) {
                System.out.println(br.readLine());
            }
            //starttls
            pw.println("STARTTLS");
//            System.out.println("a");
            System.out.println(br.readLine());

            sock = (SSLSocket) sf.createSocket(socket, null, socket.getPort(), false);
//            System.out.println("Convert success");
//            pw.println("auth login");
//            System.out.println(br.readLine());
        } catch (IOException e) {
            System.out.println("++++++++" + e.getMessage());
        }

//        String[] cipher = {"SSL_DH_anon_WITH_RC4_128_MD5"};
//        String[] cipher = {"TLS_AES_128_GCM_SHA256"};
        String[] cipher = {"TLS_RSA_WITH_AES_128_CBC_SHA"};
        sock.setEnabledCipherSuites(cipher);

        PrintWriter writer = null;
        BufferedReader reader = null;

        try {
            sock.startHandshake();
            System.out.println("Handshake success!");
        } catch (IOException e) {
            System.out.println("*************" + e.getMessage());
        }
        try {
            //writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
            writer = new PrintWriter(sock.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            writer.println("EHLO smtp.kth.se");
            writer.flush();
            for (int i = 0; i < 9; i++) {
                System.out.println(reader.readLine());
            }

            writer.println("AUTH LOGIN");
            writer.flush();
            System.out.println(reader.readLine());
            
            user = Base64.getEncoder().encodeToString("ruizhi".getBytes("utf-8"));
            passwd = Base64.getEncoder().encodeToString("yrz.547166287".getBytes("utf-8"));
            
            writer.println(user);
            writer.flush();
            System.out.println(reader.readLine());
//            
            writer.println(passwd);
            writer.flush();
            System.out.println(reader.readLine());
            
            writer.println("MAIL FROM:<ruizhi@kth.se>");
            writer.flush();
            System.out.println(reader.readLine());
            
            writer.println("RCPT TO:<ruizhi@kth.se>");
            writer.flush();
            System.out.println(reader.readLine());
            
//            writer.println("SUBJECT:HELLO WORLD");
//            writer.flush();
//            System.out.println(reader.readLine());
            
            writer.println("DATA");
            writer.flush();
            System.out.println(reader.readLine());
            
            writer.println("Hello");
            writer.flush();
//            System.out.println(reader.readLine());
            
            writer.println(".");
            writer.flush();
            System.out.println(reader.readLine());
            
//            writer.println(".");
//            writer.flush();
//            System.out.println(reader.readLine());
            
            writer.println("QUIT");
            writer.flush();
            System.out.println(reader.readLine());
            
//            writer.println("QUIT");
//            writer.flush();
//            System.out.println(reader.readLine());
//            


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
