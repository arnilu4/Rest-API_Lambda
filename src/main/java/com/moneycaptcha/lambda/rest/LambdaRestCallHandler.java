package com.moneycaptcha.lambda.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LambdaRestCallHandler implements RequestStreamHandler {

	JSONParser jsonParser = new JSONParser();

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		LambdaLogger lambdaLogger = context.getLogger();
		lambdaLogger.log("Starting Lamda handler for checking valid transaction with proxies.");

		String proxi = "";
		String node1Consent = null;
		String node2Consent = null;
		String node3Consent = null;
		String node4Consent = null;
		String node5Consent = null;

		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		JSONObject response = new JSONObject();
		String successCode = "200";
		JSONObject event = null;
		final String pathParameters = "pathParameters";
		final String queryStrParameters = "queryStringParameters";
		String balance = null;
		String outputString = null;

		try {
			event = (JSONObject) jsonParser.parse(reader);

			if (event.get(pathParameters) != null) {
				JSONObject pathParams = (JSONObject) event.get(pathParameters);
				if (pathParams.get("proxy") != null) {
					proxi = (String) pathParams.get("proxy");
				}
			}
			if (event.get(queryStrParameters) != null) {
				JSONObject queryParams = (JSONObject) event.get(queryStrParameters);
				if (queryParams.get("node1") != null) {
					node1Consent = (String) queryParams.get("node1");
				}
			}
			if (event.get(queryStrParameters) != null) {
				JSONObject queryParams = (JSONObject) event.get(queryStrParameters);
				if (queryParams.get("node2") != null) {
					node2Consent = (String) queryParams.get("node2");
				}
			}
			if (event.get(queryStrParameters) != null) {
				JSONObject queryParams = (JSONObject) event.get(queryStrParameters);
				if (queryParams.get("node3") != null) {
					node3Consent = (String) queryParams.get("node3");
				}
			}
			if (event.get(queryStrParameters) != null) {
				JSONObject queryParams = (JSONObject) event.get(queryStrParameters);
				if (queryParams.get("node4") != null) {
					node4Consent = (String) queryParams.get("node4");
				}
			}
			if (event.get(queryStrParameters) != null) {
				JSONObject queryParams = (JSONObject) event.get(queryStrParameters);
				if (queryParams.get("node5") != null) {
					node5Consent = (String) queryParams.get("node5");
				}
			}
			if (event.get(queryStrParameters) != null) {
				JSONObject queryParams = (JSONObject) event.get(queryStrParameters);
				if (queryParams.get("balance") != null) {
					balance = (String) queryParams.get("balance");
				}
			}

		} catch (Exception e) {
			response.put("statusCode", "400");
			response.put("exception", e);
		}

		boolean validTransaction = false;
		if (proxi.equals("valid")) {
			List<String> consentList = new ArrayList<String>();
			consentList.add(node1Consent);
			consentList.add(node2Consent);
			consentList.add(node3Consent);
			consentList.add(node4Consent);
			consentList.add(node5Consent);
			validTransaction = validTransaction(consentList, lambdaLogger);
			outputString = validTransaction ? "Transaction is valid": "Transaction is Invalid";
		} else if (proxi.equals("updateBalance")) {
			outputString = "Balance Updated";
		}
		
		JSONObject responseBodyJson = new JSONObject();
		responseBodyJson.put("input", event.toString());
		responseBodyJson.put("message", "output is " + outputString);
		
		JSONObject header = new JSONObject();
		header.put("x-custom-header", "Sample Custome Header Value");
		header.put("Accesss-Controll-Allow-Origin", "*");
		
		response.put("isBase64Encoded", false);
		response.put("statusCode", successCode);
		response.put("headers", header);
		response.put("body", responseBodyJson.toString());
		
		OutputStreamWriter outputStreamWriter = new  OutputStreamWriter(output, "UTF-8");
		outputStreamWriter.write(response.toJSONString());
		outputStreamWriter.close();

	}

	public boolean validTransaction(List<String> nodeConsentList, final LambdaLogger lambdaLogger) {
		int accepted = 0;
		lambdaLogger.log("Consent List values are : " + nodeConsentList.toString());
		if (nodeConsentList != null && !nodeConsentList.isEmpty()) {

			for (int counter = 0; counter < nodeConsentList.size(); counter++) {
				if ("1".equals(nodeConsentList.get(counter))) {
					++accepted;
				}
			}
			
			lambdaLogger.log("Accepted consent count is : " + accepted);
			lambdaLogger.log("Consent nodes size is : " + nodeConsentList.size());
			float percentage = ((accepted / nodeConsentList.size()) * 100);
			return percentage >= 50;
		}
		return false;

	}
}
