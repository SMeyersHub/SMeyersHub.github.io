#!/bin/gprolog --consult-file

:- include('data.pl').

lte(time(_,_,am), time(_,_,pm)).
lte(time(BeginH, _, AP), time(EndH, _, AP)) :- 
	BeginH < EndH.

lte(time(H, BeginM, AP), time(H, EndM, AP)) :- 
	BeginM < EndM.

meetone(Person, slot(BeginTime,EndTime)) :- 
	free(Person,slot(PersonBegin,PersonEnd)),
	lte(BeginTime, PersonEnd),
	lte(PersonBegin, EndTime).

main :- findall(Person,
		meetone(Person,slot(time(8,30,am),time(8,45,am))),
		People),
	write(People), nl, halt.

:- initialization(main).
