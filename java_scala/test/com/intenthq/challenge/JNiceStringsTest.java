package com.intenthq.challenge;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class JNiceStringsTest {

	@Test
	void test() {
		assertEquals(1, JNiceStrings.nice(Arrays.asList("ugknbfddgicrmopn")));
		assertEquals(1, JNiceStrings.nice(Arrays.asList("aaa")));
		assertEquals(0, JNiceStrings.nice(Arrays.asList("xyyaeu")));
		assertEquals(0, JNiceStrings.nice(Arrays.asList("jchzalrnumimnmhp")));
		assertEquals(0, JNiceStrings.nice(Arrays.asList("haegwjzuvuyypxyu")));
		assertEquals(0, JNiceStrings.nice(Arrays.asList("haegwjzuvuyypxy")));
		assertEquals(0, JNiceStrings.nice(Arrays.asList("dvszwmarrgswjxmb")));
		assertEquals(0, JNiceStrings.nice(Arrays.asList("aea")));
		assertEquals(0, JNiceStrings.nice(Arrays.asList("")));
		assertEquals(0, JNiceStrings.nice(Arrays.asList("a")));
		assertEquals(0, JNiceStrings.nice(Arrays.asList("ab")));
	}

}
