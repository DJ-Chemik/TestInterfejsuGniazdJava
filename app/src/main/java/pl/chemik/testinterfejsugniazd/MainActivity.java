package pl.chemik.testinterfejsugniazd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final int SERVERPORT = 1234;
    private static final String SERVER_IP = "192.168.0.108";
    private Socket socket; // Utworzenie gniazda
    public Thread watekObslugiSieci;
    public Runnable clientThread;

    private ArrayList<String> indeksy = new ArrayList<>(Arrays.asList("136809", "132336", "131313"));
    private TextView tv_wynik;


    public void sendIndex1(View v){
//        watekObslugiSieci = new Thread(new ClientThread());
//        watekObslugiSieci.start();
//        watekObslugiSieci.interrupt();

        clientThread = new ClientThread();
        watekObslugiSieci = new Thread(clientThread);
        watekObslugiSieci.start();
}

    public void sendIndex2(View v) {

    }

    public void sendIndex3(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    class ClientThread implements Runnable{

        private byte[] buffer = new byte[1024];

        @Override
        public void run() {

            try {

                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
                OutputStream outputstream = null;
                try {
                    outputstream = socket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PrintWriter out = new PrintWriter(outputstream);
                out.write(indeksy.get(0));
                out.flush();


                InputStream in = null;
                String output = "Brak danych... :-(\n";

                try {
                    in = socket.getInputStream();

                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        output = new String(buffer, 0, read);
                        System.out.print(output);
                        System.out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                socket.close();
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }


        }

        public void send(int wartoscZListyIndeksow) {

        }


        public String receive() {
            String output="";
            return output;
        }

    }

}
