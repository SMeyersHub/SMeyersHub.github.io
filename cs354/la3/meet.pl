#!/bin/gprolog --consult-file

:- include('data.pl').
:- include('uniq.pl').

lte(time(H, M, AP), time(H, M, AP)).
lte(time(_,_,am), time(_,_,pm)).

lte(time(BeginH, _, AP), time(EndH, _, AP)) :- 
	BeginH < EndH.

lte(time(H, BeginM, AP), time(H, EndM, AP)) :- 
	BeginM < EndM.

slotCheck(BT1,BT2, ET1, ET2) :-
	BT2 \== ET1,
	BT1 \== ET2,
	lte(BT1, ET2),
	lte(BT2, ET1).

slotCompare(Slot, slot(BTime, ETime), slot(BTime,ETime)) :-
	Slot = slot(BTime, ETime).

slotCompare(Slot, slot(BT1, ET1), slot(BT2, ET2)) :-
	slotCheck(BT1,BT2,ET1,ET2),
	lte(BT2, BT1),
	lte(ET2, ET1),
	Slot = slot(BT1, ET2).

slotCompare(Slot, slot(BT1, ET1), slot(BT2, ET2)) :-
	slotCheck(BT1,BT2,ET1,ET2),
	lte(BT2, BT1),
	lte(ET1, ET2),
	Slot = slot(BT1, ET1).

slotCompare(Slot, slot(BT1, ET1), slot(BT2, ET2)) :-
	slotCheck(BT1,BT2,ET1,ET2),
	lte(BT1, BT2),
	lte(ET1, ET2),
	Slot = slot(BT2, ET1).

slotCompare(Slot, slot(BT1, ET1), slot(BT2, ET2)) :-
	slotCheck(BT1,BT2,ET1,ET2),
	lte(BT1, BT2),
	lte(ET2, ET1),
	Slot = slot(BT2, ET2).

listRecur(PassedIn, Return, []) :-
	Return = PassedIn.
listRecur(PassedIn, Return, [H | T]) :-
	free(H, PersonSlot),
	slotCompare(NewSet, PassedIn, PersonSlot),
	listRecur(NewSet, Return, T).

meet(Slot) :-
	people([H | T]),
	free(H, PersonSlot),
	listRecur(PersonSlot, Slot, T).

people([ann,bob,carla]).

main :- findall(Slot, meet(Slot), Slots),
        uniq(Slots, Uniq),
        write(Uniq), nl, halt.

:- initialization(main).
