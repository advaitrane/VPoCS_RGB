package com.example.vpocs_rgb;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;

import android.util.Log;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.vr.ndk.base.DaydreamApi;
//import com.google.vr.sdk.samples.video360.rendering.Mesh;

import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import java.io.*;
import java.net.*;
import java.lang.String;


public class MainActivity extends AppCompatActivity {
    private  VrPanoramaView mVrPanoramaView;
    private Socket client;
    private DataOutputStream outToServer;
    //private PrintWriter out;

    String ip_add;
    int port;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        Bundle b = getIntent().getExtras();
        ip_add = b.getString("ip_add");
        port = b.getInt("port");

        // Server part of the code
         new Thread(new ClientThread()).start();

         if(client==null){
         System.out.println("No client created");
         }


        //Creating the server connection

        /**try {
            //InetAddress serverAddr = InetAddress.getBy("10.10.10.45");//"192.168.225.165" was being used. Use ip_add which is collected in ipActivity
            // InetAddress serverAddr = InetAddress.getLocalHost();
            //  client = new Socket("192.168.56.1", 6789);
            client = new Socket("http://10.10.10.45", 5050);
            outToServer= new DataOutputStream(client.getOutputStream());

        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
     /* finally{
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } */

        mVrPanoramaView = (VrPanoramaView) findViewById(R.id.pano_view);
        final int INTERVAL = 5000;
        //Log.e("ip is", ip_add);
        loadPhotoSphere("black.png");


        final Random rd=new Random();

        new Thread(new Runnable() {
        int i=0;
        String colours[]={"red.png", "blue.png", "green.png"};
        String labels[] = {"1,RED" , "2,BLUE" , "3,GREEN"} ;
        int count[] = {0,0,0};

            public void run() {
                try {
                    Thread.sleep(15000);//change back to 15000
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                while (true) {
                    try {

                        loadPhotoSphere("black.png");
                        Thread.sleep(INTERVAL);

                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // catch(IOException r){}

                    i=rd.nextInt(3);
                    if (count[i] == 20){
                        i  = (i+1)%3;
                    }
                    if(count[i] == 20){
                        i = (i+1)%3;
                    }
                    if(count[i] == 20){
                        i = (i+1)%3;
                    }

                    if(count[0]==20 && count[1]==20 && count[2]==20){
                        System.out.printf("check now");
                        count[0]=0;
                        count[1]=0;
                        count[2]=0;
                    }
                    count[i] = count[i] +1;
                    loadPhotoSphere(colours[i]);

                    //if(outToServer != null) {
                    try {
                        //DataOutputStream outToServer = new DataOutputStream(client.getOutputStream());
                        outToServer.write(labels[i].getBytes("UTF-8"), 0, labels[i].length());
                        //outToServer.flush();
                        //out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
                        // out.println(colours[i]+"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //}
                }
            }
        }).start();

    }

    private void loadPhotoSphere(String s) {
        //This could take a while. Should do on a background thread, but fine for current example
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        //VrPanoramaView.Options options = null;
        InputStream inputStream = null;
        //  VrPanoramaView mVrPanoramaView = (VrPanoramaView) findViewById(R.id.pano_view);
        AssetManager assetManager = getAssets();

        try {

            inputStream = assetManager.open(s);
            options.inputType = VrPanoramaView.Options.TYPE_MONO;
            mVrPanoramaView.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options);
            inputStream.close();
            //   outToServer.writeBytes(s + '\n');
        } catch (IOException e) {
            Log.e("Tuts+", "Exception in loadPhotoSphere: " + e.getMessage() );
        }
    }

    @Override
    protected void onPause() {
        mVrPanoramaView.pauseRendering();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVrPanoramaView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        mVrPanoramaView.shutdown();
        super.onDestroy();
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    class ClientThread implements Runnable{
        @Override
        public void run() {
            try {
                //InetAddress serverAddr = InetAddress.getByName("http://10.10.10.45");//"192.168.225.165" was being used 192.168.0.3
                // InetAddress serverAddr = InetAddress.getLocalHost();
                //  client = new Socket("192.168.56.1", 6789);
                //client = new Socket("10.10.10.45", 5050);
                client = new Socket(ip_add, port);
                outToServer= new DataOutputStream(client.getOutputStream());

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            /*finally{
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } */
      }

    }
}
