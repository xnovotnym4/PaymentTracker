Few assumptions have been made considering console input:
- payment needs to have strict format - 3 capital letters followed by space and a Integer number
- program can be set to check if loaded currency is valid
- only valid currencies from given list of currencies and their rate to USD are converted to USD
- after typing "quit" as a input, program will terminate

There are 2 global variables for easy customization of the program:
- printTimer - sets time interval between each display of net amounts of each currency, in milliseconds
- allowCurrencyCheck - in resource file currenciesRate.txt, mostly used currencies in the world are specified with their conversion rate to USD; this boolean global variable will allow (true) or deny (false) check if the currency from input is valid currency from the list
