
import com.utils.LoadData;
import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("/features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "io.cucumber.core.plugin.SerenityReporterParallel,pretty,timeline:build/test-results/timeline")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME,value = "@test1")
public class Runner {

    @BeforeSuite
    public static void setup() {
        LoadData.loadDataFromExcel("./src/test/resources/features");
    }
}
