package com.egen.main.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NoHttpResponseException;
import org.apache.http.conn.HttpHostConnectException;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.egen.main.Exception.InvalidRequestException;
import com.egen.main.entity.Alert;
import com.egen.main.entity.Metric;
import com.egen.main.rules.Launcher;

import groovyx.net.http.HTTPBuilder;

@Service
public class MetricsServiceImpl implements MetricsService {

	@Autowired
	Launcher objLauncher;

	@Autowired
	private Datastore datastore;

	@Autowired
	private Environment envi;

	@Override
	public Metric create(Metric objMetric) throws InvalidRequestException {

		if (objMetric.getTimestamp() == 0 || objMetric.getValue() == 0) {
			throw new InvalidRequestException(objMetric);
		}
		objLauncher.executeRules(objMetric, this);
		datastore.save(objMetric);
		return objMetric;
	}

	@Override
	public List<Metric> getMetrics() {
		return datastore.createQuery(Metric.class).asList();
	}

	@Override
	public List<Metric> getMetrics(long from, long to) {
		return datastore.createQuery(Metric.class).filter("timestamp >=", from).filter("timestamp <=", to).asList();
	}

	@Override
	public Alert createAlert(String sAlert) {
		Alert alert = new Alert();
		alert.setValue(sAlert);
		alert.setTimestamp(System.currentTimeMillis());
		datastore.save(alert);
		return alert;
	}

	@Override
	public Alert postAlert(String sAlert) throws IOException, URISyntaxException {
		URL url = new URL(envi.getProperty("alert.url"));
		long sysmil;
		HTTPBuilder http = new HTTPBuilder(url);

		Map<String, Object> map = new HashMap<>();
		sysmil = System.currentTimeMillis();
		String json = "{\"timestamp\": \"" + String.valueOf(sysmil) + "\", \"value\": \"" + sAlert + "\"}";
		map.put("body", json);
		System.out.println("Posting data " + json + " to api at " + url);

		Map<String, String> headers = new HashMap<>();
		headers.put("content-type", "application/json");
		map.put("headers", headers);

		try {
			http.post(map);
		} catch (HttpHostConnectException | NoHttpResponseException e) {
			System.out.println("API [" + url + "] not reachable. Error - " + e.getMessage());
		}
		Alert alert = new Alert();
		alert.setTimestamp(sysmil);
		alert.setValue(sAlert);
		return alert;
	}
}
