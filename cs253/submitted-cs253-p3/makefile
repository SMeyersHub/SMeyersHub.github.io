OBJS = sed253.o substitute.o delete.o copy.o

EXE = sed253

CFLAGS=-Wall -std=c99

all: rules.d $(EXE)

rules.d: $(wildcard *.c) $(wildcard *.h)
	gcc -MM $(wildcard *.c) >rules.d
-include rules.d 

$(EXE): $(OBJS)
	$(CC) $(CFLAGS) $^ -o $@

clean: 
	rm -f $(OBJS) $(EXE) *.d *~ 
