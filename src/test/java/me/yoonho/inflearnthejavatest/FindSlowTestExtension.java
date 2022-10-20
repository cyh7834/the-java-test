package me.yoonho.inflearnthejavatest;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

// 테스트 메소드가 실행된 시간을 계산하여 일정 시간이 넘었을 때 느린 테스트임을 알려준다.
public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private static final long THRESHOLD = 1000L;

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        String testClassName = extensionContext.getRequiredTestClass().getName();
        String testMethodName = extensionContext.getRequiredTestMethod().getName();
        ExtensionContext.Store store = extensionContext.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));

        store.put("START_TIME", System.currentTimeMillis());

    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        String testClassName = extensionContext.getRequiredTestClass().getName();
        String testMethodName = extensionContext.getRequiredTestMethod().getName();
        ExtensionContext.Store store = extensionContext.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));

        long startTime = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - startTime;

        if (duration > THRESHOLD) {
            System.out.print("Please consider mark method + " + testMethodName + " with @SlowTest.\n");
        }
    }
}
