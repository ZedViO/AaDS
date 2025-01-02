#include <iostream>
#include <vector>
#include <queue>
#include <stack>
#include <algorithm>
#include <string>
#include <sstream>
#include <unordered_map>
#include <unordered_set>

using namespace std;

int main() {
    setlocale(LC_ALL, "Russian");
    string graphType, startVertex, searchType;
    cin >> graphType >> startVertex >> searchType;
    cin.ignore();

    unordered_map<string, vector<string>> neigh;
    string line;

    while (getline(cin, line)) {
        if (line.empty()) {
            break;
        }
        istringstream iss(line);
        string v1, v2;
        iss >> v1 >> v2;

        if (graphType == "d") {
            neigh[v1].push_back(v2);
        }
        else {
            neigh[v1].push_back(v2);
            neigh[v2].push_back(v1);
        }
    }

    unordered_set<string> visited;

    if (searchType == "d") {
        stack<string> s;
        s.push(startVertex);

        while (!s.empty()) {
            string visiting = s.top();
            s.pop();

            if (visited.find(visiting) == visited.end()) {
                visited.insert(visiting);
                cout << visiting << endl;
            }

            if (neigh.find(visiting) != neigh.end()) {
                vector<string>& vertexNeigh = neigh[visiting];
                sort(vertexNeigh.begin(), vertexNeigh.end());

                for (auto it = vertexNeigh.rbegin(); it != vertexNeigh.rend(); ++it) {
                    if (visited.find(*it) == visited.end()) {
                        s.push(*it);
                    }
                }
            }
        }
    }
    else {
        queue<string> q;
        q.push(startVertex);

        while (!q.empty()) {
            string visiting = q.front();
            q.pop();

            if (visited.find(visiting) == visited.end()) {
                visited.insert(visiting);
                cout << visiting << endl;
            }

            if (neigh.find(visiting) != neigh.end()) {
                vector<string>& vertexNeigh = neigh[visiting];
                sort(vertexNeigh.begin(), vertexNeigh.end());

                for (const auto& vertex : vertexNeigh) {
                    if (visited.find(vertex) == visited.end()) {
                        visited.insert(vertex);
                        cout << vertex << endl;
                        q.push(vertex);
                    }
                }
            }
        }
    }
    return 0;
}