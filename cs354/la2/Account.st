"gst sum.st"

"Account class"

Object subclass: Account [
	|number customer balance|

	accrue: rate [
		^self subclassResponsibility
	]
	
	"Return balance"	
	balance [
		^balance
	]

	"Deposit and withdraw from balance"
	deposit: amount [
		balance:=balance+amount.
	]
	
	withdraw: amount [
		balance:=balance-amount.
	]

	toString [
		^(number asString),':', (customer toString),':',(balance asString)
	]
]

