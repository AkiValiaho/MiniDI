package tooling;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Aki on 29.9.2017.
 */
public class ClassNameMatcher {
    public boolean classNamesMatch(List<Object> objects, List<Class> classes) {
        if (objects.size() != classes.size()) {
            return false;
        }
        final Iterator<Object> objectIterator = objects.iterator();
        final Iterator<Class> classIterator = classes.iterator();
        while (classIterator.hasNext()) {
            final Class next = classIterator.next();
            final Object next1 = objectIterator.next();
            if (!next.getName().equals(next1.getClass().getName())) {
                return false;
            }
        }
        return true;
    }

    public boolean classNamesMatch(Field matchedField, Object matchedInstance) {
        return matchedField.getType().equals(matchedInstance.getClass());
    }
}
