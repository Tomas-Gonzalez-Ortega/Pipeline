import java.util.*;
import java.util.stream.*;
import java.time.*;
import java.lang.reflect.*;

public class TestRunner {

	private final Class clazz;
	private final List<AssertionError> failures;
	private final List<Throwable> errors;
	private int run = 0;
	private int passed = 0;

	private TestRunner(Class clazz) {
		this.clazz = clazz;
		this.failures = new LinkedList<AssertionError>();
		this.errors = new LinkedList<Throwable>();
	}

	private TestRunner(String testClassName) throws ClassNotFoundException {
		this(Class.forName(testClassName));
	}

	private void runTestsViaReflection() {
		System.err.println("Running test harness: " + clazz.getName());

		final LocalDateTime start = LocalDateTime.now();

		Stream.of(clazz.getDeclaredMethods())
			.filter(m -> m.getName().startsWith("test"))
			.filter(m -> m.getGenericParameterTypes().length == 0)
			.sequential()
			.forEach(this::runTest);

		final LocalDateTime end = LocalDateTime.now();
		final Duration dur = Duration.between(start, end);
		System.err.println("\nDuration: " + dur);

		if(failures.size() == 0 && errors.size() == 0) {
			System.err.println(passed + " test(s) passed.");
		} else {
			System.err.format("%d test(s) run: %d passed, %d failed, %d error(s).\n",
				run, passed, failures.size(), errors.size());
			System.err.println("\n***** FAILED *****\n");

			Stream.concat(failures.stream(), errors.stream())
				.sequential()
				.forEachOrdered(Throwable::printStackTrace);
		}
	}

	private void runTest(Method m) {
		try {
			Object instance = null;
			if((m.getModifiers() & Modifier.STATIC) != 0) {
				instance = null;
			} else {
				instance = clazz.newInstance();
			}
			m.setAccessible(true);
			m.invoke(instance);
			passed++;
			System.err.print('.');
		} catch(InstantiationException | IllegalAccessException | IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch(InvocationTargetException e) {
			Throwable cause = e.getCause();

			int localStack = invokeThrowException().getStackTrace().length-2;
			StackTraceElement[] oldStack = cause.getStackTrace();
			StackTraceElement[] newStack = new StackTraceElement[oldStack.length - localStack];
			System.arraycopy(oldStack, 0, newStack, 0, newStack.length);
			cause.setStackTrace(newStack);

			if(cause instanceof AssertionError) {
				failures.add((AssertionError) cause);
				System.err.print('X');
			} else {
				errors.add(cause);
				System.err.print('E');
			}
		}
		run++;
	}

	private static RuntimeException invokeThrowException() {
		try {
			TestRunner.class.getDeclaredMethod("throwException").invoke(null);
		} catch (InvocationTargetException e) {
			return (RuntimeException) e.getCause();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	private static void throwException() {
		throw new RuntimeException();
	}

	public static void main(String[] args) throws Exception {
		if(args.length != 1) {
			System.err.println("Usage: java -ea TestRunner <testclassname>");
			System.exit(1);
		}
		new TestRunner(args[0]).runTestsViaReflection();
	}

	static {
		boolean assertsEnabled = false;
		assert assertsEnabled = true; // Intentional side effect!!!
		if (!assertsEnabled) {
			throw new IllegalArgumentException("Asserts must be enabled!!! java -ea");
		}
	}
}
