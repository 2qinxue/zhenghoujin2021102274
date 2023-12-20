package com.jnu.yidongzuoye.data;
import androidx.test.platform.app.InstrumentationRegistry;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.ArrayList;

public class DataBankTaskTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
    }

    public void testTasksInput() {

    }

    public void testSaveTasks() {
        ArrayList<Task> task1 = new ArrayList<Task>();
        task1.add(new Task("创新工程实践30分钟", "30", "1/100"));
        task1.add(new Task("阅读信息安全教学基础（第2版）100分钟", "100", "0/100"));

        String filename = "DataBank_Test";
        DataBankTask dataBank = new DataBankTask();
        dataBank.saveTasks(InstrumentationRegistry.getInstrumentation().getTargetContext(),task1,filename);
        ArrayList<Task> task2 = dataBank.tasksInput(InstrumentationRegistry.getInstrumentation().getTargetContext(),filename);
        assertNotSame(task1,task2);
        assertEquals(task1.size(),task2.size());
        for(int i=0;i<task1.size();i++) {
            Assert.assertNotSame(task1.get(i),task2.get(i));
            Assert.assertEquals(task1.get(i).getTaskName(),task2.get(i).getTaskName());
            Assert.assertEquals(task1.get(i).getScore(),task2.get(i).getScore());
            Assert.assertEquals(task1.get(i).getNum(),task2.get(i).getNum());
        }
    }
}