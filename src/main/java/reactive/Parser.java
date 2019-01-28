package reactive;

import reactive.action.ActionHandlers;
import reactive.action.ActionRunner;
import environment.EnvironmentState;
import krislet.Memory;
import krislet.SendCommand;
import org.json.JSONArray;
import org.json.JSONObject;
import reactive.action.NoActionRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Parser {
    private HashMap<EnvironmentState, ActionRunner> environmentActionHashMap;
    private ActionHandlers actionHandlers;
    private ActionRunner defaultActionRunner;
    private Memory memory;

    public Parser(Memory memory, SendCommand sendCommand) {
        this.memory = memory;
        actionHandlers = new ActionHandlers(sendCommand);
        environmentActionHashMap = new HashMap<>();

        JSONObject jsonObject = loadJsonFile("reactive.json");
        JSONArray jMatchersArray = jsonObject.getJSONArray("matchers");

        JSONObject defActionObj = jsonObject.getJSONObject("defaultAction");
        defaultActionRunner = parseAction(defActionObj);

        for (int i = 0; i < jMatchersArray.length(); i++) {
            JSONObject jMatcher = jMatchersArray.getJSONObject(i);
            JSONObject jMatcherAction = jMatcher.getJSONObject("action");
            String jEnvironmentMatcher = jMatcher.getString("environment");

            ActionRunner actionRunner = parseAction(jMatcherAction);

            EnvironmentState environmentState = parseEnvironment(jEnvironmentMatcher);
            environmentActionHashMap.put(environmentState, actionRunner);
        }


    }

    private JSONObject loadJsonFile(String resourceName) {
        try {
            URL resource = getClass().getClassLoader().getResource(resourceName);
            Path filePath = Paths.get(resource.toURI());
            String content = new String(Files.readAllBytes(filePath));

            return new JSONObject(content);
        } catch (IOException | URISyntaxException | NullPointerException e) {
            e.printStackTrace();
            throw new NullPointerException("Failed to load JSON file");
        }
    }

    private ActionRunner parseAction(JSONObject jMatcherAction) {
        if (jMatcherAction.getString("action").equalsIgnoreCase("none"))
            return new NoActionRunner(actionHandlers, memory);

        return ActionRunner.ParseFromJsonObject(actionHandlers, memory, jMatcherAction);
    }

    private EnvironmentState parseEnvironment(String jEnvironmentMatcher) {
        return EnvironmentState.valueOf(jEnvironmentMatcher);
    }

    public ActionRunner getActionRunner(EnvironmentState environmentState) {
        return environmentActionHashMap.get(environmentState);
    }

    public ActionRunner getDefaultActionRunner() {
        return defaultActionRunner;
    }
}
