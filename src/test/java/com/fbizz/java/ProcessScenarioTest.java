package com.fbizz.java;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.run.ProcessRunner.ExecutableRunner;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.init;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class ProcessScenarioTest {

    private static final String PROCESS_DEFINITION_KEY = "java-camunda";

    @Autowired
    private ProcessEngine processEngine;

    static {
        LogFactory.useSlf4jLogging(); // MyBatis
    }

    @Rule
    @ClassRule
    public static ProcessEngineRule rule;

    @PostConstruct
    void initRule() {
        rule = TestCoverageProcessEngineRuleBuilder.create(processEngine).build();
    }

    @Before
    public void setup() {
        init(processEngine);
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private ProcessScenario myProcess;

    @Test
    @Deployment(resources = "process.bpmn") // only required for process test coverage
    public void testHappyPath() {
        // Define scenarios by using camunda-bpm-assert-scenario:

        ExecutableRunner starter = Scenario.run(myProcess) //
                .startByKey(PROCESS_DEFINITION_KEY);

        when(myProcess.waitsAtReceiveTask(anyString())).thenReturn((messageSubscription) -> {
            messageSubscription.receive();
        });
        when(myProcess.waitsAtUserTask(anyString())).thenReturn((task) -> {
            task.complete();
        });

        // OK - everything prepared - let's go and execute the scenario
        Scenario scenario = starter.execute();

        // now you can do some assertions
        verify(myProcess).hasFinished("EndEvent");
    }

}
