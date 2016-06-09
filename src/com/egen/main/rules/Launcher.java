package com.egen.main.rules;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;

import com.egen.main.entity.Metric;
import com.egen.main.service.MetricsService;

public class Launcher {

	private MetricsUnderWeightRule underWT;

	private MetricsOverWeightRule overWT;

	private RulesEngine rulesEngine;

	public Launcher(double baseWT, boolean isPost) {

		underWT = new MetricsUnderWeightRule(baseWT, isPost);
		overWT = new MetricsOverWeightRule(baseWT, isPost);

		// create a rules engine
		rulesEngine = RulesEngineBuilder.aNewRulesEngine().named("Weight rules engine").build();

		// register rules
		rulesEngine.registerRule(underWT);
		rulesEngine.registerRule(overWT);
	}

	public void executeRules(Metric objMetric, MetricsService objMetricsService) {
		underWT.setMetrics(objMetric);
		underWT.setMetricsService(objMetricsService);
		overWT.setMetrics(objMetric);
		overWT.setMetricsService(objMetricsService);

		rulesEngine.fireRules();
	}
}
