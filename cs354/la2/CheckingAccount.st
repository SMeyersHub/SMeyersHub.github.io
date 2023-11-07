Account subclass: CheckingAccount[
	|interest|
	
	"Constructor for a Checking Account"
	CheckingAccount class [
		new: number customer: aCustomer balance: aBalance [
			^self new init: number customer: aCustomer balance: aBalance.
		]
	]	

	"Initializer for the checking account"
	init: aNumber customer: aCustomer balance: aBalance [
		interest:=0.
		number := aNumber. 
		customer := aCustomer.
		balance := aBalance
	]
	"Accrue interest"
	accrue: rate [
	]
]
