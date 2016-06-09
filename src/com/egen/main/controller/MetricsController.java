package com.egen.main.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.egen.main.Exception.InvalidRequestException;
import com.egen.main.entity.Metric;
import com.egen.main.entity.Status;
import com.egen.main.service.MetricsService;

@RestController
@RequestMapping(path = "/weight")
public class MetricsController {

	@Autowired
	private MetricsService objMetricsService;

	@RequestMapping(path = "", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<?> create(@RequestBody Metric objMetric) {
		try {
			objMetricsService.create(objMetric);
			return new ResponseEntity<Status>(new Status(201, "User added Successfully !"), HttpStatus.ACCEPTED);
		} catch (InvalidRequestException e) {
			return new ResponseEntity<Status>(new Status(0, e.toString()), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<Status>(new Status(0, e.toString()), HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(path = "", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Metric> readMetrics() {
		List<Metric> metricsList = objMetricsService.getMetrics();
		return metricsList;

	}

	@RequestMapping(path = "/query", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Metric> readMetricsByTime(@RequestParam(required = false, value = "from") long from,
			@RequestParam(required = false, value = "to") long to) {
		if (from == 0 || to == 0 || from > to) {
			return new ArrayList<Metric>();
		}
		List<Metric> metricsList = objMetricsService.getMetrics(from, to);
		return metricsList;
	}

}
