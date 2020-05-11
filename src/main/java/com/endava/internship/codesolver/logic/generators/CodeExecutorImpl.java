package com.endava.internship.codesolver.logic.generators;

import com.endava.internship.codesolver.logic.converter.RuntimeCompilerExecutor;
import com.endava.internship.codesolver.logic.dto.TaskResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CodeExecutorImpl implements CodeExecutor {

    private static final String CLASS_NAME = "MyClass";

    private static final String PACKAGE = "package com.endava.internship.codesolver.logic.converter.generated;\n"
            + "import com.endava.internship.codesolver.logic.converter.testentities.Privilege;\n" +
            "import com.endava.internship.codesolver.logic.converter.testentities.User;\n" +
            "import java.util.*;\n" +
            "import java.util.Optional;\n" +
            "import java.util.Map.Entry;\n" +
            "import static java.util.Arrays.asList;\n" +
            "import java.util.*;\n" +
            "import java.util.Map.Entry;\n" +
            "import java.util.function.Function;\n" +
            "import java.util.function.Predicate;\n" +
            "import java.util.stream.Collectors;\n" +
            "import static java.util.Arrays.asList;\n" +
            "import static java.util.Collections.singletonList;\n" +
            "import static java.util.Collections.emptyList;\n" +
            "import java.util.function.Function;\n" +
            "import java.util.function.Predicate;\n" +
            "import java.util.stream.Collectors;\n" +
            "import static java.util.stream.Collectors.*;\n" +
            "  public class ";

    private final RuntimeCompilerExecutor runtimeCompilerExecutor;

    private static String generateString(String userCode, String taskTests) {
        return PACKAGE + CLASS_NAME + " { " + userCode + taskTests + "}";
    }


    public TaskResult execute(final String solution, final String tests) {

        List<Boolean> listOfRes = runtimeCompilerExecutor.executeUserCode(CLASS_NAME, generateString(solution, tests));
        String errorStack = runtimeCompilerExecutor.getErrorStack();

        return TaskResult.builder()
                .successful(areAllTestsPassed(listOfRes))
                .compiled(!listOfRes.isEmpty())
                .results(buildResults(listOfRes, errorStack))
                .build();
    }

    private String buildResults(List<Boolean> listOfResults, String errorStack) {

        if (listOfResults.isEmpty()) {
            return errorStack;
        }

        int numberOfPassedTests = 0, currentTes = 1;

        StringBuilder result = new StringBuilder("Tests:\n");

        for (Boolean testRes : listOfResults) {
            if (testRes) {

                numberOfPassedTests++;

                result.append("\tTest ").append(currentTes++).append(". Status: passed\n");
            } else {
                result.append("\tTest ").append(currentTes++).append(". Status: failed\n");
            }
        }

        result.append("\nTOTAL: ").append(numberOfPassedTests).append("/").append(listOfResults.size());
        result.append("\nPERCENTAGE: ").append(getPercentage(numberOfPassedTests, listOfResults.size())).append(" %");

        return result.toString();
    }

    private boolean areAllTestsPassed(List<Boolean> results) {

        return results.size() == results.stream()
                .filter(r -> r)
                .count();
    }

    private double getPercentage(int numberOfPassedTests, int numberOfTests) {
        return (double) numberOfPassedTests / (double) numberOfTests * 100;
    }

}
