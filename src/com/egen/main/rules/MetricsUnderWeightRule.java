package com.egen.main.rules;

import org.easyrules.core.BasicRule;

import com.egen.main.entity.Alert;
import com.egen.main.entity.Metric;
import com.egen.main.service.MetricsService;

public class MetricsUnderWeightRule extends BasicRule {

	private double BASE_WEIGHT;

	private Metric metrics;

	private MetricsService objMetricsService;

	private boolean isPost;

	public void setMetricsService(MetricsService objMetricsService) {
		this.objMetricsService = objMetricsService;
	}

	public MetricsUnderWeightRule(double baseWT, boolean isPost) {
		super("UnderWeightRule", "Check if person's weight is < 10% of base weight, if yes create an alert.", 1);
		BASE_WEIGHT = baseWT;
		this.isPost = isPost;
	}

	public Metric getMetrics() {
		return metrics;
	}

	public void setMetrics(Metric metrics) {
		this.metrics = metrics;
	}

	public double getBASE_WEIGHT() {
		return BASE_WEIGHT;
	}

	public void setBASE_WEIGHT(double bASE_WEIGHT) {
		BASE_WEIGHT = bASE_WEIGHT;
	}

	public MetricsService getObjMetricsService() {
		return objMetricsService;
	}

	public void setObjMetricsService(MetricsService objMetricsService) {
		this.objMetricsService = objMetricsService;
	}

	@Override
	public boolean evaluate() {
		return metrics.getValue() / BASE_WEIGHT < 0.9;
	}

	@Override
	public void execute() {
		StringBuilder objBuilder = new StringBuilder(
				"Weight has dropped below 10% of base weight. Current weight is: ");
		objBuilder.append(metrics.getValue());
		Alert objAlert = null;
		if (isPost) {
			try {
				objAlert = objMetricsService.postAlert(objBuilder.toString());
				System.out.println("Alert created " + objAlert);
			} catch (Exception e) {
				System.out.println("ERROR :: Alert creation failed " + e.getMessage());
			}
		} else {
			objAlert = objMetricsService.createAlert(objBuilder.toString());
			System.out.println("Alert created " + objAlert);
		}
	}
}
