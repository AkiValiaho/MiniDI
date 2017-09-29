package tooling;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Aki on 29.9.2017.
 */
class ClassNameMatcher {
    private final List<Object> objects;
    private final List<Class> classes;

    ClassNameMatcher(List<Object> objects, List<Class> classes) {
        this.objects = objects;
        this.classes = classes;
    }

    boolean classNamesMatch() {
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
}
