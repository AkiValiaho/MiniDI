package model;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Aki on 29.9.2017.
 */
class MatchedPair {
    private Field matchedField;
    private Object matchedInstance;
    private Field[] declaredFields;
    private Object[] fieldInjectedInstances;

    MatchedPair(Field[] declaredFields, Object[] fieldInjectedInstances, Object instanceToInjectTo) throws IllegalAccessException {
        this.declaredFields = declaredFields;
        this.fieldInjectedInstances = fieldInjectedInstances;
        setFieldsToInstance(instanceToInjectTo);
    }

    private MatchedPair(Field field, Object instance) {
        this.matchedField = field;
        this.matchedInstance = instance;
    }

    private void setFieldsToInstance(Object o) throws IllegalAccessException {
        List<MatchedPair> matchedPairs = matchPairs();
        matchedPairs
                .forEach(pair -> {
                    try {
                        pair.setMatchedFieldToInstance(o);
                    } catch (IllegalAccessException e) {
                        System.exit(1);
                    }
                });
    }

    private List<MatchedPair> matchPairs() {
        OrderedClassEntitites orderedClassEntitites = new OrderedClassEntitites()
                .sortEntities();
        List<Field> orderedFields = orderedClassEntitites.getOrderedFields();
        List<Object> orderedInstances = orderedClassEntitites.getOrderedInstances();
        return createListOfMatchedPairs(orderedFields, orderedInstances);
    }

    private List<MatchedPair> createListOfMatchedPairs(List<Field> orderedFields, List<Object> orderedInstances) {
        List<MatchedPair> matchedPairs = new ArrayList<>();
        final Iterator<Field> fieldIterator = orderedFields.iterator();
        for (Object instance : orderedInstances) {
            Field field = findMatchingField(fieldIterator, instance);
            matchedPairs.add(new MatchedPair(field, instance));
        }
        return matchedPairs;
    }

    private Field findMatchingField(Iterator<Field> fieldIterator, Object instance) {
        Field field = fieldIterator.next();
        while (instance.getClass().equals(field.getClass())) {
            field = fieldIterator.next();
        }
        field.setAccessible(true);
        return field;
    }

    private void setMatchedFieldToInstance(Object o) throws IllegalAccessException {
        matchedField.set(o, matchedInstance);
    }

    private class OrderedClassEntitites {
        private List<Field> orderedFields;
        private List<Object> orderedInstances;

        public List<Field> getOrderedFields() {
            return orderedFields;
        }

        public List<Object> getOrderedInstances() {
            return orderedInstances;
        }

        public OrderedClassEntitites sortEntities() {
            orderedFields = sortFields(Arrays.asList(declaredFields));
            orderedInstances = sortInstances(Arrays.asList(fieldInjectedInstances));
            return this;
        }

        private List<Field> sortFields(List<Field> fields) {
            fields.sort((Comparator.comparing(Field::getName)));
            return fields;
        }

        private List<Object> sortInstances(List<Object> objects) {
            objects.sort((Comparator.comparing(o -> o.getClass().getCanonicalName())));
            return objects;
        }
    }
}
