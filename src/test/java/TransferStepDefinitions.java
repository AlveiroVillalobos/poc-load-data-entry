import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TransferStepDefinitions {

    @Given("step 1 transfer test 1 feature")
    public void transferTestStep1(){
        System.out.println("Transfer test step1");
    }

    @When("step 2 transfer test 1 feature")
    public void transferTestStep2(DataTable dataTable){
        System.out.println("Transfer test step2");
    }

    @Then("step 3 transfer test 1 feature")
    public void transferTestStep3(){
        System.out.println("Transfer test step3");
    }
}
