package com.qwer.apis;

import java.util.HashMap;
import java.util.Map;

import com.qwer.utils.ApiUtils;

import io.restassured.response.Response;

public class RegisterApi {

	public void registerUserByApi(String username, String email, String password) {
		String url = "https://qwer-staging1.kingshaper.com/api/register";

		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("username", username);
		requestBody.put("email", email);
		requestBody.put("password", password);
		requestBody.put("reference", null);
		requestBody.put("affiliate_token", null);
		requestBody.put("visitor_id", "wsXn3k43xnaZh302Be6A");
		requestBody.put("voucher_code", "");

		Response response = ApiUtils.sendPostRequest(url, requestBody, headers);

		System.out.println("Status code: " + response.getStatusCode());
		System.out.println("Response body:");
		System.out.println(response.asPrettyString());
	}

}
