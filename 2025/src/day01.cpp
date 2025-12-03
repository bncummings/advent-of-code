#include <iostream>
#include <fstream>
#include <regex>

using namespace std;

struct instruction {
    char dir;
    int degree;
};

vector<instruction> read_instructions(const string& filepath) {
    std::ifstream MyFile(filepath);

    vector<instruction> instrs;
    regex matcher("([LR])([0-9]+)");
    smatch matches;

    string line;
    while (getline(MyFile, line)) {
        if(regex_match(line, matches, matcher)) {
            instrs.emplace_back(instruction{
                .dir =  matches[1].str()[0],
                .degree = std::stoi(matches[2].str())
            });
        } else {
            throw std::runtime_error("Failed to read instruction.");
        }
    }
    // Close the file
    MyFile.close();
    return instrs;
}

int combinations(const vector<instruction>& instrs) {
    int zeros = 0;
    int dial = 50;

    for (const auto& instr : instrs) {
        switch (instr.dir) {
            case 'L': {
                auto dest = (dial - instr.degree);
                auto rem = dest % 100;

                /* we add an extra zero if the parity changes from positive to neg or arrives at 0 */
                zeros += -dest / 100 + (dial > 0 && dest <= 0);
                dial = rem >= 0 ? rem : rem + 100;
                break;
            }
            case 'R': {
                auto dest = (dial + instr.degree);

                zeros += dest / 100;
                dial = dest % 100;
                break;
            }
            default:
                throw std::runtime_error("Illegal State Exception.");
        }
    }

    return zeros;
}

int main() {
    vector<instruction> instrs = read_instructions("../res/day01.txt");
    cout << combinations(instrs);
    return 0;
}




