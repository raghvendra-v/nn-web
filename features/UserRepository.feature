Feature: Catalogue Rest API 
Background: 
		Given the api context URI is '/nn-web'
		
Scenario:
		When the user queries the URI 'api' 
		Then the user should be given list of all the repositories under the '_links'

Scenario Outline: Persist Users 
			And the following users are to be created
			When User requests "POST" on "user" with request Fields=<Fields>&serviceType=<serviceType>&networkInstanceId=<networkInstanceId>&networkInstanceType=<networkInstanceType>&specificationList.Category[]=<specificationList.Category>
			Then User should get a response with header containing "Srims-request-id" and a JSON body with non-zero length of "specificationList"
Examples: 
		|Fields					|serviceType	|	networkInstanceId				|	networkInstanceType			|specificationList.Category	|
		|	specificationList	|	"VSOD"		|	�PID of	Central	Service�		|	�Central Network Service�	|	"Routing"				|
		|	specificationList	|	"VSOD"		|	�PID of	VSOD	Service�		|	�Virtual Service on Demand�	|	"Routing"				|
		|	specificationList	|	"VSOD"		|	�ENCS PID�						|	�Device�					|	"Device"				|
		|	specificationList	|	"VSOD"		|	�Port Id�						|	�LAN Port�					|	"Device"				|
		|	specificationList	|	"VSOD"		|	�ISRv PID�						|	�Virtual Routing�			|	"Routing"				|
		|	specificationList	|	"VSOD"		|	�VPN id�						|	�VPN Connection�			|	"Routing"				|
		|	specificationList	|	"VSOD"		|	�Subnet Id�						|	�Subnet�					|	"Routing"				|

			