package com.example.enzo.androidmqttclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ToggleButton;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MqttClientTest client=new MqttClientTest();


        final ToggleButton toggle = (ToggleButton) findViewById(R.id.nameConfirm);
        final EditText username = (EditText) findViewById(R.id.userText);
        final EditText message = (EditText) findViewById(R.id.messageText);
        EditText receivedMessage = (EditText) findViewById(R.id.subscribeReceive);
        final Button sendButton= (Button) findViewById(R.id.sendButton);
        ListView view =  (ListView) findViewById(R.id.messages_view);

        client.setViev(view,receivedMessage);

        final EditText destinationUsername = (EditText) findViewById(R.id.destName);

        try {
            client.initializeMqttClient();
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }


        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    username.setEnabled(false);
                    destinationUsername.setEnabled(false);
                    message.setVisibility(View.VISIBLE);
                    sendButton.setVisibility(View.VISIBLE);
                    message.setText("Insert message");
                    client.subscribeOnTopic(destinationUsername.getText().toString()+"/#");
                } else {
                    // The toggle is disabled
                    username.setEnabled(true);
                    destinationUsername.setEnabled(true);
                    message.setVisibility(View.INVISIBLE);
                    sendButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        /*message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                message.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                client.publishOnTopic(username.getText()+"/"+destinationUsername.getText(), message.getText().toString());
                message.setText("");
            }
        });


    }
}
