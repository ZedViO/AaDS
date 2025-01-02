import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        List<String> commands = new ArrayList<>();
        BSTC bt = new BSTC();
        PrintStream out = System.out;

        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.isEmpty()) {
                continue;
            }
            commands.add(line);
        }
        sc.close();

        for (String command : commands) {
            try {
                if (command.equals("min")) {
                    Node min = bt.min();
                    System.out.println(min.key + " " + min.value);
                }
                else if (command.equals("max")) {
                    Node max = bt.max();
                    System.out.println(max.key + " " + max.value);
                }
                else if (command.equals("print")) {
                    bt.print(out);
                }
                else if (command.matches("^search -?\\d+$")) {
                    long key = Long.parseLong(command.split(" ")[1]);
                    System.out.println(bt.search(key) != null ? "1 " + bt.search(key) : "0");
                }
                else if (command.matches("^delete -?\\d+$")) {
                    bt.delete(Long.parseLong(command.split(" ")[1]));
                }
                else if (command.matches("^set -?\\d+ \\S+$")) {
                    bt.set(Long.parseLong(command.split(" ")[1]), command.split(" ")[2]);
                }
                else if (command.matches("^add -?\\d+ \\S+$")) {
                    bt.add(Long.parseLong(command.split(" ")[1]), command.split(" ")[2]);
                }
            }
            catch (RuntimeException e) {
                System.out.print(e.getMessage());
            }
        }
    }
}
class Node {
    long key;
    String value;
    Node left, right, parent = null;
}
class BSTC {
    private Node root = null;

    public void add(long key, String value) {
        if (root == null) {
            root = new Node();
            root.value = value;
            root.key = key;
            return;
        }
        Node node = new Node();
        Node projection = root;
        node.value = value;
        node.key = key;
        while (true) {
            if (key >= projection.key) {
                if (value.equals(projection.value) && key == projection.key) {
                    throw new RuntimeException("error\n");
                }
                if (projection.right == null) {
                    node.parent = projection;
                    projection.right = node;
                    break;
                }
                projection = projection.right;
            }
            else {
                if (projection.left == null) {
                    node.parent = projection;
                    projection.left = node;
                    break;
                }
                projection = projection.left;
            }
        }
    }
    public String search(long key) {
        Node projection = root;
        while (projection != null) {
            if (projection.key == key) {
                return projection.value;
            }
            else if (projection.key > key) {
                projection = projection.left;
            }
            else {
                projection = projection.right;
            }
        }
        return null;
    }
    public void set(long key, String value) {
        Node projection = root;
        while (projection != null) {
            if (projection.key == key) {
                projection.value = value;
                break;
            }
            else if (projection.key > key) {
                projection = projection.left;
            }
            else {
                projection = projection.right;
            }
        }
        if (projection == null) { throw new RuntimeException("error\n"); }
    }
    public Node min() {
        if (root == null) throw new RuntimeException("error\n");
        Node projection = root;
        while (projection.left != null) {
            projection = projection.left;
        }
        return projection;
    }
    public Node max() {
        if (root == null) throw new RuntimeException("error\n");
        Node projection = root;
        while (projection.right != null) {
            projection = projection.right;
        }
        return projection;
    }
    public void delete(long key) {
        Node projection = root;
        while (projection != null) {
            if (projection.key == key) {
                break;
            }
            else if (projection.key > key) {
                projection = projection.left;
            }
            else {
                projection = projection.right;
            }
        }
        if (projection == null) { throw new RuntimeException("error\n"); }

        if (projection.left == null && projection.right == null) {
            if (projection.parent != null) {
                if (projection.parent.left == projection) {
                    projection.parent.left = null;
                } else {
                    projection.parent.right = null;
                }
            } else {
                root = null;
            }
        }
        else if (projection.right == null) {
            if (projection.parent != null) {
                if (projection.parent.left == projection) {
                    projection.parent.left = projection.left;
                } else {
                    projection.parent.right = projection.left;
                }
                projection.left.parent = projection.parent;
            } else {
                root = projection.left;
                projection.left.parent = null;
            }
        } else if (projection.left == null) {
            if (projection.parent != null) {
                if (projection.parent.left == projection) {
                    projection.parent.left = projection.right;
                } else {
                    projection.parent.right = projection.right;
                }
                projection.right.parent = projection.parent;
            } else {
                root = projection.right;
                projection.right.parent = null;
            }
        }
        else {
            Node successor = projection.left;
            while (successor.right != null) {
                successor = successor.right;
            }
            delete(successor.key);
            projection.key = successor.key;
            projection.value = successor.value;
        }
    }
    public void print(PrintStream out) {
        if (root == null) { System.out.println("_"); return; }
        else out.append("[%d %s]\n".formatted(root.key, root.value));

        Queue<Node> q = new LinkedList<>();
        StringBuilder level = new StringBuilder();
        int nullnodes = 0;
        int counter = 0;
        int nodesinline = 2;
        q.add(root);
        while (nullnodes != nodesinline) {
            if (counter == nodesinline) {
                out.append(level.toString()).append("\n");
                nodesinline *= 2;
                counter = 0;
                nullnodes = 0;
                level.setLength(0);
            }
            Node visiting = q.poll();
            try {
                q.add(visiting.left);
                q.add(visiting.right);
            } catch (NullPointerException e) {
                q.add(null);
                q.add(null);
                nullnodes += 2;
                counter += 2;
                level.append("_ _ ");
                continue;
            }
            if (visiting.left == null) {
                ++nullnodes;
                level.append("_ ");
            } else {
                level.append("[%d %s %d] ".formatted(visiting.left.key, visiting.left.value, visiting.key));
            }
            if (visiting.right == null) {
                ++nullnodes;
                level.append("_ ");
            } else {
                level.append("[%d %s %d] ".formatted(visiting.right.key, visiting.right.value, visiting.key));
            }
            counter += 2;
        }
    }
}