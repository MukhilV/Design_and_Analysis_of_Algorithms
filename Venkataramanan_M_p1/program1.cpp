
#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <cmath>
#include <cstring>
#include <algorithm>
#include <regex>
#include <iomanip>
using namespace std;


/* A Model class to mock the properties of every item in the program */
class Item
{
public:
    int price;
    int marketPrice;
    int netPrice;
    string cardName;
};


/* A Model class to mock the Output of the program */
class Output
{
public:
    int numberOfItemsGiven;
    int numberOfItemsChosen;
    int profit;
    double runningTime;
    Item items[50];
};


/* A Model class to mock the Test-Cases of the program */
class TestCase
{
public:
    int Weight;
    int n;
    Item items[50];
    Output output;
};


/* Global variable declarations */
int W;
int counter = 0;
Item market_prices[50];
TestCase testcases[50];

/* A funtion to tell is the given string is a number or not */
bool isNumeric(string str)
{
    for (int i = 0; i < str.length(); i++)
    {
        if(isalpha(str[i])) return false;
    }
    return true;
}

/* Utility funtions to trim the unwanted spaces from the test file */
std::string ltrim(const std::string &s)
{
    return std::regex_replace(s, std::regex("^\\s+"), std::string(""));
}

std::string rtrim(const std::string &s)
{
    return std::regex_replace(s, std::regex("\\s+$"), std::string(""));
}

std::string trim(const std::string &s)
{
    return ltrim(rtrim(s));
}

/* A funtion to read the input files */
void readFile(string filePath)
{
    fstream new_file;
    new_file.open(filePath, ios::in);
    bool isPriceListFile = false;

    if (new_file.is_open())
    {

        string line;
        getline(new_file, line);

        size_t found = line.find(' ');
        int numberOfItems;
        if (found != SIZE_MAX)
        {
            // cout << "\nPrice List: \n";
            while (1)
            {
                if (counter > 0)
                {
                    if (!getline(new_file, line))
                        break;
                    found = line.find(' ');
                }
                else
                {
                }
                trim(line);
                string word1 = line.substr(0, found);
                string word2 = line.substr(found + 1, line.length() - word1.length() - 1);

                if (isNumeric(word1) & isNumeric(word2))
                {
                    isPriceListFile = true;
                    numberOfItems = stoi(word1);
                    W = stoi(word2);
                    // cout << "numberOfItems : " << numberOfItems << '\n';
                    // cout << "W : " << W << '\n';

                    testcases[counter].Weight = W;
                    testcases[counter].n = numberOfItems;

                    Item items[50];
                    int i = 0;
                    while (numberOfItems)
                    {
                        try{
                            getline(new_file, line);
                            trim(line);
                        } catch(...) {
                            cout << "\n!!!!!Exception!!!!\n";
                        }
                        size_t found1 = line.find(' ');
                        // cout << found1 << "\n";
                        string subword1 = line.substr(0, found1);
                        string subword2 = line.substr(found1 + 1, line.length() - word1.length() - 1);
                        items[i].cardName = subword1;
                        items[i].price = stoi(subword2);
                        // cout << subword1 << " " << subword2 << " " << numberOfItems <<'\n';
                        i += 1;
                        numberOfItems -= 1;
                    }
                    std::copy(std::begin(items), std::end(items), std::begin(testcases[counter].items));
                }
                // cout << "Testcase : " << testcases[counter].Weight << "," << testcases[counter].n << testcases[counter].items << '\n'
                //      << '\n';
                counter++;
            }
            // cout << "\n===============================================\n";
        }
        else
        {
            // cout << "\nMarket Prices : \n";
            numberOfItems = stoi(line);
            // cout << "numberOfItems : " << numberOfItems << '\n';
            int i = 0;
            while (numberOfItems--)
            {
                getline(new_file, line);
                trim(line);
                found = line.find(' ');
                // for (int dummy = 0; dummy < line.length(); dummy++)
                //     cout << line[dummy];
                // cout << "\n";
                string word1 = line.substr(0, found);
                string word2 = line.substr(found + 1, line.length() - word1.length() - 1);
                // cout << line.length() << " , " << word1.length() << " , " << found << "\n";
                market_prices[i].cardName = word1;
                market_prices[i].price = stoi(word2);
                i += 1;
            }
            // cout << "\n===============================================\n";
        }

        new_file.close();
    }
}


/* This function checks if the given input item is in market or not */
bool isPresentInMarket(string cardName)
{
    bool isFound = false;
    for (int i = 0; i < 50; i++)
    {
        if (market_prices[i].price > 0 && market_prices[i].cardName.compare(cardName) == 0)
        {
            isFound = true;
            break;
        }
        else if (market_prices[i].price <= 0)
            break;
    }
    return isFound;
}

/* This function checks the validity of the input given to the program */
bool checkIsValidInput(TestCase testCase, int testCaseNumber)
{
    bool validInput = true;
    // cout << "\n================================================\n";
    if (testCase.Weight > 0)
    {
        // cout << "Test Case No : " << testCaseNumber + 1 << " ;Weight: " << testCase.Weight << " ;N: " << testCase.n << "\n";
        // cout << "Items:\n";
        for (int j = 0; j < 50; j++)
        {
            if (testCase.items[j].price > 0 && testCase.items[j].cardName != "")
            {
                // cout << testCase.items[j].cardName << ", Price :" << testCase.items[j].price << "\n";
                if (!isPresentInMarket(testCase.items[j].cardName))
                {
                    // cout << "\nThe given input is invalid\n";
                    validInput = false;
                    break;
                }
            }
        }
    }
    // cout << "\n";
    return validInput;
}

/* Actual implementation to compute the maximum profit  */
Output computeMaxProfit(TestCase &testCase, Item market_prices[50])
{

    clock_t start, end;
    start = clock();

    int Weight = testCase.Weight;
    int maxProfit = 0;
    Item itemsWithMaxProfit[50];
    int numberOfItemsWithMaxProfit = 0;
    int totalAmountSpent = 0;

    for (int j = 0; j < pow(2, testCase.n); j++)
    {
        // int nthBit = 0;
        int profit = 0, sumOfWeights = 0, amountSpent = 0;
        Item itemsIncluded[50];
        int numberOfItemsIncluded = 0, netProfit =0;
        //cout << "\n";
        int c = 0;
        for (int i = 0; i < testCase.n; i++)
        { // cout << ((j >> i) & 1);
            if (((j >> i) & 1) && true)
            { c++;
                if ((sumOfWeights + testCase.items[i].price) <= Weight)
                {
                    sumOfWeights += testCase.items[i].price;
                    for (int item = 0; item < 50; item++)
                    {
                        if (testCase.items[i].cardName.compare(market_prices[item].cardName) == 0)
                        {
                            profit += market_prices[item].price;
                            itemsIncluded[numberOfItemsIncluded++] = market_prices[item];
                            amountSpent += testCase.items[i].price;
                        }
                    }
                }
            }
            // nthBit += 1;
        }
        // netProfit = profit - amountSpent;
        profit = profit - amountSpent;
        if (maxProfit <= profit)
        {
            maxProfit = profit;
            // cout <<"\n" << profit << ", " << maxProfit << "\n";
            totalAmountSpent = amountSpent;
            std::copy(std::begin(itemsIncluded), std::end(itemsIncluded), std::begin(itemsWithMaxProfit));
            numberOfItemsWithMaxProfit = numberOfItemsIncluded;
        }
        // cout << "\t" << c << "\t" << profit << "\t" << maxProfit;
        // cout << "\n";
    }

    // cout << "\n\n maxProfit:" << maxProfit << "\t"
    //      << "totalAmountSpent : " << totalAmountSpent << "\n\n";

    testCase.output.numberOfItemsGiven = testCase.n;
    testCase.output.numberOfItemsChosen = numberOfItemsWithMaxProfit;
    testCase.output.profit = maxProfit;

    end = clock();
    double time_taken = double(end - start) / double(CLOCKS_PER_SEC);
    // cout << "\nTime : " << fixed
    //      << time_taken << setprecision(9) << '\n';
    testCase.output.runningTime = time_taken;
    std::copy(std::begin(itemsWithMaxProfit), std::end(itemsWithMaxProfit), std::begin(testCase.output.items));

    return testCase.output;
}

/* A funtion to print the output in the appropriate file */
void printOutput(Output output)
{
    // cout << output.numberOfItemsGiven << '\t'
    //        << output.profit << '\t'
    //        << output.numberOfItemsChosen << '\t'
    //        << output.runningTime << '\n';
    // for (int i = 0; i < output.numberOfItemsChosen; i++)
    // {
    //     cout << output.items[i].cardName << " " << output.items[i].price << " " << output.items[i].marketPrice << " " <<"\n";
    // }
    ofstream MyFile;
    MyFile.open("output.txt", ios::app);
    MyFile << output.numberOfItemsGiven << '\t'
           << output.profit << '\t'
           << output.numberOfItemsChosen << '\t'
           << output.runningTime << '\n';
    for (int i = 0; i < output.numberOfItemsChosen; i++)
    {
        MyFile << output.items[i].cardName << " " 
        // << output.items[i].price << " " 
        // << output.items[i].marketPrice << " " 
        <<"\n";
    }

    // Close the file
    MyFile.close();
}


/* Funtion to initialize the variables with proper values to avoid exceptions */
void initializeVariables()
{
    for (int i = 0; i < 50; i++)
    {
        market_prices[i].price = 0;
        market_prices[i].marketPrice = 0;
        market_prices[i].netPrice = 0;
        market_prices[i].cardName = "";
        testcases[i].Weight = 0;
        testcases[i].n = 0;
        for (int j = 0; j < 50; j++)
        {
            testcases[i].items[j].price = 0;
            testcases[i].items[j].marketPrice = 0;
            testcases[i].items[j].cardName = "";
        }
    }
}

int main(int argc, char* argv[])
{
    initializeVariables();
    // readFile("market_price.txt");
    readFile(argv[1]);
    // cout << '\n'
    //      << '\n';
    // readFile("price_list.txt");
    readFile(argv[2]);

    bool isvalidInput = true;

    ofstream MyFile;
    MyFile.open("output.txt");
    // cout << "\n\n\n";
    // cout << "OUTPUT : " << '\n';
    // MyFile << "Output : " << '\n';
    MyFile.close();
    for (int i = 0; i < counter; i++)
    {
        isvalidInput = true;
        isvalidInput = checkIsValidInput(testcases[i], i);
        if (isvalidInput)
        {
            testcases[i].output = computeMaxProfit(testcases[i], market_prices);
            printOutput(testcases[i].output);
        }
        else
        {
            cout << "\nInvalid Input: The given cardname in price-list file is not found in market-price file\n";
            MyFile.open("output.txt", ios::app);
            MyFile << "\nInvalid Input: The given cardname in price-list file is not found in market-price file\n";
            MyFile.close();
        }
    }
}

/**************

The best case and worst case complexity of the algorithm is 2^n.
Because, for given number of items 'n', we will generate the list of all possible combinations
before finding the maximum profit with lowest cost

T(n) = O(2^n)

***************/

/*
all :
	g++ knapSack.cpp -o knapSack 
	./knapSack ../p1_testcase/m_10.txt ../p1_testcase/p_10.txt
	cat output.txt

	g++ knapSack.cpp -o knapSack 
	./knapSack ../p1_testcase/m_20.txt ../p1_testcase/p_20.txt
	cat output.txt

	g++ knapSack.cpp -o knapSack 
	./knapSack ../p1_testcase/m_30.txt ../p1_testcase/p_30.txt
	cat output.txt

*/
