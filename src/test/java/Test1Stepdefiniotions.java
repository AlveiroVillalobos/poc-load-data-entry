import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Test1Stepdefiniotions {

    @Given("step 1 test 1 feature")
    public void test1Step1(){
        System.out.println("Test 1 step 1");
    }

    @When("step 2 test 1 feature")
    public void test1Step2(){
        System.out.println("Test 1 step 3");
    }

    @Then("step 3 test 1 feature")
    public void test1Step3(){
        System.out.println("Test 1 step 3");
    }

}
