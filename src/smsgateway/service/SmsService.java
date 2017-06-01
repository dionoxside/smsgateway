/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smsgateway.service;

import org.smslib.AGateway;
import org.smslib.Service;
import org.smslib.modem.ModemGateway;
import org.smslib.modem.SerialModemGateway;

/**
 *
 * @author novan
 */
public class SmsService {
//Service service;
    /**
     *
     */
    public void StartService()throws Exception{
//      this.service = new Service();
      SerialModemGateway gateway = new SerialModemGateway("", "COM7", 9600, "", "");
       gateway.setInbound(true);
       gateway.setOutbound(true);
       gateway.setProtocol(AGateway.Protocols.PDU);
//         gateway.setIpProtocol(ModemGateway.IPProtocols.BINARY);
//       this.service.addGateway(gateway);
       Service.getInstance().addGateway(gateway);
    }
}
