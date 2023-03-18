package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import views.Program;

class MyTests {

	@Test
	public void testAdd() {
		int a = 4;
		int b = 5;
		int result = Program.add(a, b);
		assertEquals(9, result);
	}

}
