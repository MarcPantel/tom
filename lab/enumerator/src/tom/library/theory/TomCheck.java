package tom.library.theory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.internal.Assignments;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

import tom.library.enumerator.Enumeration;
import tom.library.theory.internal.AssignmentRunner;
import tom.library.theory.internal.ExecutionHandler;
import tom.library.theory.internal.TestObject;
import tom.library.theory.shrink.DefaultShrinkHandler;
import tom.library.theory.shrink.ShrinkHandler;

public final class TomCheck extends Theories {

	private static HashMap<Type, Enumeration<?>> enumerations;
	public TomCheck(Class<?> klass) throws InitializationError {
		super(klass);
	}

	public static Enumeration<?> get(Type type) {
		return enumerations.get(type);
	}

	@Override
	public void run(RunNotifier notifier) {
		TomCheck.initEnumerations(getTestClass().getJavaClass());
		super.run(notifier);
	}

	@Override
	protected void collectInitializationErrors(List<Throwable> errors) {
		validateEnumDecl(errors);
		super.collectInitializationErrors(errors);
	}

	private void validateEnumDecl(List<Throwable> errors) {
		Field[] fields = getTestClass().getJavaClass().getDeclaredFields();

		for (Field field : fields) {
			if (field.getAnnotation(Enum.class) == null) {
				continue;
			}
			if (!Modifier.isStatic(field.getModifiers())) {
				errors.add(new Error("Enum field " + field.getName() + " must be static"));
			}
			if (!Modifier.isPublic(field.getModifiers())) {
				errors.add(new Error("Enum field " + field.getName() + " must be public"));
			}
			if (! field.getType().equals(Enumeration.class)) {
				errors.add(new Error("Enum field " + field.getName() + " must be of type Enumeration<?>"));
			}
		}
	}



	public static void initEnumerations(Class<?> testclass) {
		enumerations = new HashMap<Type, Enumeration<?>>();
		Field[] fields = testclass.getFields();
		for(Field field: fields) {
			if (field.getAnnotation(Enum.class) != null) {
				try {
					ParameterizedType fieldType = (ParameterizedType) field.getGenericType();
					Type dataType = fieldType.getActualTypeArguments()[0];
					enumerations.put(dataType, (Enumeration<?>) field.get(null));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

		}

	}

	@Override
	// just for printing statistics
	protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		Description description = describeChild(method);
		Statement statement = methodBlock(method);
        if (isIgnored(method)) {
            notifier.fireTestIgnored(description);
        } else {
            runLeaf(statement, description, notifier);
            printGeneratedDataStatistic(method, (TheoryAnchor) statement);
        }        
	}
	
	@Override
	// same as original code - just to be sure we use the inner class from TomCheck
    public Statement methodBlock(FrameworkMethod method) {
		// get shrink annotation
        return new TheoryAnchor(method, getTestClass());
    }
	
	// protected in BlockJUnit4ClasRunner (parent of Theories) but not overridden in Theories
	// same code as in BlockJUnit4ClasRunner
	protected boolean isIgnored(FrameworkMethod child) {
		 return child.getAnnotation(Ignore.class) != null;
	}

	protected void printGeneratedDataStatistic(final FrameworkMethod method, final TheoryAnchor anchor) {
		System.out.println(String.format("%s\nGenerated test data: %s\nTested data: %s\nUntested data: %s\n", 
						method.getName(),
						getTotalGeneratedDataFromTheoryAnchor(anchor), 
						getTotalTestedDataFromTheoryAnchor(anchor),
						getTotalUntestedDataFromTheoryAnchor(anchor)));
	}

	protected int getTotalTestedDataFromTheoryAnchor(TheoryAnchor anchor) {
		return anchor.getSuccessCount() + anchor.getFailureCount();
	}

	protected int getTotalGeneratedDataFromTheoryAnchor(TheoryAnchor anchor) {
		return getTotalTestedDataFromTheoryAnchor(anchor) + anchor.getViolationAssumptionCount();
	}
	
	protected int getTotalUntestedDataFromTheoryAnchor(TheoryAnchor anchor) {
		return anchor.getViolationAssumptionCount();
	}
	
	public static class TheoryAnchor extends Statement {
		private TestObject testObject;
	    //private ShrinkHandler shrinkHandler;
	    private ExecutionHandler handler;
	    
	    public TheoryAnchor(FrameworkMethod method, TestClass testClass) {
	       testObject = new TestObject(method, testClass);
	    }
	    
		private ShrinkHandler newHandlerInstance() {
			ShrinkHandler handler = null;
			try {
				handler = getShrinkHandlerClass().getConstructor(TestObject.class).newInstance(testObject);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			return handler;
		}
	    
	    private Class<? extends ShrinkHandler> getShrinkHandlerClass() {
	    	Class<? extends ShrinkHandler> shrinkClass;
	    	if (testObject.getMethod().getAnnotation(Shrink.class) != null) {
	    		shrinkClass = testObject.getMethod().getAnnotation(Shrink.class).handler();
			} else {
				shrinkClass = DefaultShrinkHandler.class;
			}
	    	return shrinkClass;
	    }
	    
	    @Override
	    public void evaluate() throws Throwable {
	    	handler = new ExecutionHandler(newHandlerInstance());
	    	AssignmentRunner runner = new AssignmentRunner(testObject, handler);
	    	runner.runWithAssignment(getUnassignedAssignments());
	    	
	        if (handler.getSuccessCount() == 0) {
	            Assert.fail("Never found parameters that satisfied method assumptions.\n"
	                    + "  Violated assumptions: " + handler.getInvalidParameters());
	        }
	    }


		private Assignments getUnassignedAssignments() throws Exception {
			return Assignments.allUnassigned(testObject.getMethod(), testObject.getTestClass());
		}
		
		public int getSuccessCount() {
			return handler.getSuccessCount();
		}
		
		public int getViolationAssumptionCount() {
			return handler.getAssumptionViolationCount();
		}
		
		public int getFailureCount() {
			return handler.getFailureCount();
		}
	}
}
