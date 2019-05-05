package com.intenthq.challenge;

import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class JEnigma {

  // We have a system to transfer information from one place to another. This system
  // involves transferring only list of digits greater than 0 (1-9). In order to decipher
  // the message encoded in the list you need to have a dictionary that will allow
  // you to do it following a set of rules:
  //    > Sample incoming message: (​1,2,3,7,3,2,3,7,2,3,4,8,9,7,8)
  //    > Sample dictionary (​23->‘N’,234->‘ ’,89->‘H’,78->‘Q’,37 ->‘A’)
  //  - Iterating from left to right, we try to match sublists to entries of the map.
  //    A sublist is a sequence of one or more contiguous entries in the original list,
  //    eg. the sublist (1, 2) would match an entry with key 12, while the sublist (3, 2, 3)
  //    would match an entry with key 323.
  //  - Whenever a sublist matches an entry of the map, it’s replaced by the entry value.
  //    When that happens, the sublist is consumed, meaning that its elements can’t be used
  //    for another match. The elements of the mapping however, can be used as many times as needed.
  //  - If there are two possible sublist matches, starting at the same point, the longest one
  //    has priority, eg 234 would have priority over 23.
  //  - If a digit does not belong to any matching sublist, it’s output as is.
  //
  // Following the above rules, the message would be: “1N73N7 HQ”
  // Check the tests for some other (simpler) examples.

	private final Trie trie;
	final Map<Integer, Character> map;
	final int maxKeyLength;

	private JEnigma(final Map<Integer, Character> map) {
		this.map = map;
		maxKeyLength = (int) Math.log10(map.keySet().stream()
				.max(Comparator.naturalOrder()).get()) + 1;
		trie = new Trie();
		for (Map.Entry<Integer, Character> entry : map.entrySet()) {
			trie.insert(entry.getKey(), entry.getValue());
		}
	}

	public static JEnigma decipher(final Map<Integer, Character> map) {
		return new JEnigma(map);
	}

	public String deciphe(List<Integer> message) {
		return decipheSimple(message);
	}

	public String decipheSimple(List<Integer> messageList) {
		StringBuilder ret = new StringBuilder();
		// Convert list to a string, more convenient to work with.
		String message = messageList.stream()
				.map(Object::toString)
				.reduce("", String::concat);

		int messageLength = message.length();
		for (int i = 0; i < messageLength; i++) {
			int maxSubStrLen = maxKeyLength;
			if (maxSubStrLen + i > messageLength) {
				maxSubStrLen = messageLength - i;
			}
			boolean found = false;
			for (int j = maxSubStrLen; j > 0; j--) {
				int key = Integer.parseInt(message.substring(i, i + j));
				Character c = map.get(key);
				if (c != null) {
					ret.append(c);
					found = true;
					i += j - 1;
					break;
				}
			}
			if (!found) {
				ret.append(message.charAt(i));
			}
		}
		return ret.toString();
	}

	public String decipheTrie(List<Integer> message) {
		StringBuilder ret = new StringBuilder();
		ListIterator<Integer> it = message.listIterator();
		int backtrackIndex = 0;
		// Longest match, contains the Trie node with the longest match found
		Trie longestMatch = trie;
		// Current Trie node, could contain a match or a prefix
		Trie current = trie;

		while (it.hasNext()) {
			int index = it.nextIndex();
			int n = it.next();
			current = current.get(n);
			if (current != null) {
				// Match or partial match (prefix)
				if (current.match()) {
					// We have a Match. Record it as the longest match found
					// and mark next location for backtrack.
					longestMatch = current;
					backtrackIndex = index + 1;
				}
			} else {
				// No match or partial match. Backtrack.
				it = message.listIterator(backtrackIndex);
				if (longestMatch.match()) {
					// If we had a match, output it to the buffer. The algorithm
					// will continue with the next character after the match.
					ret.append(longestMatch.getCharacter());
				} else {
					// If we didn't have a match, output the raw character in the
					// backtrack index. The algorithm will continue with the
					// next character after that one.
					ret.append(it.next());
					backtrackIndex++;
				}
				longestMatch = trie;
				current = trie;
			}
			// If we are about to end the loop, check first that we don't have backtrack
			// characters left to process.
			if (!it.hasNext() && backtrackIndex != it.nextIndex()) {
				// Backtrack.
				it = message.listIterator(backtrackIndex);
				ret.append(it.next());
				backtrackIndex++;
				longestMatch = trie;
				current = trie;
			}
		}
		if (longestMatch.match()) {
			ret.append(longestMatch.getCharacter());
		}
		return ret.toString();
	}

	private static class Trie {
		private Trie[] children = new Trie[9];
		Character chr;

		public Trie get(int n) {
			return children[n - 1];
		}

		public void insert(int number, char chr) {
			String str = String.valueOf(number);
			insert(str, chr);
		}

		public char getCharacter() {
			return chr;
		}

		public boolean match() {
			return chr != null;
		}

		private void insert(String str, char chr) {
			int n = str.charAt(0) - '0';
			Trie child = getOrCreateChild(n);
			if (str.length() == 1) {
				child.chr = chr;
			} else {
				child.insert(str.substring(1), chr);
			}
		}

		private Trie getOrCreateChild(int n) {
			n--;
			Trie ret = children[n];
			if (ret == null) {
				ret = new Trie();
				children[n] = ret;
			}
			return ret;
		}
	}
}