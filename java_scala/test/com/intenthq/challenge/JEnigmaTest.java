package com.intenthq.challenge;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class JEnigmaTest {

	@Test
	void test() {
		Map<Integer,Character> map = new HashMap<>();
		map.put(23, 'N');
		map.put(234, ' ');
		map.put(89, 'H');
		map.put(78, 'Q');
		map.put(37, 'A');
		map.put(1237324, 'x');
		map.put(2352327, 'X');
		map.put(2347, '*');
		map.put(732372341, '+');
		JEnigma decipher = JEnigma.decipher(map);

		assertEquals("N", decipher.deciphe(Arrays.asList(2,3)));
		assertEquals("NH", decipher.deciphe(Arrays.asList(2,3,8,9)));
		assertEquals("1N", decipher.deciphe(Arrays.asList(1,2,3)));
		assertEquals(" ", decipher.deciphe(Arrays.asList(2,3,4)));
		assertEquals("1N73N7 HQ", decipher.deciphe(Arrays.asList(1,2,3,7,3,2,3,7,2,3,4,8,9,7,8)));
		assertEquals("1N73N7 HQ9", decipher.deciphe(Arrays.asList(1,2,3,7,3,2,3,7,2,3,4,8,9,7,8,9)));
		assertEquals("  N5N  ", decipher.deciphe(Arrays.asList(2,3,4,2,3,4,2,3,5,2,3,2,3,4,2,3,4)));
		assertEquals("X", decipher.deciphe(Arrays.asList(2,3,5,2,3,2,7)));
		assertEquals("*7", decipher.deciphe(Arrays.asList(2,3,4,7,7)));
	}

}
