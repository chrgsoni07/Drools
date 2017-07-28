package com.sample;

import java.util.ArrayList;
import java.util.List;

import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.runtime.BatchExecutionCommandImpl;
import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.internal.runtime.helper.BatchExecutionHelper;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

public class DecisionClient {
	public static void main(String args[]) {
		
		String url = "http://localhost:8080/kie-server/services/rest/server";
		String username = "kieserver";
		String password = "kieserver1!";
		String container = "auctionContainer";
		
		AuctionPOJO obj = new AuctionPOJO(8213034705L, 95, 2.927373, "jake7870", 0, 95, 117.5);

        InsertObjectCommand insertObjectCommand = new InsertObjectCommand(obj, "f1");
        FireAllRulesCommand fireAllRulesCommand = new FireAllRulesCommand("fire-identifier");

        List<GenericCommand<?>> commands = new ArrayList<GenericCommand<?>>();
        commands.add(insertObjectCommand);
        commands.add(fireAllRulesCommand);
        BatchExecutionCommand command = new BatchExecutionCommandImpl(commands);

        String xStreamXml = BatchExecutionHelper.newXStreamMarshaller().toXML(command); // actual XML request

        KieServicesConfiguration config =  KieServicesFactory.newRestConfiguration(url,username,password);
         KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
        // the request "xStreamXml" we generated in previous step
        // "ListenerReproducer" is the name of the Container
        ServiceResponse<String> response = client.executeCommands(container, xStreamXml); 
        System.out.println(response.getResult());

        
	}
}
