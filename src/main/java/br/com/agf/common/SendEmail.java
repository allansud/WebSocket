package br.com.agf.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class SendEmail {

	public boolean Enviar(String to, String subject, String message) {
		boolean succeed = false;
		try {
			Client client = Client.create();
			
			WebResource resource = client.resource("http://indexacao.esy.es/indexacao.php");			
			ClientResponse response = resource.queryParam("to", to)
					.queryParam("subject", subject).queryParam("message", message)
					.type("application/x-www-form-urlencoded")
					.get(ClientResponse.class);
			
			return response.getStatus() != 200 ? false : true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return succeed;
	}

	protected String readEmailFromHtml(String filePath, Map<String, String> input) {
		String msg = readContentFromFile(filePath);
		try {
			Set<Entry<String, String>> entries = input.entrySet();
			for (Map.Entry<String, String> entry : entries) {
				msg = msg.replace(entry.getKey().trim(), entry.getValue().trim());
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return msg;
	}

	private String readContentFromFile(String fileName) {
		StringBuffer contents = new StringBuffer();

		try {
			// Usnado o buffer para ler uma linha de cada vez.
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			try {
				String line = null;
				while ((line = reader.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				reader.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return contents.toString();
	}
}
