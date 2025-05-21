// Code provided by UVic

#include <iostream>
#include <sstream>
#include <vector>
#include <string>
using namespace std;

// Students can add global variables or functions here (e.g., for capacities, flows, BFS, etc.)

// Stores flight-pilot data: flightInfo (e.g. "A3"), pilotInfo (e.g. "012")
static vector<pair<string, string>> flightPilotPairings;

// All lines from stdin
static vector<string> inputLines;

/**
 * Solve one day's allocation using Edmonds-Karp for max-flow.
 * TODO (students):
 *   1) Construct your graph and capacities from flightPilotPairings.
 *   2) Implement the Edmonds-Karp algorithm (BFS in the residual graph).
 *   3) Output the 10-character assignment string or "!" if impossible.
 */
void solveAllocationProblem()
{
    // Example placeholder: remove once you implement Edmonds-Karp
    cout << "__________"; // 10 underscores
}

/**
 * Process input data day by day; each day ends when an empty line is encountered.
 */
void processInputData(const vector<string>& lines)
{
    int i = 0;
    while (i < (int)lines.size())
    {
        flightPilotPairings.clear();

        while (i < (int)lines.size() && !lines[i].empty())
        {
            string line = lines[i++];
            stringstream ss(line);
            vector<string> tokens;
            string token;
            while (ss >> token) { tokens.push_back(token); }
            if (tokens.size() < 2) continue;

            string flightInfo = tokens[0];
            string pilotInfo  = tokens[1];
            if (!pilotInfo.empty() && pilotInfo.back() == ';') pilotInfo.pop_back();
            flightPilotPairings.push_back({flightInfo, pilotInfo});
        }

        if (!flightPilotPairings.empty())
        {
            solveAllocationProblem();
            cout << endl;
        }

        while (i < (int)lines.size() && lines[i].empty()) { i++; }
    }
}

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    string line;
    while (getline(cin, line))
    {
        inputLines.push_back(line);
    }

    processInputData(inputLines);
    return 0;
}
