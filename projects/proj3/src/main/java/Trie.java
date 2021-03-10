
/*import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
 */
import java.util.*;

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
        ArrayList<String> listOfPrefix = new ArrayList();
        ArrayList<Node> listOfNodes = new ArrayList();
        findTheNodes(node, prefix, 0,
                prefix.length() - 1, listOfPrefix, "", listOfNodes);
        ArrayList list = new ArrayList();
        for (int i = 0; i < listOfPrefix.size(); i += 1) {
            Node nodeHelper = listOfNodes.get(i);
            String prefixHelper = listOfPrefix.get(i);
            Iterable iterable = nodeHelper.next.keySet();
            Iterator iterator = iterable.iterator();
            if (!iterator.hasNext()) {
                list.add(listOfPrefix.get(i));
            }
            while (iterator.hasNext()) {
                char character = (char) iterator.next();
                collect(prefixHelper+""+character, list, nodeHelper.next.get(character));
            }
        }
        return list;
    }

    /* find the nodes of the last matching character in the prefix,
    and add them to listOfNodes. Also, found all the possible prefix. For eg,
    if the user type "mo" Find if "mo" or "mO" or "Mo" or "mO" exists and add
    them to the listOfPrefix. */
    private void findTheNodes(Node node, String prefix, int charIndex,
                              int lastIndex, List listOfPrefix, String string,
                              List listOfNodes) {
        Node newNode1;
        String string1;
        Node newNode2 = null;
        String string2 = null;
        if (charIndex > lastIndex) {
            listOfPrefix.add(string);
            listOfNodes.add(node);
        } else {
            char character = prefix.charAt(charIndex);
            if (Character.isAlphabetic(character)) {
                newNode1 = findTheNodeHelper(Character.toUpperCase(character), node);
                string1 = findTheString(Character.toUpperCase(character), node);
                newNode2 = findTheNodeHelper(Character.toLowerCase(character), node);
                string2 = findTheString(Character.toLowerCase(character), node);
            } else {
                newNode1 = findTheNodeHelper(character, node);
                string1 = findTheString(character, node);
            }
            if (newNode1 == null && newNode2 == null) {
                return;
            } else if (newNode1 == null) {
                findTheNodes(newNode2, prefix, charIndex += 1,
                        lastIndex, listOfPrefix, string+""+string2, listOfNodes);
            } else if (newNode2 == null) {
                findTheNodes(newNode1, prefix, charIndex += 1,
                        lastIndex, listOfPrefix, string+""+string1, listOfNodes);
            } else {
                findTheNodes(newNode1, prefix, charIndex += 1,
                        lastIndex, listOfPrefix, string+""+string1, listOfNodes);
                findTheNodes(newNode2, prefix, charIndex,
                        lastIndex, listOfPrefix, string+""+string2, listOfNodes);
            }

        }
    }

    private Node findTheNodeHelper(char character, Node node) {
        Node newNode = null;
        if (node.next.containsKey(character)) {
            newNode = node.next.get(character);
        }
        return newNode;
    }

    private String findTheString(char character, Node node) {
        if (node.next.containsKey(character)) {
            return String.valueOf(character);
        } else {
            return null;
        }
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
        trie.put("Sea ahead");
        trie.put("Sea ahead");
        trie.put("22");
        List list = trie.keyWithPrefix("2");
    }

}