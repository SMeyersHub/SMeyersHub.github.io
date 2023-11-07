Object subclass: Customer [
	"instance variables"
	|name|

	"Constructor for a customer object"
	Customer class [
	    new: n [
	        ^super new init: n
	    ]
	]
	
	"Initializer for customer name"
	init: n [
	    name:=n
	]
	
	toString[ 
		^name
	]
]
