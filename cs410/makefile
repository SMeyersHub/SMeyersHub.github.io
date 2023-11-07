JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Assignment.java \
	Category.java \
	Class.java \
	MySqlDatabase.java \
	JavaShell.java \
  Student.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class