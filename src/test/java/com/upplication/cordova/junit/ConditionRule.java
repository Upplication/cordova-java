/*******************************************************************************
 * Copyright (c) 2013,2014 Rüdiger Herrmann
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * Contributors:
 * Rüdiger Herrmann - initial API and implementation
 * Matt Morrissette - allow to use non-static inner IgnoreConditions
 ******************************************************************************/
package com.upplication.cordova.junit;

import java.lang.reflect.Modifier;

import org.junit.Assume;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * https://gist.github.com/rherrmann/7447571
 */
public class ConditionRule implements MethodRule {

    public interface Condition {
        boolean isSatisfied();
    }


    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        Statement result = base;
        if (hasConditionalIgnoreAnnotation(method)) {
            Condition condition = getCondition(target, method);
            if (!condition.isSatisfied()) {
                result = new IgnoreStatement(condition);
            }
        }
        return result;
    }

    private static boolean hasConditionalIgnoreAnnotation(FrameworkMethod method) {
        return method.getAnnotation(com.upplication.cordova.junit.Condition.class) != null;
    }

    private static Condition getCondition(Object target, FrameworkMethod method) {
        com.upplication.cordova.junit.Condition annotation = method.getAnnotation(com.upplication.cordova.junit.Condition.class);
        return new IgnoreConditionCreator(target, annotation).create();
    }

    private static class IgnoreConditionCreator {
        private final Object target;
        private final Class<? extends Condition> conditionType;

        IgnoreConditionCreator(Object target, com.upplication.cordova.junit.Condition annotation) {
            this.target = target;
            this.conditionType = annotation.value();
        }

        Condition create() {
            checkConditionType();
            try {
                return createCondition();
            } catch (RuntimeException re) {
                throw re;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private Condition createCondition() throws Exception {
            Condition result;
            if (isConditionTypeStandalone()) {
                result = conditionType.newInstance();
            } else {
                result = conditionType.getDeclaredConstructor(target.getClass()).newInstance(target);
            }
            return result;
        }

        private void checkConditionType() {
            if (!isConditionTypeStandalone() && !isConditionTypeDeclaredInTarget()) {
                String msg
                        = "Conditional class '%s' is a member class "
                        + "but was not declared inside the test case using it.\n"
                        + "Either make this class a static class, "
                        + "standalone class (by declaring it in it's own file) "
                        + "or move it inside the test case using it";
                throw new IllegalArgumentException(String.format(msg, conditionType.getName()));
            }
        }

        private boolean isConditionTypeStandalone() {
            return !conditionType.isMemberClass() || Modifier.isStatic(conditionType.getModifiers());
        }

        private boolean isConditionTypeDeclaredInTarget() {
            return target.getClass().isAssignableFrom(conditionType.getDeclaringClass());
        }
    }

    private static class IgnoreStatement extends Statement {
        private final Condition condition;

        IgnoreStatement(Condition condition) {
            this.condition = condition;
        }

        @Override
        public void evaluate() {
            Assume.assumeTrue("Ignored by " + condition.getClass().getSimpleName(), false);
        }
    }

}