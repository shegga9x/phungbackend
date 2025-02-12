package com.example.backend;

public class BackendAppli1cation {

	public static void main(String[] args) {

		BackendAppli1cation app = new BackendAppli1cation();
		String s = "ccccccccccccbbd";

		System.out.println(app.longestPalindrome(s));

	}




	
	public String longestPalindrome(String s) {
		if (s.length() <= 1) {
			return s;
		}
		int charRePosi = s.substring(1).indexOf(s.charAt(0)) + 1;
		String result = "";
		String temp = "";
		if (charRePosi != -1) {
			result = s.substring(0, charRePosi + 1);
			temp = s.substring(1, charRePosi);
		} else {
			return result;
		}
		return longestPalindrome(temp, result);
	}

	public String longestPalindrome(String s, String temp) {
		if (s.length() <= 1) {
			return temp;
		}
		int charRePosi = s.substring(1).indexOf(s.charAt(0)) + 1;
		String result = "";
		if (charRePosi != -1) {
			result = s.substring(0, charRePosi + 1);
			temp = s.substring(1, charRePosi);
		} else {
			return temp;
		}
		return longestPalindrome(temp, result);
	}

}
