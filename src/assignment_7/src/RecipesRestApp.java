package assignment_7.src;

import controllers.ControllerOperators;
import controllers.ControllerRecipes;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class RecipesRestApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(ControllerOperators.class);
        classes.add(ControllerRecipes.class);
        return classes;
    }
}
