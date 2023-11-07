OBJS = smash.o commands.o history.o
EXE = smash

CFLAGS = -Wall -std=c99

all: rules.d $(EXE)

rules.d: $(wildcard *.c) $(wildcard *.h)
	gcc -MM $(wildcard *.c) >rules.d
-include rules.d 

$(EXE): $(OBJS)
	$(CC) $(CFLAGS) $^ -o $@

clean: 
	rm -f $(OBJS) $(EXE) *.d *~ 
