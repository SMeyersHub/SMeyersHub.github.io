Object subclass: Bank [
	|accounts|
	
	Bank class [
		new [
			^super new init
		]
	]

	init [
		accounts:= Set new
	]

	addAcc: account [
		accounts add: account.
	]

	accrue: rate [
		accounts do: [ :account |
			(account accrue: rate)
		].
	]
	
	toString [
	|return|
	return:=''.
		accounts do: [ :account |
			return:=return , (account toString), (Character nl asString).
		].
	^return
	]
]

|Bank| bank:= Bank new.
|Customer| c:= Customer new: 'Ann'.
|CheckingAccount| checkingAccount:= CheckingAccount new: '01001' customer: c balance:100.00.
|SavingsAccount| savingsAccount:=SavingsAccount new: '01002' customer: c balance:200.00.
(bank addAcc: checkingAccount).
(bank addAcc: savingsAccount).
(bank accrue: 0.02).
(bank toString) printNl.
