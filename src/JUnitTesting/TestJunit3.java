package JUnitTesting;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import junit.framework.AssertionFailedError;
import junit.framework.TestResult;

public class TestJunit3 extends TestResult {
   // add the error
   public synchronized void addError(Test test, Throwable t) {
      super.addError((junit.framework.Test) test, t);
   }

   // add the failure
   public synchronized void addFailure(Test test, AssertionFailedError t) {
      super.addFailure((junit.framework.Test) test, t);
   }
	
   @Test
   public void testAdd() {
      // add any test
	   String str = "cool";
	   assertEquals("cool", str);
   }
   
   // Marks that the test run should stop.
   public synchronized void stop() {
      //stop the test here
   }
}