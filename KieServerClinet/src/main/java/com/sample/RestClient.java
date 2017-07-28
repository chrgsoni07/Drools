package com.sample;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.drools.core.command.impl.GenericCommand;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;


public class RestClient {
	public static void main(String[] args) {
		//String url = "http://localhost:8080/kie-server/services/rest/server/containers/instances";
		String url = "http://localhost:8080/kie-server/services/rest/server";
		String username = "kieserver";
		String password = "kieserver1!";
		String container = "auctionContainer";
		
		AuctionPOJO obj = new AuctionPOJO(8213034705L, 95, 2.927373, "jake7870", 0, 95, 117.5);
		KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(url, username, password);
		Set<Class<?>> allClasses = new HashSet<Class<?>>();
		allClasses.add(AuctionPOJO.class);
		config.addJaxbClasses(allClasses);
		
		KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
		RuleServicesClient ruleClient = client.getServicesClient(RuleServicesClient.class);
		List<GenericCommand<?>> commands = new ArrayList<GenericCommand<?>>();
		commands.add((GenericCommand<?>) KieServices.Factory.get().getCommands().newInsert(obj, "AuctionPOJO"));
		commands.add((GenericCommand<?>) KieServices.Factory.get().getCommands().newFireAllRules("fire-identifier"));
		BatchExecutionCommand batchCommand = KieServices.Factory.get().getCommands().newBatchExecution(commands);
		//System.out.println(batchCommand.toString());
		ServiceResponse<String> response = ruleClient.executeCommands(container, batchCommand);
		System.out.println(response.getResult());
	}
}