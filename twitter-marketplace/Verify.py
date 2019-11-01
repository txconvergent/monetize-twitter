import re

def checkEmail(emailString):
	
	ex = re.compile("(^[A-Za-z0-9_.+-]+@[A-Za-z0-9-]+\.[A-Za-z0-9-.]+$)")
	if(ex.match(emailString) == None):
		return False
	return True

def checkUsername(userString):

	if(("TWITTER" in userString.upper()) or ("ADMIN" in userString.upper())):
		return False
	ex = re.compile("(^[A-Za-z0-9_]{1,15}$)")
	if(ex.match(userString) == None):
		return False
	return True

def checkPassword(passwordString):

	ex = re.compile("(^[A-Za-z0-9!@#\$%^&\+\.\*-]{6,}$)")
	if(ex.match(passwordString) == None):
		return False
	return True

def checkAddress(addressString):
	ex = re.compile("(^[A-Za-z0-9'\.\-\s\,]+$)")
	if(ex.match(addressString) == None):
		return False
	return True

def checkPhone(phoneString):
	ex = re.compile(\
	"^\+?(\d{2}\s?)?(\(?1?[2-9][0-8]\d\)?([\s\.-])?)?[2-9](\d{2})([\s\.-])?(\d{4})$")
	if(ex.match(phoneString) == None):
		return False
	return True

def checkName(nameString):
	ex = re.compile("[A-Za-z]+")
	if(ex.match(nameString) == None):
		return False
	return True

def checkCreditCard(card):
	sum = 0
	numDigits = len(card)
	parity = numDigits % 2
	for i in range(numDigits):
		digit = int(card[i])
		if(i%2==parity):
			digit *= 2
			if(digit>9):
				digit-=9
		sum+=digit
	return (sum%10==0)

def main():
	
	user = ""
	while(user!="0"):
		user = input("Enter phone: ")
		print(checkPhone(user))
	
	#print(checkCreditCard("4111111111111111"))
main()