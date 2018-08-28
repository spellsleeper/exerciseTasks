package de.danri.test.exercisetasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ExerciseRunner extends BlockJUnit4ClassRunner {

    private static final String EXERCISE_EXEC_INFO =
            "executing exercise %s " + "-" + " topics are: %s";
    private static final String TASK_EXEC_INFO = "executing task %s - topics "
            + "are: %s";

    private final Logger logger;


    public ExerciseRunner(Class testClass) throws InitializationError {
        super(testClass);
        logger = LogManager.getLogger(this.getTestClass().getJavaClass());
    }


    @Override
    public void run(RunNotifier notifier) {
        String name = "";
        String[] topics = {};

        if (this.getTestClass().getJavaClass().isAnnotationPresent(Exercise.class)) {
            Exercise exerciseData =
                    (Exercise) this.getTestClass().getJavaClass().getAnnotation(Exercise.class);
            name = exerciseData.name();
            topics = exerciseData.topics();
        }
        name = name.isEmpty() ? this.getClass().getSimpleName() : name;
        logger.info(String.format(EXERCISE_EXEC_INFO, name,
                Arrays.asList(topics).stream().collect(Collectors.joining(", "
                ))));

        super.run(notifier);

    }

    @Override
    protected void runChild(final FrameworkMethod method,
                            RunNotifier notifier) {
        if (method.getAnnotation(Task.class) != null) {
            Task taskData = method.getAnnotation(Task.class);
            String signature = taskData.signature() == null ?
                    method.getName() : taskData.signature();
            String[] topics = taskData.topics();
            logger.info(String.format(TASK_EXEC_INFO, signature,
                    Arrays.asList(topics).stream().collect(Collectors.joining(", "))));
        }
        Description description = describeChild(method);
        if (isIgnored(method)) {
            notifier.fireTestIgnored(description);
        } else {
            runLeaf(methodBlock(method), description, notifier);
        }
    }
}

