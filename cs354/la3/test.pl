X = 5.
Y = 6.
T = 7.
foo([]).
foo([_]).
foo([X | [Y|T]]) :- 
  X =< Y, 
  foo([Y | T]).