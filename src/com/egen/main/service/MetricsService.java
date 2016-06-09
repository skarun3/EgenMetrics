package com.egen.main.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.egen.main.Exception.InvalidRequestException;
import com.egen.main.entity.Alert;
import com.egen.main.entity.Metric;

@Service
public interface MetricsService {

	public Metric create(Metric objMetric) throws InvalidRequestException;

	public List<Metric> getMetrics();

	public List<Metric> getMetrics(long from, long to);

	public Alert createAlert(String sAlert);

	public Alert postAlert(String sAlert) throws IOException, URISyntaxException;

}
