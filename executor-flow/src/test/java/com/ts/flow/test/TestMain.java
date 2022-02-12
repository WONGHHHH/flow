package com.ts.flow.test;

import com.ts.flow.context.DataContext;
import com.ts.flow.service.ServiceLauncher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class TestMain {

    @Test
	public void testExecute(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-*.xml");
        ServiceLauncher serviceLauncher = new ServiceLauncher();
        serviceLauncher.setApplicationContext(ctx);
        serviceLauncher.setResource("D:\\IDEA_WORK\\flow-old\\executor-flow\\flow");
        serviceLauncher.init();
        serviceLauncher.execute("TEST","TESTFLOW",new DataContext());
    }
}

