package com.ibm.ciclan.Base;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.ibm.ciclan.Enumerations.BrowserStack.BrowserStack;
import com.jayway.jsonpath.JsonPath;


/**
 *
 *
 **/
public class BrowserStackIntegration {

	private static JsonArray json = getPlatforms();


	// mobile os version
	public static String selectMobile_latestOs(String os) {

		// filter json by various options
		List<Map<String, Object>> filteredPlatforms = JsonPath.parse(json.toString()).read("$[?(@.os == '"+os+"')]");

		Set<String> browserVersions = new HashSet<String>();

		if ("android".equalsIgnoreCase(os)) {

			for (Map<String, Object> platform: filteredPlatforms) {
				//System.out.println(platform.get("device").toString()+" ["+platform.get("os_version").toString()+"]");

				browserVersions.add(String.format("%04.1f", Double.parseDouble(platform.get("os_version").toString())));

			}
			return String.format("%4.1f", Float.parseFloat(Collections.max(browserVersions))).trim();
		}

		if ("ios".equalsIgnoreCase(os)) {
			for (Map<String, Object> platform: filteredPlatforms) {
				//System.out.println(platform.get("device").toString()+" ["+platform.get("os_version").toString()+"]");

				browserVersions.add(String.format("%02d", Integer.parseInt(platform.get("os_version").toString())));

			}
			return String.format("%d", Integer.parseInt(Collections.max(browserVersions)));
		}

		return "";

	}


	// mobile os version
	public static String selectMobile_latestOs(String os, BrowserStack.MobileType type) {

		List<Map<String, Object>> filteredPlatforms = null;

		switch (type) {
			case PHONE: {
				// filter json by various options
				filteredPlatforms = JsonPath.parse(json.toString()).read("$[?(@.os == '" + os + "' && @.device =~ /^(?!.*(Tab|Pad)).*$/i)]");
				break;
			}
			case TABLET: {
				// filter json by various options
				filteredPlatforms = JsonPath.parse(json.toString()).read("$[?(@.os == '" + os + "' && (@.device =~ /.*tab.*/i || @.device =~ /.*pad.*/i))]");
				break;
			}
		}

		Set<String> browserVersions = new HashSet<String>();

		if ("android".equalsIgnoreCase(os)) {
			for (Map<String, Object> platform : filteredPlatforms) {
				// System.out.println(platform.get("device").toString()+"["+platform.get("os_version").toString()+"]");
				browserVersions.add(String.format("%04.1f", Double.parseDouble(platform.get("os_version").toString())));
			}
			return String.format("%4.1f", Float.parseFloat(Collections.max(browserVersions))).trim();
		}

		if ("ios".equalsIgnoreCase(os)) {
			for (Map<String, Object> platform : filteredPlatforms) {
				// System.out.println(platform.get("device").toString()+"["+platform.get("os_version").toString()+"]");
				browserVersions.add(String.format("%02d", Integer.parseInt(platform.get("os_version").toString())));
			}
			return String.format("%d", Integer.parseInt(Collections.max(browserVersions)));
		}
		return "";
	}


	// mobile os version
	public static String selectMobile_devicePerOsOsVersion(String os, String osVersion) {

		Random random = new Random(System.currentTimeMillis());
		int target = 0;

		// filter json by various options
		List<Map<String, Object>> filteredPlatforms = JsonPath.parse(json.toString()).read("$[?(@.os == '"+os+"')]");

		Set<String> browserVersions = new HashSet<String>();

		if ("android".equalsIgnoreCase(os)) {
			for (Map<String, Object> platform: filteredPlatforms) {
				//System.out.println(platform.get("device").toString()+" ["+platform.get("os_version").toString()+"]");
				browserVersions.add(String.format("%04.1f", Double.parseDouble(platform.get("os_version").toString())));
			}
		}

		if ("ios".equalsIgnoreCase(os)) {
			for (Map<String, Object> platform: filteredPlatforms) {
				//System.out.println(platform.get("device").toString()+" ["+platform.get("os_version").toString()+"]");
				browserVersions.add(String.format("%02d", Integer.parseInt(platform.get("os_version").toString())));
			}
		}

		//System.out.format("************\n%s, Max: %s\n************\n", os, Collections.max(browserVersions));

		filteredPlatforms = JsonPath.parse(json.toString()).read("$[?(@.os == '"+os+"' && @.os_version == '"+Collections.max(browserVersions)+"')]");

		List<String> devices = new ArrayList<String>();

		for (Map<String, Object> platform: filteredPlatforms) {
			//System.out.println(platform.get("device").toString()+" ["+platform.get("os_version")+"]");
			devices.add(platform.get("device").toString());
		}

		target = random.nextInt(devices.size());

		return devices.get(target);

	}


	// mobile os version
	public static String selectMobile_devicePerOsOsVersion(String os, String osVersion, BrowserStack.MobileType type) {

		Random random = new Random(System.currentTimeMillis());
		int target = 0;

		List<Map<String, Object>> filteredPlatforms = null;

		switch(type) {
			case PHONE: {
				// filter json by various options
				filteredPlatforms = JsonPath.parse(json.toString()).read("$[?(@.os == '"+os+"' && @.device =~ /^(?!.*(tab|pad)).*$/i)]");
				break;
			}
			case TABLET: {
				// filter json by various options
				filteredPlatforms = JsonPath.parse(json.toString()).read("$[?(@.os == '"+os+"' && (@.device =~ /.*tab.*/i || @.device =~ /.*pad.*/i))]");
				break;
			}
		}


		Set<String> browserVersions = new HashSet<String>();

		if ("android".equalsIgnoreCase(os)) {
			for (Map<String, Object> platform: filteredPlatforms) {
				//System.out.println(platform.get("device").toString()+" ["+platform.get("os_version").toString()+"]");
				browserVersions.add(String.format("%04.1f", Double.parseDouble(platform.get("os_version").toString())));
			}
		}

		if ("ios".equalsIgnoreCase(os)) {
			for (Map<String, Object> platform: filteredPlatforms) {
				//System.out.println(platform.get("device").toString()+" ["+platform.get("os_version").toString()+"]");
				browserVersions.add(String.format("%02d", Integer.parseInt(platform.get("os_version").toString())));
			}
		}

		//System.out.format("************\n%s, Max: %s\n************\n", os, Collections.max(browserVersions));

		String maxVer = Collections.max(browserVersions);

		if ("android".equalsIgnoreCase(os)) {
			maxVer = String.format("%3.1f", Float.parseFloat(maxVer));
		}
		if ("ios".equalsIgnoreCase(os)) {
			maxVer = String.format("%d", Integer.parseInt(maxVer));
		}

		switch(type) {
			case PHONE: {
				filteredPlatforms = JsonPath.parse(json.toString()).read("$[?(@.os == '"+os+"' && @.os_version == '"+maxVer+"' && @.device =~ /^(?!.*(tab|pad)).*$/i)]");
				break;
			}
			case TABLET: {
				filteredPlatforms = JsonPath.parse(json.toString()).read("$[?(@.os == '"+os+"' && @.os_version == '"+maxVer+"' && (@.device =~ /.*tab.*/i || @.device =~ /.*pad.*/i))]");
				break;
			}
		}

		List<String> devices = new ArrayList<String>();

		for (Map<String, Object> platform: filteredPlatforms) {
			//System.out.println(platform.get("device").toString()+" ["+platform.get("os_version")+"]");
			devices.add(platform.get("device").toString());
		}

		target = random.nextInt(devices.size());

		return devices.get(target);

	}


	// desktop
	public static String selectDesktop_latestBrowserVersionPerOsOsVersionBrowser(String os, String os_version, String browser) {

		//System.out.format("==========\nOS: %s\nOS_VERSION: %s\nBrowser: %s\n==========\n", os, os_version, browser);

		NumberFormat formatter = NumberFormat.getNumberInstance(Locale.ROOT);
		formatter.setMinimumFractionDigits(1);
		formatter.setMaximumFractionDigits(2);

		Random random = new Random(System.currentTimeMillis());
		int target = 0;

		// filter json by various options
		List<Map<String, Object>> filteredPlatforms = JsonPath.parse(json.toString()).read("$[?(@.os == '"+os+"' && @.os_version == '"+os_version+"' && @.browser == '"+browser+"')]"); //  && @.os_version =~ /8.*/

		Set<Double> browserVersions = new HashSet<Double>();


		for (Map<String, Object> platform: filteredPlatforms) {
			//System.out.println(platform.get("browser").toString()+" ["+platform.get("browser_version").toString()+"]");
			try {
				browserVersions.add(Double.parseDouble(platform.get("browser_version").toString()));
			} catch (NumberFormatException nfe) {
				//do nothing. discard betas.
			}
		}

		//System.out.format("************\n%s, %s, %s, Max: %06.2f\n************\n", os, os_version, browser, Collections.max(browserVersions));

		filteredPlatforms = JsonPath.parse(json.toString()).read("$[?(@.os == '"+os+"' && @.os_version == '"+os_version+"' && @.browser == '"+browser+"' && @.browser_version == '"+Collections.max(browserVersions)+"')]");

		List<Double> browsers = new ArrayList<Double>();

		for (Map<String, Object> platform: filteredPlatforms) {
			//System.out.println(platform.get("browser").toString()+" ["+platform.get("browser_version")+"]");
			browsers.add(Double.parseDouble(platform.get("browser_version").toString()));
		}

		target = random.nextInt(browsers.size());

		return formatter.format(browsers.get(target));

	}


	// support method
	private static JsonArray getPlatforms() {

		JsonArray jsonResponse;
		String json = null;
		JsonParser jsonParser = new JsonParser();

		HttpHost host = new HttpHost("api.browserstack.com", 443, "https");
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(host.getHostName(), host.getPort()), new UsernamePasswordCredentials("jerimiahmack1", "RNpJvnPhx6ruQCRsaCJD"));

		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

		try {
			AuthCache authCache = new BasicAuthCache();
			BasicScheme basicAuth = new BasicScheme();
			authCache.put(host,  basicAuth);

			HttpClientContext localContext = HttpClientContext.create();
			localContext.setAuthCache(authCache);

			HttpGet httpGet = new HttpGet("/automate/browsers.json");

			CloseableHttpResponse response = null;
			try {
				response = httpClient.execute(host, httpGet, localContext);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				json = EntityUtils.toString(response.getEntity());

			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			jsonResponse = jsonParser.parse(json).getAsJsonArray();

			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} finally {

			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		System.out.println(jsonResponse);

		return jsonResponse;

	}


}
