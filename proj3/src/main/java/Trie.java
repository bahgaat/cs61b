
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class Trie {
    private Node node; /* the root node. */

    private static class Node {
        private boolean isKey;
        private HashMap<Character, Node> next;


        private Node(boolean isKey, HashMap<Character, Node> next) {
            this.isKey = isKey;
            this.next = next;
        }
    }

    /* initialize an empty trie. */
    public Trie() {
        node = new Node(false, new HashMap<>());
    }

    /* put the string into the trie. */
    public void put(String key) {
        putHelper(node, key, 0, key.length() - 1);
    }

    private void putHelper(Node node, String key, int charIndex, int lastChar) {
        Node newNode;
        if (charIndex > lastChar) {
            return;
        }
        char character = key.charAt(charIndex);
        if (node.next.containsKey(character)) {
            newNode = node.next.get(character);
        } else {
            if (lastChar == charIndex) {
                newNode = new Node(true, new HashMap<>());
                node.next.put(character, newNode);
                return;
            } else {
                newNode = new Node(false, new HashMap<>());
                node.next.put(character, newNode);
            }
        }
        putHelper(newNode, key, charIndex += 1, lastChar);
    }

    /* return all the strings in the trie which begin with the specific prefix. */
    public List<String> keyWithPrefix(String prefix) {
        //TODO return the node with the new prefix in a hashmap. Iam thinking
        // maybe an alternative solution is to convert the prefix into array and then
        // change it as i like
        char[] prefixArray = prefix.toCharArray();
        Node neededNode = findTheNode(node, prefixArray, 0,
                prefixArray.length - 1);
        ArrayList list = new ArrayList();
        prefix = String.valueOf(prefixArray);
        if (neededNode != null) {
            Iterable iterable = neededNode.next.keySet();
            Iterator iterator = iterable.iterator();
            while (iterator.hasNext()) {
                char character = (char) iterator.next();
                collect(prefix+""+character, list, neededNode.next.get(character));
            }
        }
        return list;
    }

    /* find the node of the last matching character in the prefix. */
    private Node findTheNode(Node node, char[] prefixArray, int charIndex, int lastIndex) {
        char character = prefixArray[charIndex];
        Node newNode = findTheNodeHelper(character, node, prefixArray,
                charIndex, lastIndex);
        if (newNode == null) {
            return null;
        } else if (charIndex == lastIndex) {
            return newNode;
        } else {
            return findTheNode(newNode, prefixArray, charIndex += 1, lastIndex);
        }
    }

    private Node findTheNodeHelper(char character, Node node, char[] prefixArray,
                                   int charIndex, int lastIndex) {

        Node newNode = null;
        if (node.next.containsKey(character)) {
            newNode = node.next.get(character);
        } else if (node.next.containsKey(Character.toUpperCase(character))) {
            newNode = node.next.get(Character.toUpperCase(character));
            prefixArray[charIndex] = Character.toUpperCase(character);
        }
        return newNode;
    }

    /* collecting all the keys which starts with s string. */
    private void collect(String s, ArrayList list, Node node) {
        if (node.isKey) {
            list.add(s);
        }
        Iterable iterable = node.next.keySet();
        Iterator iterator = iterable.iterator();
        while (iterator.hasNext()) {
            char character = (char) iterator.next();
            collect(s+""+character, list, node.next.get(character));
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.put("She");
        trie.put("Sells");
        trie.put("Sells");
        trie.put("Sea");
        trie.put("Shells");
        trie.put("by");
        trie.put("the");
        trie.put("Shore");
        trie.put("Sea");
        trie.put("Sea ahead");
        trie.put("sewre");
        trie.put("Tomato");
        List list = trie.keyWithPrefix("t");
    }

}
