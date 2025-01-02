#include <iostream>
#include <queue>
#include <string>
#include <regex>

class BSTC {
    struct Node {
        long long key;
        std::string value;
        Node* left = nullptr;
        Node* right = nullptr;
        Node* parent = nullptr;
    };

    Node* root = nullptr;

    static void zag(Node* x) {
        if (Node* parent = x->parent) {
            x->parent = parent->parent;
            if (parent->parent) {
                if (parent->parent->left == parent) {
                    parent->parent->left = x;
                }
                else {
                    parent->parent->right = x;
                }
            }
            parent->right = x->left;
            if (x->left) {
                x->left->parent = parent;
            }
            x->left = parent;
            parent->parent = x;
        }
    }

    static void zig(Node* x) {
        if (Node* parent = x->parent) {
            x->parent = parent->parent;
            if (parent->parent) {
                if (parent->parent->left == parent) {
                    parent->parent->left = x;
                }
                else {
                    parent->parent->right = x;
                }
            }
            parent->left = x->right;
            if (x->right) {
                x->right->parent = parent;
            }
            x->right = parent;
            parent->parent = x;
        }
    }

    void splay(Node* x) {
        if (!x) return;
        while (x->parent) {
            Node* parent = x->parent;

            if (Node* grandparent = parent->parent; !grandparent) {
                if (parent->left == x) zig(x);
                else zag(x);
            }
            else if (grandparent->left == parent && parent->left == x) {
                zig(parent);
                zig(x);
            }
            else if (grandparent->right == parent && parent->right == x) {
                zag(parent);
                zag(x);
            }
            else if (grandparent->left == parent && parent->right == x) {
                zag(x);
                zig(x);
            }
            else if (grandparent->right == parent && parent->left == x) {
                zig(x);
                zag(x);
            }
        }
        root = x;
    }

public:
    void add(const long long key, const std::string& value) {
        if (!root) {
            root = new Node{ key, value };
            return;
        }
        auto node = new Node{ key, value };
        Node* projection = root;
        Node* parent = nullptr;
        while (projection) {
            parent = projection;
            if (key > projection->key) {
                projection = projection->right;
            }
            else if (key < projection->key) {
                projection = projection->left;
            }
            else {
                if (value == projection->value && key == projection->key) {
                    splay(projection);
                    throw std::runtime_error("error\n");
                }
            }
        }
        node->parent = parent;
        if (key > parent->key) {
            parent->right = node;
        }
        else if (key < parent->key) {
            parent->left = node;
        }
        splay(node);
    }

    Node* search(const long long key) {
        Node *projection = root;
        Node *result = nullptr;
        Node *prev = nullptr;
        while (projection) {
            if (projection->key == key) {
                splay(projection);
                result = projection;
                return result;
            }
            prev = projection;
            projection = (projection->key > key) ? projection->left : projection->right;
        }
        splay(prev);
        return result;
    }

    void set(const long long key, const std::string& value) {
        Node* projection = root;
        Node* prev = nullptr;
        while (projection) {
            if (projection->key == key) {
                projection->value = value;
                splay(projection);
                return;
            }
            prev = projection;
            projection = (projection->key > key) ? projection->left : projection->right;
        }
        splay(prev);
        throw std::runtime_error("error\n");
    }

    std::pair<long long, std::string> min() {
        if (!root) throw std::runtime_error("error\n");
        Node* projection = root;
        while (projection->left) {
            projection = projection->left;
        }
        splay(projection);
        std::pair<long long, std::string> result = std::make_pair(projection->key, projection->value);
        return result;
    }

    std::pair<long long, std::string> max() {
        if (!root) throw std::runtime_error("error\n");
        Node* projection = root;
        while (projection->right) {
            projection = projection->right;
        }
        splay(projection);
        std::pair<long long, std::string> result = std::make_pair(projection->key, projection->value);
        return result;
    }

    void deleteNode(long long key) {
        if (!root) {
            throw std::runtime_error("error\n");
        }
        Node* projection = root;
        Node* prev = nullptr;
        while (projection) {
            if (projection->key == key) break;
            prev = projection;
            projection = (projection->key > key) ? projection->left : projection->right;
        }
        if (!projection) {
            splay(prev);
            throw std::runtime_error("error\n");
        }
        if (!projection->parent && !projection->left && !projection->right) {
            root = nullptr;
            return;
        }
        splay(projection);

        Node* rootleft = root->left;
        Node* rootright = root->right;
        if (!rootleft) {
            root = rootright;
            root->parent = nullptr;
            return;
        }
        if (!rootright) {
            root = rootleft;
            root->parent = nullptr;
            return;
        }
        root->left = nullptr;
        root->right = nullptr;
        root = rootleft;
        root->parent = nullptr;
        Node* max = root;
        while (max->right) {
            max = max->right;
        }
        splay(max);
        root->right = rootright;
        root->right->parent = root;
    }

    void print(std::ostream& out) const {
        if (!root) {
            out << "_\n";
            return;
        }
        out << "[" << root->key << " " << root->value << "]\n";
        std::deque<Node*> q;
        int indexlast = -1;
        if (root->left) {
            q.push_back(root->left);
            indexlast = 0;
        }
        if (root->right) {
            if (!root->left) q.push_back(nullptr);
            q.push_back(root->right);
            indexlast = 1;
        }
        int indexlastbef = indexlast;
        int levelSize = 2;
        while (indexlast != -1) {
            indexlast = -1;
            int tr = 0;
            for (auto visiting : q) {
                if (visiting) {
                    if (visiting->left) indexlast = tr * 2;
                    if (visiting->right) indexlast = tr * 2 + 1;
                }
                ++tr;
            }
            int checker = -1;
            for (int i = 0; i <= indexlastbef; ++i) {
                Node *visiting = q.front();
                q.pop_front();
                if (checker == indexlast) {
                    out << (visiting ? "[" + std::to_string(visiting->key) + " " + visiting->value + " " + std::to_string(visiting->parent->key) + "] " : "_ ");
                }
                else {
                    out << (visiting ? "[" + std::to_string(visiting->key) + " " + visiting->value + " " + std::to_string(visiting->parent->key) + "] " : "_ ");
                    q.push_back((visiting ? visiting->left : nullptr));
                    ++checker;
                    if (checker == indexlast) continue;
                    q.push_back((visiting ? visiting->right : nullptr));
                    ++checker;
                }
            }
            if (indexlastbef < levelSize - 1) {
                for (int j = indexlastbef; j < levelSize - 1; ++j) {
                    out << "_ ";
                }
            }
            out << "\n";
            if (indexlast == -1) return;
            levelSize *= 2;
            indexlastbef = indexlast;
        }
    }
};

int main() {
    std::string line;
    std::queue<std::string> commands;
    BSTC bt;
    std::ostream& out = std::cout;

    while (std::getline(std::cin, line)) {
        if (line.empty()) continue;
        commands.push(line);
    }

    while (!commands.empty()) {
        try {
            std::string command = commands.front();
            commands.pop();

            if (command == "min") {
                std::pair<long long, std::string> min = bt.min();
                out << min.first << " " << min.second << "\n";
            }
            else if (command == "max") {
                std::pair<long long, std::string> max = bt.max();
                out << max.first << " " << max.second << "\n";
            }
            else if (command == "print") {
                bt.print(out);
            }
            else {
                std::regex regex_pattern(R"(^(search|delete|set|add) (-?\d+)\s*(\S+)?$)");
                if (std::smatch match; regex_search(command, match, regex_pattern)) {
                    std::string action = match[1];
                    std::string val = match[3];
                    long long key = stoll(match[2]);
                    std::string value = match.size() > 3 ? val : "";

                    if (action == "search") {
                        out << (bt.search(key) ? "1 " + bt.search(key)->value : "0") << "\n";
                    }
                    else if (action == "delete") {
                        bt.deleteNode(key);
                    }
                    else if (action == "set") {
                        bt.set(key, value);
                    }
                    else if (action == "add") {
                        bt.add(key, value);
                    }
                }
                else {
                    out << "error\n";
                }
            }
        }
        catch (std::runtime_error& e) {
            out << e.what();
        }
    }
    return 0;
}