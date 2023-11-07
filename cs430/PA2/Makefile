# Files and directories
INCLUDES=-I ./include
BIN=./bin
LIB=./lib
SRC=./src
TEST=./test
HEADERS=./include/*.h
# Compiler flags
CC=gcc
CFLAGS=-Wall -Wextra
OPENMPFLAGS=-fopenmp

GTEST_DIR := ./lib/gtest/googletest
GMOCK_DIR := ./lib/gtest/googlemock
GBUILD :=  ./lib/bin/gtest

GTEST_SRCS_ = $(GTEST_DIR)/src/*.cc $(GTEST_DIR)/src/*.h $(GTEST_HEADERS)
GMOCK_SRCS_ = $(GMOCK_DIR)/src/*.cc $(GMOCK_HEADERS)


# Flags passed to the C++ compiler.
CXXFLAGS += -g -Wall -Wextra -pthread -std=c++11
CPPFLAGS += -isystem $(GTEST_DIR)/include -isystem $(GMOCK_DIR)/include

# Google Test libraries
GTEST_LIBS = $(GBUILD)/libgtest.a  $(GBUILD)/libgtest_main.a

# All Google Test headers.  Usually you shouldn't change this
# definition.
GTEST_HEADERS = $(GTEST_DIR)/include/gtest/*.h \
				$(GTEST_DIR)/include/gtest/internal/*.h

# For simplicity and to avoid depending on implementation details of
# Google Mock and Google Test, the dependencies specified below are
# conservative and not optimized.  This is fine as Google Mock and
# Google Test compile fast and for ordinary users their source rarely
# changes.
$(GBUILD)/gtest-all.o : $(GTEST_SRCS_)
	mkdir -p $(@D)
	$(CXX) $(CPPFLAGS) -I$(GTEST_DIR)  $(CXXFLAGS) \
			-c $(GTEST_DIR)/src/gtest-all.cc -o $@

$(GBUILD)/gtest_main.o : $(GTEST_SRCS_)
	mkdir -p $(@D)
	$(CXX) $(CPPFLAGS) -I$(GTEST_DIR) $(CXXFLAGS) \
			-c $(GTEST_DIR)/src/gtest_main.cc -o $@

$(GBUILD)/libgtest.a : $(GBUILD)/gtest-all.o
	mkdir -p $(@D)
	$(AR) $(ARFLAGS) $@ $^

$(GBUILD)/libgtest_main.a : $(GBUILD)/gtest-all.o $(GBUILD)/gtest_main.o
	mkdir -p $(@D) 
	$(AR) $(ARFLAGS) $@ $^

# Make targets
makedirs:
	mkdir -p $(BIN)/test
	mkdir -p $(TEST)

libmmio:
	$(CC) $(CFLAGS)  -c $(INCLUDES) -o $(LIB)/mmio.o -fPIC $(SRC)/mmio.c

libmatrixmatrix: libmmio
	$(CC) $(CFLAGS) $(OPENMPFLAGS) $(INCLUDES) -o $(LIB)/libmatrixmatrix.o -c $(SRC)/matrixMatrix.c

libmatrixvector: libmmio
	$(CC) $(CFLAGS) $(OPENMPFLAGS) $(INCLUDES) -o $(LIB)/libmatrixvector.o -c $(SRC)/matrixVector.c

libfibonacci:
	$(CC) $(CFLAGS) $(OPENMPFLAGS) $(INCLUDES) -o $(LIB)/libfibonacci.o -c $(SRC)/fibonacci.c

libleibniz:
	$(CC) $(CFLAGS) $(OPENMPFLAGS) $(INCLUDES) -o $(LIB)/libleibniz.o -c $(SRC)/leibniz.c

libmontecarlo:
	$(CC) $(CFLAGS) $(OPENMPFLAGS) $(INCLUDES) -o $(LIB)/libmontecarlo.o -c $(SRC)/montecarlo.c

matrixvector: makedirs libmatrixvector
	$(CC) $(CFLAGS) $(OPENMPFLAGS) $(INCLUDES) -o $(BIN)/matrixvector $(LIB)/mmio.o $(LIB)/libmatrixvector.o $(SRC)/matrixVectormain.c

matrixmatrix: makedirs libmatrixmatrix
	$(CC) $(CFLAGS) $(OPENMPFLAGS) $(INCLUDES) -o $(BIN)/matrixmatrix $(LIB)/mmio.o $(LIB)/libmatrixmatrix.o $(SRC)/matrixMatrixmain.c

fibonacci: makedirs libfibonacci
	$(CC) $(CFLAGS) $(OPENMPFLAGS) $(INCLUDES) -o $(BIN)/fibonacci $(LIB)/libfibonacci.o $(SRC)/fibonaccimain.c

leibniz: makedirs libleibniz
	$(CC) $(CFLAGS) $(OPENMPFLAGS) $(INCLUDES) -o $(BIN)/leibniz $(LIB)/libleibniz.o $(SRC)/leibnizmain.c

montecarlo: makedirs libmontecarlo
	$(CC) $(CFLAGS) $(OPENMPFLAGS) $(INCLUDES) -o $(BIN)/montecarlo $(LIB)/libmontecarlo.o $(SRC)/montecarlomain.c

all: matrixmatrix matrixvector fibonacci leibniz montecarlo

.PHONY: libmmio matrix_vector matrix_matrix fibonacci leibniz montecarlo all clean

clean:
	$(RM) -r *.dSYM $(BIN)/* $(LIB)/*.o $(LIB)/bin

libgtest: makedirs $(GTEST_LIBS)

$(BIN)/test/testmatrixmatrix.o : libgtest $(TEST)/testmatrixmatrix.c ${HEADERS} $(GTEST_HEADERS)
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) $(OPENMPFLAGS) $(INCLUDES) -c $(TEST)/testmatrixmatrix.c -o $@

testmatrixmatrix : $(BIN)/test/testmatrixmatrix.o $(LIB)/libmatrixmatrix.o $(LIB)/mmio.o $(GBUILD)/libgtest_main.a
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) $(OPENMPFLAGS) $(INCLUDES) -lpthread $^ -o ./bin/$@
	./bin/testmatrixmatrix

$(BIN)/test/testmatrixvector.o : libgtest $(TEST)/testmatrixvector.c ${HEADERS} $(GTEST_HEADERS)
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) $(OPENMPFLAGS) $(INCLUDES) -c $(TEST)/testmatrixvector.c -o $@

testmatrixvector : $(BIN)/test/testmatrixvector.o $(LIB)/libmatrixvector.o $(LIB)/mmio.o $(GBUILD)/libgtest_main.a
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) $(OPENMPFLAGS) $(INCLUDES) -lpthread $^ -o ./bin/$@
	./bin/testmatrixvector

$(BIN)/test/testleibniz.o : libgtest $(TEST)/testleibniz.c ${HEADERS} $(GTEST_HEADERS)
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) $(OPENMPFLAGS) $(INCLUDES) -c $(TEST)/testleibniz.c -o $@

testleibniz : $(BIN)/test/testleibniz.o $(LIB)/libleibniz.o $(LIB)/mmio.o $(GBUILD)/libgtest_main.a
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) $(OPENMPFLAGS) $(INCLUDES) -lpthread $^ -o ./bin/$@
	./bin/testleibniz

$(BIN)/test/testfibonacci.o : libgtest $(TEST)/testfibonacci.c ${HEADERS} $(GTEST_HEADERS)
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) $(OPENMPFLAGS) $(INCLUDES) -c $(TEST)/testfibonacci.c -o $@

testfibonacci : $(BIN)/test/testfibonacci.o $(LIB)/libfibonacci.o $(LIB)/mmio.o $(GBUILD)/libgtest_main.a
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) $(OPENMPFLAGS) $(INCLUDES) -lpthread $^ -o ./bin/$@
	./bin/testfibonacci

$(BIN)/test/testmontecarlo.o : libgtest $(TEST)/testmontecarlo.c ${HEADERS} $(GTEST_HEADERS)
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) $(OPENMPFLAGS) $(INCLUDES) -c $(TEST)/testmontecarlo.c -o $@

testmontecarlo : $(BIN)/test/testmontecarlo.o $(LIB)/libmontecarlo.o $(LIB)/mmio.o $(GBUILD)/libgtest_main.a
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) $(OPENMPFLAGS) $(INCLUDES) -lpthread $^ -o ./bin/$@
	./bin/testmontecarlo

testall: testmatrixmatrix testmatrixvector testleibniz testfibonacci testmontecarlo