package tooling;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Aki on 29.9.2017.
 */
class ClassNameMatcher {
    boolean allClassNamesMatch(List<Object> objects, List<Class> classes) {
        if (objects.size() != classes.size()) {
            return false;
        }
        final Iterator<Object> objectIterator = objects.iterator();
        for (Class next : classes) {
            final Object next1 = objectIterator.next();
            if (!next.getName().equals(next1.getClass().getName())) {
                return false;
            }
        }
        return true;
    }
}
