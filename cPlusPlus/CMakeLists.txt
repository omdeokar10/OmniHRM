cmake_minimum_required(VERSION 3.7)

project(hello_crow)

set(INCLUDE_PATHS boost_1_83_0 crow/include)

add_executable(hello_crow main.cpp)

target_include_directories(hello_crow PUBLIC ${INCLUDE_PATHS})
target_link_libraries(hello_crow)
