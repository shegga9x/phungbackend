package com.example.backend;

public class BackendAppli1cation {
	public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
		ListNode result = new ListNode();
		if (list2.val <= list1.val) {
			result = list2;
			list2 = list1;
			list1 = result;
		}
		result = new ListNode();
		while (list1 != null) {
			if (list2 != null && list1.next != null && list1.val <= list2.val) {
				System.out.println(list1.val);
				while (list2 != null && list1.next.val >= list2.val) {
					System.out.println(list2.val);
					list2 = list2.next;
				}
			}
			list1 = list1.next;
		}
		while (list2 != null) {

		}
		while (list1 != null) {
		}
		return null;
	}

	// public static void main(String[] args) {
	// 	BackendAppli1cation app = new BackendAppli1cation();
	// 	ListNode list1 = app.new ListNode(1, app.new ListNode(2, app.new ListNode(3)));
	// 	ListNode list2 = app.new ListNode(4, app.new ListNode(5, app.new ListNode(6)));
	// 	ListNode mergedList = app.mergeTwoLists(list1, list2);
	// 	while (mergedList != null) {
	// 		System.out.print(mergedList.val + " ");
	// 		mergedList = mergedList.next;
	// 	}
	// }

	public class ListNode {
		int val;
		ListNode next;

		ListNode() {
		}

		ListNode(int val) {
			this.val = val;
		}

		ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}
}
