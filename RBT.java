import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String [] args) {
        RBT rbt = new RBT();
        Scanner sc = new Scanner(System.in);
        Queue<String> commands = new LinkedList<>();
        PrintStream out = new PrintStream(System.out);

        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.isEmpty()) {
                break;
            }
            commands.add(line);
        }
        sc.close();
        while (!commands.isEmpty()) {
            try {
                String command = commands.poll();
                if (command.equals("print")) {
                    rbt.print(out);
                }
                else if (command.matches("^(search|delete) -?\\d+$")) {
                    String[] parts = command.split(" ");
                    String action = parts[0];
                    switch (action) {
                        case "search":
                            long key = Long.parseLong(parts[1]);
                            System.out.println((rbt.search(key) != null) ? "1 " + rbt.search(key) : "0");
                            break;
                        case "delete":
                            rbt.delete(Long.parseLong(parts[1]));
                            break;
                    }
                }
                else if (command.matches("^(add) -?\\d+ \\S+$")) {
                    String[] parts = command.split(" ");
                    rbt.add(Long.parseLong(parts[1]), parts[2]);
                }
                else {
                    System.out.println("error");
                }
            }
            catch (Exception e) {
                System.out.print(e.getMessage());
            }
        }
    }
}
class RBT {
    private static class Node {
        boolean color; //TRUE == RED, FALSE == BLACK
        long key;
        String value;
        Node left = null;
        Node right = null;
        Node parent;
    }
    private Node root = null;

    public void add(long key, String value) {
        if (root == null) {
            root = new Node();
            root.key = key;
            root.value = value;
            root.color = false;
            return;
        }
        Node node = new Node();
        Node projection = root;
        node.key = key;
        node.value = value;
        while (true) {
            if (key >= projection.key) {
                if (projection.right == null) {
                    node.parent = projection;
                    projection.right = node;
                    break;
                }
                projection = projection.right;
            } else {
                if (projection.left == null) {
                    node.parent = projection;
                    projection.left = node;
                    break;
                }
                projection = projection.left;
            }
        }
        node.color = true;
        korrektur(node);
    }
    public String search(long key) {    //IMPORTED FROM BST
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
    public void delete(long key) {
        Node d = find(key);
        if (d != null) {
            if (d.left == null && d.right == null) { //DELETE WITH NO KIDS
                deleteWithNoKids(d);
            }
            else if (d.left != null && d.right != null) { //DELETE WITH TWO KIDS
                deleteWithTwoKids(d);
            }
            else {
                deleteWithOneKid(d);
            }
        }
    }
    private void deleteWithNoKids(Node d) {
        if (d == root) {
            root = null;
            return;
        }
        if (d.color) {                  //NO-KID NODE IS RED
            if (d.parent.left == d) {
                d.parent.left = null;
            }
            else {
                d.parent.right = null;
            }
            d.parent = null;
        }
        else {                         //NO-KID NODE IS BLACK
            deleteWithNoKidsKorrektur(d);
            if (d.parent.left == d) {
                d.parent.left = null;
            }
            else {
                d.parent.right = null;
            }
            d.parent = null;
        }
    }
    private void deleteWithTwoKids(Node d) {
        Node rc = replaceCandidate(d);
        swap(d, rc);
        if (rc.left == null && rc.right == null) {
            deleteWithNoKids(rc);
        }
        else {
            deleteWithOneKid(rc);
        }
    }
    private void deleteWithOneKid(Node d) {
        if (d.left != null) {
            swap(d, d.left);
            if (d.left.left == null && d.left.right == null) {
                deleteWithNoKids(d.left);
            }
            else if (d.left.left != null && d.left.right != null) {
                deleteWithTwoKids(d.left);
            }
        }
        else {
            swap(d, d.right);
            if (d.right.left == null && d.right.right == null) {
                deleteWithNoKids(d.right);
            }
            else if (d.right.left != null && d.right.right != null) {
                deleteWithTwoKids(d.right);
            }
        }
    }
    private void deleteWithNoKidsKorrektur(Node d) {
        if (d.parent == null) {
            return;
        }
        if (d.parent.left == d) {   //BRO'S RIGHT
            Node b = d.parent.right;
            if (!b.color) {         //IF BRO'S BLACK
                if (b.right != null && b.right.color || b.left != null && b.left.color) {    //AND ONE OF HIS KID'S RED
                    if (b.right != null && b.right.color) {                //RIGHT KID'S RED, LEFT B
                        b.color = d.parent.color;
                        d.parent.color = false;
                        b.right.color = false;
                        zag(b);
                        if (b.parent == null) root = b;
                    }
                    else {                              //LEFT KID'S REG, RIGHT B
                        b.left.color = false;
                        b.color = true;
                        zig(b.left);
                        b.color = d.parent.color;
                        d.parent.color = false;
                        b.right.color = false;
                        zig(b);
                        if (b.parent == null) root = b;
                    }
                }
                else {                                  //OMG ALL KIDS ARE BLACK
                    boolean pcolorprevstate = d.parent.color;
                    b.color = true;
                    d.parent.color = false;
                    if (!pcolorprevstate) {
                        deleteWithNoKidsKorrektur(d.parent);
                    }
                }
            }
            else {                                              //BRO'S RED
                b.color = false;
                d.parent.color = true;
                zag(b);
                if (b.parent == null) root = b;
                deleteWithNoKidsKorrektur(d);
            }
        }
        else {                              //BRO'S LEFT
            Node b = d.parent.left;
            if (!b.color) {         //IF BRO'S BLACK
                if (b.right != null && b.right.color || b.left != null && b.left.color) {    //AND ONE OF HIS KID'S RED
                    if (b.left.color) {                //LEFT KID'S RED, RIGHT B/R
                        b.color = d.parent.color;
                        d.parent.color = false;
                        b.left.color = false;
                        zig(b);
                        if (b.parent == null) root = b;
                    }
                    else {                              //RIGHT KID'S REG, LEFT B
                        b.right.color = false;
                        b.color = true;
                        zag(b.right);
                        b.color = d.parent.color;
                        d.parent.color = false;
                        b.left.color = false;
                        zag(b);
                        if (b.parent == null) root = b;
                    }
                }
                else {                                  //OMG ALL KIDS ARE BLACK
                    boolean pcolorprevstate = d.parent.color;
                    b.color = true;
                    d.parent.color = false;
                    if (!pcolorprevstate) {
                        deleteWithNoKidsKorrektur(d.parent);
                    }
                }
            }
            else {                                              //BRO'S RED
                b.color = false;
                d.parent.color = true;
                zig(b);
                if (b.parent == null) root = b;
                deleteWithNoKidsKorrektur(d);
            }
        }
    }
    private Node find(long key) {
        Node projection = root;
        while (projection != null) {
            if (projection.key == key) {
                return projection;
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
    private void swap(Node a, Node b) {
        long tempkey = a.key;
        String tempvalue = a.value;
        a.key = b.key;
        a.value = b.value;
        b.key = tempkey;
        b.value = tempvalue;
    }
    private void korrektur(Node n) {
        Node p, g, u;
        while (n.parent != null && n.parent.color) {
            p = n.parent;
            g = p.parent;
            u = (p == g.left) ? g.right : g.left;
            if (u != null && u.color) {
                p.color = false;
                u.color = false;
                g.color = g != root;
                n = g;
            }
            else {
                if (g.left == p && p.left == n) {
                    p.color = !p.color;
                    g.color = !g.color;
                    zig(p);
                    if (p.parent == null) {
                        root = p;
                    }
                }
                else if (g.right == p && p.right == n) {
                    p.color = !p.color;
                    g.color = !g.color;
                    zag(p);
                    if (p.parent == null) root = p;
                }
                else if (g.left == p && p.right == n) {
                    n.color = !n.color;
                    g.color = !g.color;
                    zag(n);
                    zig(n);
                    if (n.parent == null) root = n;
                }
                else if (g.right == p && p.left == n) {
                    n.color = !n.color;
                    g.color = !g.color;
                    zig(n);
                    zag(n);
                    if (n.parent == null) root = n;
                }
            }
        }
    }
    private void zag(Node x) {      //IMPORTED FROM SPLAY TREE
        Node parent = x.parent;
        if (parent != null) {
            x.parent = parent.parent;
            if (parent.parent != null) {
                if (parent.parent.left == parent) {
                    parent.parent.left = x;
                }
                else {
                    parent.parent.right = x;
                }
            }
            parent.right = x.left;
            if (x.left != null) {
                x.left.parent = parent;
            }
            x.left = parent;
            parent.parent = x;
        }
    }
    private void zig(Node x) {      //IMPORTED FROM SPLAY TREE
        Node parent = x.parent;
        if (parent != null) {
            x.parent = parent.parent;
            if (parent.parent != null) {
                if (parent.parent.left == parent) {
                    parent.parent.left = x;
                }
                else {
                    parent.parent.right = x;
                }
            }
            parent.left = x.right;
            if (x.right != null) {
                x.right.parent = parent;
            }
            x.right = parent;
            parent.parent = x;
        }
    }
    private Node replaceCandidate(Node x) {
        Node max = x.left;
        Node min = x.right;
        int i = 0;
        int j = 0;
        for (; max.right != null; i++) {
            max = max.right;
        }
        for (; min.left != null; j++) {
            min = min.left;
        }
        return i > j ? max : min;
    }
    public void print(PrintStream out) {        //IMPORTED FROM BST
        if (root == null) { System.out.println("_"); return; }
        else out.append("[%d %s]\n".formatted(root.key, root.color ? "r" : "b"));

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
                level.append("[%d %s] ".formatted(visiting.left.key, visiting.left.color ? "r" : "b"));
            }
            if (visiting.right == null) {
                ++nullnodes;
                level.append("_ ");
            } else {
                level.append("[%d %s] ".formatted(visiting.right.key, visiting.right.color ? "r" : "b"));
            }
            counter += 2;
        }
    }
}