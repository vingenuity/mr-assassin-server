package us.mrassassin.c2dm;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection; //added because of problem with HttpsURLConnection
import javax.net.ssl.SSLSession;

public class MessageUtil {
	private final static String AUTH = "authentication";

	private static final String UPDATE_CLIENT_AUTH = "Update-Client-Auth";

	public static final String PARAM_REGISTRATION_ID = "registration_id";

	public static final String PARAM_DELAY_WHILE_IDLE = "delay_while_idle";

	public static final String PARAM_COLLAPSE_KEY = "collapse_key";

	private static final String UTF8 = "UTF-8";

	public static int sendMessage(String auth_token, String registrationId,
			String message) throws IOException {

		StringBuilder postDataBuilder = new StringBuilder();
		postDataBuilder.append(PARAM_REGISTRATION_ID).append("=")
				.append(registrationId);
		postDataBuilder.append("&").append(PARAM_COLLAPSE_KEY).append("=")
				.append("0");
		postDataBuilder.append("&").append("data.payload").append("=")
				.append(URLEncoder.encode(message, UTF8));

		byte[] postData = postDataBuilder.toString().getBytes(UTF8);

		// Hit the dm URL.

		URL url = new URL("https://android.clients.google.com/c2dm/send");
		//HttpURLConnection.setDefaultHostnameVerifier(new CustomizedHostnameVerifier());
		//TODO: make sure connection works even without previous function
		//NOTE setDefaultHostnameVerifier is not a part of http, only https
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
		conn.setRequestProperty("Content-Length",
				Integer.toString(postData.length));
		conn.setRequestProperty("Authorization", "GoogleLogin auth="
				+ auth_token);

		OutputStream out = conn.getOutputStream();
		out.write(postData);
		out.close();

		int responseCode = conn.getResponseCode();
		String rep = conn.getResponseMessage();
		//conn.getInputStream().
		String messageId;
		String error;
		BufferedReader reader = new BufferedReader(new
		InputStreamReader(conn.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
		if (line.startsWith("id=")) {
		   messageId = line.substring(3);
		} else if (line.startsWith("Error=")){
		  error = line.substring(6);

				                        }
				                } 
		return responseCode;
	}
	/*
	private static class CustomizedHostnameVerifier implements HostnameVerifier {
=======
	/*private static class CustomizedHostnameVerifier implements HostnameVerifier {
>>>>>>> .r8
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}*/
	private static class CustomizedHostnameVerifier{
		public boolean verify(String hostname) {
			return true;
		}
	}
}

