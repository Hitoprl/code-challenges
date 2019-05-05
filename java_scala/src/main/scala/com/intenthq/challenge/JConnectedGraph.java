package com.intenthq.challenge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JConnectedGraph {
	// Find if two nodes in a directed graph are connected.
	// Based on http://www.codewars.com/kata/53897d3187c26d42ac00040d
	// For example:
	// a -+-> b -> c -> e
	// |
	// +-> d
	// run(a, a) == true
	// run(a, b) == true
	// run(a, c) == true
	// run(b, d) == false
	public static boolean run(JNode source, JNode target) {
		if (source.equals(target)) {
			return true;
		}
		return run(source, target, new HashSet<>());
	}

	private static boolean run(JNode source, JNode target, Set<JNode> visited) {
		visited.add(source);
		for (JNode adj : source.edges) {
			if (visited.contains(adj)) {
				continue;
			}
			if (target.equals(adj)) {
				return true;
			}
			boolean result = run(adj, target, visited);
			if (result) {
				return true;
			}
		}
		visited.remove(source);
		return false;
	}

	public static class JNode {
		public final int value;
		public final List<JNode> edges;

		public JNode(final int value, final List<JNode> edges) {
			this.value = value;
			this.edges = edges;
		}

		public JNode(final int value) {
			this.value = value;
			this.edges = new ArrayList<>();
		}

		public void connect(final JNode node) {
			edges.add(node);
		}
	}

}
