package es.codeurjc.ais.tictatoe.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		  plugin = {"pretty"}, 
		  features = { "classpath:es/codeurjc/test/cucumber" },
		  glue = {"es.codeurjc.test.cucumber" })
public class TicTacToeTest {}