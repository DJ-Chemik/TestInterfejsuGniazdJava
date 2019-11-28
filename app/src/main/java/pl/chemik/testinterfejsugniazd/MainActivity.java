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
    private ClientThread watekObslugiSieci;

    private ArrayList<String> indeksy = new ArrayList<>(Arrays.asList("136809", "132336", "131313"));
    private TextView tv_wynik;


    public void sendIndex1(View view) throws IOException {
        watekObslugiSieci.send(indeksy.get(0));
        System.out.println(watekObslugiSieci.receive());
    }

    public void sendIndex2(View v) {
        watekObslugiSieci.send(indeksy.get(1));
        System.out.println(watekObslugiSieci.receive());
    }

    public void sendIndex3(View v) {
        watekObslugiSieci.send(indeksy.get(2));
        System.out.println(watekObslugiSieci.receive());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        watekObslugiSieci = new ClientThread();
        watekObslugiSieci.start();

    }

    class ClientThread extends Thread {

        private byte[] buffer = new byte[1024];

        @Override
        public void run() {

            try {

                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);
//                    socket.close();

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

        public void send(int wartoscZListyIndeksow) {
            OutputStream outputstream = null;
            try {
                outputstream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter out = new PrintWriter(outputstream);
            out.write(indeksy.get(wartoscZListyIndeksow));
            out.flush();
        }

        public void send(String indeks) {
            OutputStream outputstream = null;
            try {
                outputstream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter out = new PrintWriter(outputstream);
            out.write(indeks);
            out.flush();
        }

        public String receive() {
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
            return output;
        }

    }

}
