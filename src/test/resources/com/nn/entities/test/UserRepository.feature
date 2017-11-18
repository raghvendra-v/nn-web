Feature: Catalogue Rest API 
Background: 
	Given the api context URI is '/nn-web' 
	And the rest endpoint is that '/api' 
	
Scenario: The REST api is operational 
	Then the user should get a response code '200' and number of '_links' should be '>2' upon querying the rest api root 
	
Scenario Outline: Creation of user accounts 
	When RequestMethod '<Method>' executed on 'users' with 'id' as '<id>', 'username' as '<username>' and 'email' as '<email>' 
	Then the request should return http code '<code>' 
	Examples: 
		|	Method	|	id	|	username			|	email							|	code	|
		|	POST	|		|	admiring_agnesi		|	admiring.agnesi@italy.com		|	201		|
		|	POST	|		|	admiring_agnesi		|	admiring.agnesi@italy.com		|	500		|	
		|	PATCH	|	1	|	admiring_agnesi		|	jealous.agnesi@italy.com		|	200		|
		|	DELETE	|	1	|	admiring_agnesi		|	jealous.agnesi@italy.com		|	204		|
		|	POST	|		|	optimistic_bhaskara	|	optimistic.bhaskara@india.com	|	201		|
		|	PATCH	|	3	|	optimistic_bhaskara	|	optimistic.bhaskara@bharat.com	|	200		|
		|	POST	|		|	stupefied_euclid	|	stupefied.euclid@geometry.com	|	201		|
		|	PATCH	|	4	|	stupefied_euclid	|	optimistic.bhaskara@india.com	|	200		|
		|	POST	|		|	cocky_goldwasser	|	cocky.goldwasser@crypto.com		|	201		|
		|	POST	|		|	romantic_dubinsky	|	romantic.dubinsky@pda.com		|	201		|
		