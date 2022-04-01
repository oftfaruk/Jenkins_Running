$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/features/login.feature");
formatter.feature({
  "name": "Login the page",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "Login",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@smoke11"
    }
  ]
});
formatter.step({
  "name": "User is on the login page",
  "keyword": "Given "
});
formatter.match({
  "location": "com.app.stepdefinitions.MyStepdefs.userIsOnTheLoginPage()"
});
formatter.result({
  "status": "passed"
});
});