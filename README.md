####exerciseTasks
This is a project with the target to log meta data for JUnit tests which were created for programming books or 
coding dojos

*It is also a simple example of how to write your own runner for JUnit:-)*

**Usage sample:**
```java
@Exercise(name="Chapter XYZ - ...",
        topics = {"Lambdas","sam types", "interface default methods", "..."})
@RunWith(ExerciseRunner.class)
public class ChapterXYZ{
    ...
```

```java
@Test
@Task(signature = "xyz.1.1a",topics = {"lambda", "..."})
public void test11a {
    ...
```