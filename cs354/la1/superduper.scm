;;INDOC README
;;The purpose for this program is to take a list or atom and make a duplicate amount of that atom.
;;
;;Author: Steven Meyers
;;Date: 9/23/20
;;
;;RUNNING:
;;To run this program, you must have the guile interpreter or some other interpreter to handle scheme.
;;From the bash line, run:
;;```
;;app guile superduper.scm
;;```
;;If using the guile interpreter, type:
;;```
;;(load 'superduper.scm')
;;(super-duper [LIST/ATOM] [COUNT])
;;```


;;Dupes the atom from a pair and cons them together to form a list with count number dupes.
(define (multiplier source count remaining)
		(if(zero? remaining)
			(super-duper (cdr source) count) ;;moves to the next pair in the list
			(cons (super-duper (car source) count) (multiplier source count (- remaining 1))))) ;;loops and cons the atom
;;Filters out pairs and nonpairs 
(define (super-duper source count)
	(if(pair? source)
		(multiplier source count count) ;;looks down 
		source
	)
)

(display "NOT PAIRS: \n")
(display "(super-duper 123 1)) \n")
(display (super-duper 123 1))
(display "\n")
(display "(super-duper 123 2)) \n")
(display (super-duper 123 2))
(display "\n")
(display "(super-duper 1 123)) \n")
(display (super-duper 1 123))
(display "\n")
(display "(super-duper 10000000000000000000000000 123)) \n")
(display (super-duper 10000000000000000000000000 123))
(display "\n")
(display "(super-duper a 2)) \n")
(display (super-duper "a" 2))
(display "\n")
(display "(super-duper a123ajdue 3)) \n")
(display (super-duper "a123ajdue" 3))
(display "\n")
(display "\n")
(display "EMPTY LISTS: \n")
(display "(super-duper '() 1)) \n")
(display (super-duper '() 1))
(display "\n")
(display "(super-duper '() 2)) \n")
(display (super-duper '() 2))
(display "\n")
(display "\n")
(display "LISTS: \n")
(display "(super-duper '(()) 2)) \n")
(display (super-duper '(()) 2))
(display "\n")
(display "(super-duper '(x) 1)) \n")
(display (super-duper '(x) 1))
(display "\n")
(display "(super-duper '(x) 2)) \n")
(display (super-duper '(x) 2))
(display "\n")
(display "(super-duper '(x y) 1)) \n")
(display (super-duper '(x y) 1))
(display "\n")
(display "(super-duper '(x y) 1)) \n")
(display (super-duper '(x y) 2))
(display "\n")
(display "(super-duper '((a b) y) 3)) \n")
(display (super-duper '((a b) y) 3))
(display "\n")
(display "(super-duper '((a b (c)) y) 3)) \n")
(display (super-duper '((a b (c)) y) 3))
(display "\n")
