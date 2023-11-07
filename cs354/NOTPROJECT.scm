
(define (super-duper source count)
	(define (multiplier source remaining)
		(if(zero? remaining)
			(super-duper (cdr source) count)
			(cons (super-duper (car source) count) (multiplier source(- remaining 1)))))
	(if (pair? source) ;;check to see if source is a pair or atom, if atom, simply return it
		(cons (super-duper (car source) count) (multiplier source (- count 1))) ;; con a copy of source's atom
		source
	)
)

(display "NUMBERS: ")
(display "\n")
(display (super-duper "a" 19))
(display "\n")
(display "LISTS: ")
(display "\n")
(display (super-duper '(x y) 4))
(display "\n")
(display (super-duper '( (a) b) 3))
(display "\n")
