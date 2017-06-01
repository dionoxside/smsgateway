/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pjar;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.smslib.AGateway;
import org.smslib.IOutboundMessageNotification;
import org.smslib.OutboundMessage;
import org.smslib.Service;

/**
 *
 * @author novan
 */

public class SendMessage1 {
//    Service service;
    public void doIt(String PhoneNumber, String Pesan) throws Exception
        {
                OutboundNotification outboundNotification = new OutboundNotification();
//                this.service.setOutboundMessageNotification(outboundNotification);
//                System.out.println("Example: Send message from a serial gsm modem.");
//                System.out.println(Library.getLibraryDescription());
//                System.out.println("Version: " + Library.getLibraryVersion());
//                SerialModemGateway gateway = new SerialModemGateway("FirstGateway", "COM4", 9600, "", "");
//                gateway.setInbound(true);
//                gateway.setOutbound(true);
//this.service.setOutboundMessageNotification(outboundNotification);
                Service.getInstance().setOutboundMessageNotification(outboundNotification);
//                Service.getInstance().addGateway(gateway);
//this.service.startService();
                Service.getInstance().startService();
//                System.out.println();
//                System.out.println("Modem Information:");
//                  String Manu = ("Manufacture" + gateway.getManufacturer());
//                  String Model = ("Model" +gateway.getModel());
//                  String Serial = ("Serial" +gateway.getSerialNo());
//                  int signal = (gateway.getSignalLevel());
//                  int batterai = (gateway.getBatteryLevel());
//                System.out.println("  Manufacturer: " + gateway.getManufacturer());
//                System.out.println("  Model: " + gateway.getModel());
//                System.out.println("  Serial No: " + gateway.getSerialNo());
//                System.out.println("  SIM IMSI: " + gateway.getImsi());
//                System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
//                System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
//                System.out.println();
                OutboundMessage msg = new OutboundMessage(PhoneNumber,Pesan);
//                msg.setGatewayId("FirstGateway");
//                this.service.sendMessage(msg);
                Service.getInstance().sendMessage(msg);
//                msg.setGatewayId("FirstGateway");
//                System.out.println(msg);
                if(msg.equals(OutboundMessage.MessageStatuses.FAILED)){
                    JOptionPane.showMessageDialog(null,"Pesan gagal terkirim");
                }else{
                    JOptionPane.showMessageDialog(null,"Pesan Anda terkirim");
//                    JOptionPane.showMessageDialog(null,"Pesan telah terkirim");
                }
//                System.out.println("Now Sleeping - Hit <enter> to terminate.");
//                System.in.read();
//                  this.service.stopService();
                Service.getInstance().stopService();
        }

        public class OutboundNotification implements IOutboundMessageNotification
        {
                public void process(AGateway gateway, OutboundMessage msg)
                {
                        System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());
                        System.out.println(msg);
                }
}
        public void ProgressBar(){
            JProgressBar Status = new JProgressBar();
            Status.setValue(20);
            Status.setStringPainted(true);    
                }
        }