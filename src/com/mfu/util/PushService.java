package com.mfu.util;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

import com.sun.org.apache.regexp.internal.recompile;

public class PushService {

	// Method to send Notifications from server to client end.

	public final static String AUTH_KEY_FCM = "AAAAB82eb04:APA91bEqjbqhZ4-D5THiBPQF3o2mym1ur2hdM3jRu8D4qpT8qpVrZqYAh4GcqzxV_0ZLzn06w2z21asBvhQfSAovXIfmjFJ34V8MK20qKRohOo6NdD9b8Jn5Rqg8Acv-uS1phnPQVfL4";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	
	public final static String AUTH_KEY_IONIC = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI4NDkxZTcxNi1jZjRkLTQwOTMtYjJmMy02MjJjYjNmMGY0YmUifQ.Zt2PJaSxMjZped-stMcxELZvkI-RBVERFS9IOqMjp5M";
	public final static String API_URL_IONIC = "https://api.ionic.io/push/notifications";

	// userDeviceIdKey is the device id you will query from your database

	public boolean send(String deviceTokenKey, String body) throws Exception {
		
		//return sendFCM(deviceTokenKey, body);
		return sendIONIC(deviceTokenKey, body);

	}
	
	private boolean sendIONIC(String deviceTokenKey, String body) throws Exception {

		String authKey = AUTH_KEY_IONIC;
		String FMCurl = API_URL_IONIC;

		try {
			URL url = new URL(FMCurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + authKey);
			conn.setRequestProperty("Content-Type", "application/json");

			JSONObject json = new JSONObject();
			json.put("tokens", new String[]{deviceTokenKey.trim()});
			json.put("profile", "smartcare"); 
			JSONObject notification = new JSONObject();
			notification.put("message", body);
			json.put("notification", notification);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(json.toString());
			wr.flush();
			conn.getInputStream();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	private boolean sendFCM(String deviceTokenKey, String body) throws Exception {

		String authKey = AUTH_KEY_FCM;
		String FMCurl = API_URL_FCM;

		try {
			URL url = new URL(FMCurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "key=" + authKey);
			conn.setRequestProperty("Content-Type", "application/json");

			JSONObject json = new JSONObject();
			json.put("to", deviceTokenKey.trim());
			JSONObject info = new JSONObject();
			info.put("title", "SmartQueue"); 
			info.put("body", body);
			info.put("sound", "default");
			json.put("notification", info);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(json.toString());
			wr.flush();
			conn.getInputStream();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static void main(String[] args) {
		try { 
			PushService fcm = new PushService();
			fcm.send(
					"eS0zknu7uOM:APA91bFmLS7rAcoz24aVQFbEfjI1u5kkGCDK8B8LsrAejywz24vtEi9HvdFq044Mzv-CMdtmnwOkVZlsG4HglKjDhvQbNDDYpAdCN1lDILgpU4X-_hpwACT7jqoTT-bOltO8orvmjgju",
					"โปรดรอหน้าห้องตรวจ 101");
			System.out.println("Success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
