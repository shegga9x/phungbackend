package com.example.backend;

public class BackendAppli1cation {

	public static void main(String[] args) {
		BackendAppli1cation app = new BackendAppli1cation();
		String s = "ababccccbsdds";
		System.out.println(app.longestPalindrome(s));

	}

	public String longestPalindrome(String s) {
		String result = "";
		for (int i = 0; i < s.length(); i++) {
			if (s.substring(i + 1).lastIndexOf(s.charAt(i)) != -1) {
				result = s.substring(i, s.lastIndexOf(s.charAt(i)) + 1);
				if (!isPalindrome(result)) {
					return longestPalindrome1(result);
				}
			}
		}
		return (result);
	}

	public String longestPalindrome1(String s) {
		String result = "";
		if (s.substring(1).lastIndexOf(s.charAt(0)) != -1) {
			result = s.substring(0, s.lastIndexOf(s.charAt(0)) + 1);
			if (!isPalindrome(result)) {
				// result = longestPalindrome1(result);
			}
		}
		return (result);
	}

	public boolean isPalindrome(String s) {
		String n1 = "";
		String n2 = "";
		for (int i = 0; i < s.length(); i++) {
			n1 += s.charAt(i);
			n2 += s.charAt(s.length() - i - 1);
		}
		return n1.equals(n2);

	}
}
