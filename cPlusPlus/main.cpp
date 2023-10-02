#include "crow.h"
#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>
#include <fcntl.h>
#include <termios.h>
#include <fstream>
#include <csignal> // for signal handling
#include <cstdlib> // for exit
#include <iostream>
#include <chrono>
#include <ctime>
#include <string>
#include <thread>

using namespace std;
using namespace crow;

using namespace std;

void handleCtrlC(int signum)
{
    printf("Ctrl+C detected. Exiting gracefully.");
}

const std::string currentDateTime()
{
    time_t now = time(0);
    struct tm tstruct;
    char buf[80];
    tstruct = *localtime(&now);
    strftime(buf, sizeof(buf), "%Y-%m-%d.%X", &tstruct);
    return buf;
}

void periodicTask()
{
    while (true)
    {
        // Do something here
        // std::ifstream inputFile("example.txt");
        // std::string line;
        // if (inputFile.is_open())
        // {
        //     char mychar;
        //     while (inputFile)
        //     {
        //         std::getline(inputFile, line);
        //         std::cout << line;
        //     }
        // }
        // inputFile.close();
        // Sleep for 5 seconds
        // std::this_thread::sleep_for(std::chrono::seconds(15));
    }
}

void log_user_action()
{

    signal(SIGINT, handleCtrlC);

    struct termios oldSettings, newSettings;

    tcgetattr(fileno(stdin), &oldSettings);
    newSettings = oldSettings;
    newSettings.c_lflag &= (~ICANON & ~ECHO);
    tcsetattr(fileno(stdin), TCSANOW, &newSettings);

    std::ofstream outputFile;
    string line;
    outputFile.open("example.txt");

    int numofTimesTimeExceeded = 0;

    while (1)
    {
        std::thread taskThread(periodicTask);

        fd_set set;
        struct timeval tv;

        tv.tv_sec = 2;
        tv.tv_usec = 0;

        FD_ZERO(&set);
        FD_SET(fileno(stdin), &set);

        int res = select(fileno(stdin) + 1, &set, NULL, NULL, &tv);

        if (res > 0)
        {
            char c;
            read(fileno(stdin), &c, 1);
            // outputFile << c; // commenting for now, not tracking the user actions.
        }
        else if (res < 0)
        {
            perror("select error");
            break;
        }
        else
        {
            numofTimesTimeExceeded += 1;
            outputFile << numofTimesTimeExceeded << endl;
        }

        taskThread.detach();
    }

    printf("closing file\n");
    outputFile.close();

    tcsetattr(fileno(stdin), TCSANOW, &oldSettings);
}

int main(int argc, char *argv[])
{
    //log_user_action();

    crow::SimpleApp app;

    CROW_ROUTE(app, "/")
    ([]()
     { return "Hello world"; });

    CROW_ROUTE(app, "/test_post").methods(HTTPMethod::POST)([](const request &req, response &res)
                                                            {
        string method = method_name(req.method);
        res.set_header("Content-Type","text/plain");
        res.write(method+" test_post");
        res.end(); });

    app.bindaddr("127.0.0.1").port(18081).run();

    return 0;
}