package servletBag;

import com.notnoop.apns.*;

public class PushNotifications {
	ApnsService pushService = APNS.newService()
			.withCert("/Users/yannianliu/Desktop/A/证书.p12", "111111")
			.withSandboxDestination()
			.build();
//	ApnsService pushService = APNS.newService()
//			.withCert("path to .p12 certificate", "password used for certificate generation")
//			.withSandboxDestination()
//			.build();
	void notify(String message){
	        String payload = APNS.newPayload().alertBody(message).build();
	        String token = "deviceToken";
	        pushService.push(token, payload);
	}
	public static void main(String[] args){
		PushNotifications object = new PushNotifications();
		object.notify("Notify my iPhone");
	}
}