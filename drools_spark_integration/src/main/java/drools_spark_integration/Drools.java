package drools_spark_integration;

import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Drools {

	public static List<AuctionPOJO> droolsCalling(List<AuctionPOJO> dataList) {
		try {

			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.getKieClasspathContainer(); 
			// go !
			for (int i = 1; i < dataList.size(); i++) {
				KieSession kSession = kContainer.newKieSession("ksession-rules");
				kSession.insert(dataList.get(i));
				kSession.fireAllRules();
			}
		} catch (Throwable t) {
			t.printStackTrace();
		
		}
		return dataList;
	}

	public static void droolsCalling(AuctionPOJO inputObject) {
		try {
			// load up the knowledge base
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.getKieClasspathContainer();
			// go !
			KieSession kSession = kContainer.newKieSession("ksession-rules");
			// insert object into the working memory
			if (inputObject != null) {
				kSession.insert(inputObject);
				kSession.fireAllRules();
			}
		} catch (Throwable t) {
			t.printStackTrace();

		}

	}

}
