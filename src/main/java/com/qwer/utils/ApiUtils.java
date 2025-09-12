package com.qwer.utils;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ApiUtils {

	private static final String BASE_URL = "https://api.example.com";

	// 🔹 GET request
	public static Response sendRequest(String endpoint) {
		return RestAssured.given().baseUri(BASE_URL).when().get(endpoint).then().extract().response();
	}

	// 🔹 GET với query params + headers
	public static Response sendGetRequest(String endpoint, Map<String, ?> queryParams, Map<String, String> headers) {
		return RestAssured.given().baseUri(BASE_URL).headers(headers).queryParams(queryParams).when().get(endpoint)
				.then().extract().response();
	}

	// 🔹 POST với body JSON
	public static Response sendPostRequest(String endpoint, Object body, Map<String, String> headers) {
		return RestAssured.given().baseUri(BASE_URL).headers(headers).contentType(ContentType.JSON).body(body).when()
				.post(endpoint).then().extract().response();
	}

	// 🔹 PUT với body JSON
	public static Response sendPutRequest(String endpoint, Object body, Map<String, String> headers) {
		return RestAssured.given().baseUri(BASE_URL).headers(headers).contentType(ContentType.JSON).body(body).when()
				.put(endpoint).then().extract().response();
	}

	// 🔹 DELETE request
	public static Response sendDeleteRequest(String endpoint, Map<String, String> headers) {
		return RestAssured.given().baseUri(BASE_URL).headers(headers).when().delete(endpoint).then().extract()
				.response();
	}

}
