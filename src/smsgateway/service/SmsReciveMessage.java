/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smsgateway.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.smslib.AGateway;
import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Message;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.modem.ModemGateway;
import org.smslib.modem.SerialModemGateway;

/**
 *
 * @author novan
 */
public class SmsReciveMessage {
    public SmsReciveMessage(Connection koneksi) {
        this.koneksi = koneksi;
    }
//    Service service;
    private Connection koneksi;
    public void startService() throws Exception{
        try{        
        InboundNotification inboundNotification = new InboundNotification();
                // Create the notification callback method for inbound voice calls.
                CallNotification callNotification = new CallNotification();
                //Create the notification callback method for gateway statuses.
                GatewayStatusNotification statusNotification = new GatewayStatusNotification();
//        SerialModemGateway gateway = new SerialModemGateway("", "COM7", 9600, "", "");
//        gateway.setProtocol(AGateway.Protocols.PDU);
//         gateway.setIpProtocol(ModemGateway.IPProtocols.BINARY);
                        // Do we want the Gateway to be used for Inbound messages?
//                        gateway.setInbound(true);
                        // Do we want the Gateway to be used for Outbound messages?
//                        gateway.setOutbound(true);
                        // Let SMSLib know which is the SIM PIN.
//                        gateway.setSimPin("0000");
                        // Set up the notification methods.
                        Service.getInstance().setInboundMessageNotification(inboundNotification);
                        Service.getInstance().setCallNotification(callNotification);
                        Service.getInstance().setGatewayStatusNotification(statusNotification);
                        // Add the Gateway to the Service object.
//                        Service.getInstance().addGateway(gateway);
                        // Similarly, you may define as many Gateway objects, representing
                        // various GSM modems, add them in the Service object and control all of them.
                        // Start! (i.e. connect to all defined Gateways)
                        Service.getInstance().startService();
        }catch (Exception e)
                {
                        e.printStackTrace();
                }
                finally
                {
                        Service.getInstance().stopService();
                }
//                       
    }
     public class InboundNotification implements IInboundMessageNotification
        {
                public void process(AGateway gateway, Message.MessageTypes msgType, InboundMessage msg)
                {
                        if (msgType == Message.MessageTypes.STATUSREPORT)return;
                         
                        try{
                            String sql = "INSERT INTO kotakmasuk VALUES (NULL,?,?,?)";
                            PreparedStatement prepare = koneksi.prepareStatement(sql);
                            prepare.setString(1, "+" +msg.getOriginator());
                            prepare.setString(2, msg.getText());
                            prepare.setDate(3, new java.sql.Date(msg.getDate().getTime()));
                            prepare.executeUpdate();
                        } catch (SQLException ex) {
                            System.out.println("Pesan Gagal Disimpan");
                            System.out.println(ex.getMessage());
                        }
                }
        }

        public class CallNotification implements ICallNotification
        {
                public void process(AGateway gateway, String callerId)
                {
                        System.out.println(">>> New call detected from Gateway: " + gateway.getGatewayId() + " : " + callerId);
                }
        }

        public class GatewayStatusNotification implements IGatewayStatusNotification
        {
                public void process(AGateway gateway, AGateway.GatewayStatuses oldStatus, AGateway.GatewayStatuses newStatus)
                {
                        System.out.println(">>> Gateway Status change for " + gateway.getGatewayId() + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
                }
        }
        public void stop(){
        try {
            Service.getInstance().stopService();
        } catch (SMSLibException ex) {
            Logger.getLogger(SmsReciveMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SmsReciveMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(SmsReciveMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
}
