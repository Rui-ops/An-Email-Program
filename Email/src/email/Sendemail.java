/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import java.io.*;
import java.net.*;
import javax.net.ssl.*;

public class Sendemail {

    public static void main(String[] args) {
        SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        for (int i = 0; i < sf.getSupportedCipherSuites().length; i++) {
            System.out.println("SF " + i + ":" + sf.getSupportedCipherSuites()[i]);
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(sf);
        SSLSocket socket = null;
        String host = "smtp.kth.se";
        //String host = "localhost";
        try {
            socket = (SSLSocket) sf.createSocket(host, 587); //default HTTPS port
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < socket.getSupportedCipherSuites().length; i++) {
            System.out.println("SS " + i + ": " + socket.getSupportedCipherSuites()[i]);
        }

        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

//            writer.println("ehlo a");
//            writer.flush();
//
//            System.out.println(reader.readLine());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //String[] cipher = {"SSL_DH_anon_WITH_RC4_128_MD5"};
//        String[] cipher = {"TLS_AES_128_GCM_SHA256"};
        String[] cipher = {"TLS_RSA_WITH_AES_128_CBC_SHA"};
        socket.setEnabledCipherSuites(cipher);

        for (int i = 0; i < socket.getEnabledCipherSuites().length; i++) {
            System.out.println("SE" + i + ":" + socket.getEnabledCipherSuites()[i]);
        }

        try {
            socket.startHandshake();
            System.out.println("Handshake succsess!");
        } catch (IOException e) {
            System.out.println("*************" + e.getMessage());
        }

    }
}
