package com.securitease.backend;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Test-scope API configuration with 'fields' param to satisfy the public API requirement.
 */
public final class ApiConfig {

    public static final String BASE_URL = "https://restcountries.com";
    public static final String ALL_COUNTRIES_PATH = "/v3.1/all";

    private ApiConfig() {}

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath(ALL_COUNTRIES_PATH)
                // Required by API as of Oct 2025; limit fields to what we assert & validate
                .addQueryParam("fields", "name,languages,cca2,independent,unMember")
                .setContentType(ContentType.JSON)
                .setConfig(RestAssured.config()
                        .httpClient(RestAssured.config().getHttpClientConfig()
                                .setParam("http.connection.timeout", 10000)
                                .setParam("http.socket.timeout", 10000)
                                .setParam("http.connection-manager.timeout", 10000)))
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .build();
    }

    public static boolean softAssertSASL() {
        // Default to SOFT (true) because public datasets may lag.
        // Set FORCE_HARD_SASL=true to make it a hard assertion in CI/local.
        String forceHard = System.getenv("FORCE_HARD_SASL");
        if (forceHard != null && forceHard.equalsIgnoreCase("true")) {
            return false;
        }
        String val = System.getenv("SOFT_ASSERT_SASL");
        if (val == null) {
            return true; // default soft
        }
        return val.equalsIgnoreCase("true");
    }
}