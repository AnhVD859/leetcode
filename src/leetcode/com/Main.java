package leetcode.com;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {
    // 1207. Unique Number of Occurrences
    public static boolean uniqueOccurrences(int[] arr) {
        int[] occurrences = new int[2001];
        for (int i : arr) {
            occurrences[i + 1000] += 1;
        }

        Set<Integer> rs = new HashSet<>();
        for (int num : occurrences) {
            if (num != 0 && !rs.add(num)) {
                return false;
            }
        }

        return true;
    }

    // 1657. Determine if Two Strings Are Close
    public static boolean closeStrings(String word1, String word2) {
        if (word1.length() != word2.length()) return false;
        if (word1.equals(word2)) return true;
        int[] frequencies1 = new int[26];
        int[] frequencies2 = new int[26];
        for (char c : word1.toCharArray()) {
            frequencies1[c - 'a']++;
        }
        for (char c : word2.toCharArray()) {
            if (frequencies1[c - 'a'] == 0) return false;
            frequencies2[c - 'a']++;
        }

        Arrays.sort(frequencies1);
        Arrays.sort(frequencies2);
        for (int i = 0; i < 26; i++) {
            if (frequencies1[i] != frequencies2[i]) return false;
        }

        return true;
    }

    // 2352. Equal Row and Column Pairs
    public static int equalPairs(int[][] grid) {
        Map<Long, Integer> check = new HashMap<>();
        for (int[] row : grid) {
            long value = 0L;
            for (int num : row) {
                value = value * 10 + num;
            }
            check.put(value, check.getOrDefault(value, 0) + 1);
        }

        for (int[] row : grid) {
            long value = 0L;
            for (int j = 0; j < grid.length; j++) {
                value = value * 10 + row[j];
            }
//            check.computeIft()
        }


        return check.values().stream().reduce(0, Integer::sum);
    }

    // 2390. Removing Stars From a String
    public String removeStars(String s) {
        char[] chars = s.toCharArray();
        char[] resultChars = new char[chars.length];
        int rsLength = 0;
        for (char aChar : chars) {
            if (aChar != '*') {
                resultChars[rsLength++] = aChar;
            } else {
                rsLength--;
            }
        }
        StringBuilder rsStringBuilder = new StringBuilder();
        for (int i = 0; i < rsLength; i++) {
            rsStringBuilder.append(resultChars[i]);
        }

        return rsStringBuilder.toString();
    }

    // 735. Asteroid Collision
//    @SuppressWarnings("all")
    public static int[] asteroidCollision(int[] asteroids) {
        if (asteroids.length < 2) return asteroids;
        int i = 0, j = 1;
        int[] result = new int[asteroids.length];
        result[0] = asteroids[0];
        boolean isNotEmpty = true;
        for (j = 1; j < asteroids.length; j++) {
            if (isNotEmpty && result[i] > 0 && asteroids[j] < 0) {
                if (Math.abs(result[i]) < Math.abs(asteroids[j])) {
                    if (i != 0) {
                        i--;
                        j--;
                    } else {
                        result[0] = asteroids[j];
                    }
                } else if (Math.abs(result[i]) == Math.abs(asteroids[j])) {
                    i--;
                    isNotEmpty = i != -1;
                }
            } else {
                result[++i] = asteroids[j];
                isNotEmpty = true;
            }
        }
        if (!isNotEmpty) {
            return new int[0];
        }
        int[] finalResult = new int[i + 1];
        System.arraycopy(result, 0, finalResult, 0, i + 1);

        return finalResult;
    }

    // 394. Decode String
    public static String decodeString(String s) {
        return decodeString(s.toCharArray(), 0).split(":")[0];
    }

    public static String decodeString(char[] inputArr, int start) {
        int number = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = start; i < inputArr.length; i++) {
            char current = inputArr[i];
            if ('0' <= current && current <= '9') {
                number = number * 10 + (current - '0');
            } else if (current == '[') {
                String[] rs = decodeString(inputArr, i + 1).split(":");
                i = Integer.parseInt(rs[1]);
                stringBuilder.append(String.valueOf(rs[0]).repeat(Math.max(0, number)));
                number = 0;
            } else if (current == ']') {
                return stringBuilder.append(":").append(i).toString();
            } else {
                stringBuilder.append(current);
            }
        }
        return stringBuilder.append(":").append(inputArr.length - 1).toString();
    }

    // 649. Dota2 Senate
    public static String predictPartyVictory(String senate) {
        return predictPartyVictory(senate.toCharArray(), 0);
    }

    public static String predictPartyVictory(char[] arr, int party) {
        int i = 0, r = 0, d = 0;
        while (i < arr.length) {
            char current = arr[i++];
            if (current == 'D') {
                d++;
                if (party < 0) {
                    arr[i - 1] = '-';
                    d--;
                }
                party++;
            } else if (current == 'R') {
                r++;
                if (party > 0) {
                    arr[i - 1] = '-';
                    r--;
                }
                party--;
            }
        }
        if (d > 0 && r == 0) return "Dire";
        if (r > 0 && d == 0) return "Radiant";

        return predictPartyVictory(arr, party);
    }

    // 2095. Delete the Middle Node of a Linked List
    public static class ListNode {
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

    public ListNode deleteMiddle(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return null;

        int c = 1;
        ListNode preMid = head, mid = head;
        ListNode p = head;
        while (p.next != null) {
            p = p.next;
            c++;
            if (c % 2 == 0 || c < 3) {
                preMid = mid;
                mid = mid.next;
            }
        }

        preMid.next = mid.next;
        return head;
    }

    // 328. Odd Even Linked List
    public static ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode evenNext = head;
        ListNode oddStart = head.next;
        ListNode oddNext = oddStart;
        ListNode q = head;
        int count = 0;
        while (q != null) {
            if (count % 2 == 0 && evenNext.next != null) {
                ListNode temp = evenNext.next.next;
                evenNext.next = temp;
                if (temp != null) evenNext = temp;
            } else if (count % 2 == 1 && oddNext != null && oddNext.next != null) {
                ListNode temp = oddNext.next.next;
                oddNext.next = temp;
                oddNext = temp;
            }
            count++;
            q = q.next;
        }
        evenNext.next = oddStart;

        return head;
    }

    public static ListNode buildFromArray(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        System.out.println("size: " + arr.length);
        ListNode head = new ListNode(arr[0]);
        ListNode prev = head;

        for (int i = 1; i < arr.length; i++) {
            ListNode next = new ListNode(arr[i]);
            prev.next = next;
            prev = next;
        }
        return head;
    }

    public static String traverse(ListNode head) {
        if (head == null) return "";
        ListNode q = head;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        while (q != null) {
            stringBuilder.append(q.val).append(",");
            q = q.next;
        }
        stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "").append("]");
        return stringBuilder.toString();
    }

    // 206. Reverse Linked List
    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        if (head.next.next == null) {
            ListNode p1 = head.next;
            p1.next = head;
            head.next = null;
            return p1;
        }
        ListNode p1 = head, p2 = head.next, p3 = head.next.next;
        head.next = null;
        while (p3 != null) {
            p2.next = p1;
            ListNode temp = p3.next;
            p3.next = p2;
            p1 = p2;
            p2 = p3;
            p3 = temp;
        }
        return p2;
    }

    // 2130. Maximum Twin Sum of a Linked List
    public static int pairSum(ListNode head) {
        ListNode p = head;
        int size = 0;
        while (p != null) {
            size++;
            p = p.next;
        }
        size /= 2;
        int[] halfArr = new int[size];
        p = head;
        int max = 0, c = 0, inc = 1;
        while (p != null) {
            if (inc > 0) {
                halfArr[c++] = p.val;
            } else {
                int sum = halfArr[--c] + p.val;
                max = Math.max(max, sum);
            }
            p = p.next;
            if (c == size) inc = -1;
        }

        return max;
    }


    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // 872. Leaf-Similar Trees
    private void traverseLeaf(TreeNode root, List<Integer> leafList) {
        if (root.left == null && root.right == null) leafList.add(root.val);
        if (root.left != null) traverseLeaf(root.left, leafList);
        if (root.right != null) traverseLeaf(root.right, leafList);
    }

    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> leafList1 = new ArrayList<>();
        List<Integer> leafList2 = new ArrayList<>();

        traverseLeaf(root1, leafList1);
        traverseLeaf(root2, leafList2);

        String root1Leaves = leafList1.stream().map(String::valueOf).collect(Collectors.joining(","));
        String root2Leaves = leafList2.stream().map(String::valueOf).collect(Collectors.joining(","));
        return root1Leaves.equals(root2Leaves);
    }

    // 1448. Count Good Nodes in Binary Tree
    public int traverseGood(TreeNode root, int maxValue, int count) {
        if (root == null) return 0;
        if (maxValue <= root.val) {
            count++;
            maxValue = root.val;
        }
        return count + traverseGood(root.left, maxValue, 0) + traverseGood(root.right, maxValue, 0);
    }

    public int goodNodes(TreeNode root) {
        return traverseGood(root, Integer.MIN_VALUE, 0);
    }

    // 437. Path Sum III
    public static int pathSum(TreeNode root, int targetSum) {
        if (root == null) return 0;
        int rs = pathSum(root, targetSum, 0, 0);
        return rs + pathSum(root.left, targetSum) + pathSum(root.right, targetSum);
    }

    public static int pathSum(TreeNode root, int targetSum, int prevSum, int count) {
        if (root == null) return count;
        prevSum += root.val;
        if (prevSum == targetSum) {
            count++;
        }
        return count + pathSum(root.left, targetSum, prevSum, 0) + pathSum(root.right, targetSum, prevSum, 0);
    }

    public static TreeNode buildFromArray(Integer[] input) {
        if (input.length == 0) return null;
        TreeNode head = new TreeNode(input[0]);
        List<TreeNode> lastLevel = new ArrayList<>(List.of(head));
        int idx = 0;
        boolean left = true;

        for (int i = 1; i < input.length; i++) {
            if (idx == lastLevel.size()) {
                idx = 0;
                lastLevel = lastLevel.stream()
                        .map(e -> {
                            List<TreeNode> data = new ArrayList<>();
                            if (e == null) return data;
                            if (e.left != null) data.add(e.left);
                            if (e.right != null) data.add(e.right);
                            return data;
                        })
                        .collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
            }
            TreeNode leaf = lastLevel.get(idx);
            if (left) {
                left = false;
                leaf.left = input[i] == null ? null : new TreeNode(input[i]);
            } else {
                left = true;
                leaf.right = input[i] == null ? null : new TreeNode(input[i]);
                idx++;
            }
        }
        return head;
    }

    // 2337. Move Pieces to Obtain a String
    public static boolean canChange(String start, String target) {
        if (start.length() != target.length()) return false;
        if (start.length() == 1) return start.equals(target);
        char[] startArr = start.toCharArray();
        char[] targetArr = target.toCharArray();
        int n = startArr.length;
        int s = 0;
        int t = 0;
        while (s < n || t < n) {
            char cs = s < n ? startArr[s] : '*';
            char ct = t < n ? targetArr[t] : '*';
            if (cs == '_' && ct != '_') s++;
            else if (cs != '_' && ct == '_') t++;
            else {
                if (cs != ct) return false;
                if (cs == 'R' && s > t) return false;
                if (cs == 'L' && s < t) return false;
                s++;
                t++;
            }
        }
        return true;
    }

    private static int getCharValue(char c) {
        return c == '_' ? 0 : (int) c;
    }

    public static void main(String[] args) {
        System.out.println(canChange(
                "_L__R__R_L",
                "L______RR_")
        );
//        System.out.println(canChange("R_L_", "__LR"));
//        System.out.println(canChange("_R", "R_"));
    }

}