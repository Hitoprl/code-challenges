package com.intenthq.challenge;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.intenthq.challenge.JConnectedGraph.JNode;

class JConnectedGraphTest {

	@Test
	void acyclicGraph() {
		JNode dest = new JNode(9);
		JNode n10 = new JNode(10);
		JNode n8 = new JNode(8, Collections.singletonList(dest));
		JNode start = new JNode(3, Arrays.asList(n8, n10));
		JNode start2 = new JNode(11, Arrays.asList(new JNode(2), dest, n10));
		JNode start3 = new JNode(5, Collections.singletonList(start2));

		assertTrue(JConnectedGraph.run(start, dest));
		assertTrue(JConnectedGraph.run(start2, dest));
		assertTrue(JConnectedGraph.run(start, dest));
		assertTrue(JConnectedGraph.run(start2, dest));
		assertTrue(JConnectedGraph.run(start3, dest));
		assertFalse(JConnectedGraph.run(dest, start3));
		assertFalse(JConnectedGraph.run(dest, start2));
		assertFalse(JConnectedGraph.run(start, start2));
	}

	@Test
	void cyclicGraph() {
		JNode dest = new JNode(9);
		JNode n10 = new JNode(10);
		JNode n8 = new JNode(8, Arrays.asList(dest));
		JNode start = new JNode(3, Arrays.asList(n8, n10));
		JNode start2 = new JNode(11, Arrays.asList(new JNode(2), dest, n10));
		JNode start3 = new JNode(5, Arrays.asList(start2));
		dest.connect(start2);

		assertTrue(JConnectedGraph.run(start, dest));
		assertTrue(JConnectedGraph.run(start2, dest));
		assertTrue(JConnectedGraph.run(start, dest));
		assertTrue(JConnectedGraph.run(start2, dest));
		assertTrue(JConnectedGraph.run(start3, dest));
		assertFalse(JConnectedGraph.run(dest, start3));
		assertTrue(JConnectedGraph.run(dest, start2));
		assertTrue(JConnectedGraph.run(start, start2));
	}

	@Test
	void connectedToItself() {
		JNode start = new JNode(1);
	    assertTrue(JConnectedGraph.run(start, start));
	}

}
