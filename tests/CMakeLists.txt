set(TEST_NAME CleanCodeNaming.tests)
set(CMAKE_CXX_STANDARD 17)
set(SOURCE_FILES
        main.cpp
		PrimesPrinterTest.cpp)
add_executable(${TEST_NAME} ${SOURCE_FILES})
target_link_libraries(${TEST_NAME} CleanCodeNaming.code ApprovalTests::ApprovalTests Catch2::Catch2)
add_test(NAME ${TEST_NAME} COMMAND ${TEST_NAME})
