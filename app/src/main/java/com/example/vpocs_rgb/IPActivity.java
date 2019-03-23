package com.example.vpocs_rgb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IPActivity extends AppCompatActivity {
    String ip_add;
    int port;

    EditText ip_input;
    EditText port_input;

    Button enter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        ip_input = (EditText) findViewById((R.id.editText));
        port_input = (EditText) findViewById((R.id.editText2));

        enter = (Button) findViewById(R.id.button2);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip_add = ip_input.getText().toString();
                port = Integer.parseInt(port_input.getText().toString());

                Bundle b = new Bundle();
                b.putString("ip_add", ip_add);
                b.putInt("port", port);

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }
}
