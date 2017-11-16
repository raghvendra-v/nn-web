Feature: Catalogue Rest API 
Background: 
	Given the api context URI is '/nn-web' 
	
Scenario: 
	When the user queries the URI 'api' 
	Then the user should be given list of all the repositories under the '_links' 
	
Scenario Outline: Persist Users 
	And the following users are to be created 
	When User requests "POST" on "users" 
	Then User should get a response with status code "" 
	Examples: 
		|	username			|	email							|
		|	admiring_agnesi		|	"admiring.agnesi@italy.com"		|
		|	optimistic_bhaskara	|	"optimistic.bhaskara@india.com"	|
		|	stupefied_euclid	|	"stupefied.euclid@geometry.com"	|
		|	cocky_goldwasser	|	"cocky.goldwasser@crypto.com"	|
		|	romantic_dubinsky	|	"romantic.dubinsky@pda.com"		|
		
			