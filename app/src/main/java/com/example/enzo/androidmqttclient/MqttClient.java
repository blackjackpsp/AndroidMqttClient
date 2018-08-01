package com.example.enzo.androidmqttclient;

import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enzo on 17/05/2018.
 */

class Message{
    private String text; // message body
    //private MemberData data; // data of the user that sent this message
    private boolean belongsToCurrentUser; // is this message sent by us?

    //public Message(String text, MemberData data, boolean belongsToCurrentUser) {
    public Message(String text,boolean belongsToCurrentUser) {
        this.text = text;
        //this.data = data;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    public String getText() {
        return text;
    }

    /*public MemberData getData() {
        return data;
    }*/

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }
}

class MqttClientTest implements MqttCallback {

    private MqttClient client;
    private MqttMessage message;
    private List<Message> messages = new ArrayList<Message>();
    private ListView view;
    private EditText receivedMessage;
    //private MessageAdapter msgAdpt;




    public void initializeMqttClient() throws MqttException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {




        try {
            client = new MqttClient("tcp://test.mosquitto.org:1883", "AndroidThingSub", new MemoryPersistence());
            client.setCallback(this);
            client.connect();

            //this.message=new MqttMessage("Ciao".getBytes());
            //String topic = "crescenzomugione/topic/led";
            //client.subscribe(topic);
            //client.publish(topic, this.message);

        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

    public void publishOnTopic(String topic, String message){


        try {
            this.message=new MqttMessage(message.getBytes());
            this.client.publish(topic, this.message);
            //this.messages.add(new Message(this.message.toString(),true));
            //msgAdpt.add(this.messages.get(0));

        } catch (MqttException e) {
        e.printStackTrace();
    }

    }


    public void subscribeOnTopic(String destination){
        try {
            this.client.subscribe(destination);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void setViev(ListView v, EditText e){
        this.view=v;
        this.receivedMessage=e;
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {


        this.receivedMessage.setText(message.toString());

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
