package com.app.runnners;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"json:target/cucumber.json",
                "html:target/htmlreprt.html",
               // "rerun:target/rerun.txt"
                },
        features = "src/test/resources/features",
        glue =  "com/app/stepdefinitions",
        strict = true,
        dryRun = false,
        tags = "@smoke11"
)
public class CukesRunner {

}
