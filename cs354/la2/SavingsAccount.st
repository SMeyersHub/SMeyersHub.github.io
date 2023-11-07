Account subclass: SavingsAccount[
	|interest|
	
	"Constructor for a Saving Account"
	SavingsAccount class [
		new: number customer: aCustomer balance: aBalance [
			^self new init: number customer: aCustomer balance: aBalance.
		]
	]	

	"Initializer for the savings account"
	init: aNumber customer: aCustomer balance: aBalance [
		interest:=0.
		number := aNumber. 
		customer := aCustomer.
		balance := aBalance
	]
	"Accrue interest"
	accrue: rate [
		interest:=interest + (balance * rate).
		balance:=balance + (balance * rate).
	]
]
