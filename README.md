#WorldPay


##Rest Methods
POST to create new offer: http://localhost:8001/offers
PUT to update an existing offer: http://localhost:8001/offers
PATCH to update only the status of an existing offer: http://localhost:8001/offers/{offer id}/status?newStatus=EXPIRED

####Background
Per Wikipedia, "an offer is a proposal to sell a specific product or
service under specific conditions". As a merchant I offer goods for
sale. I want to create an offer so that I can share it with my
customers.

All my offers have shopper friendly descriptions. I price all my offers
up front in a defined currency.

An offer is time-bounded, with the length of time an offer is valid for
defined as part of the offer, and should expire automatically. Offers may
also be explicitly cancelled before they expire.

####Assignment
You are required to create a simple RESTful software service that will
allow a merchant to create a new simple offer. Offers, once created, may be
queried. After the period of time defined on the offer it should expire and
further requests to query the offer should reflect that somehow. Before an offer
has expired users may cancel it.

####Guidelines
The solution should be written in Java or Scala
The merchant should be able to interact with the service over HTTP
No restrictions on external libraries
Submit as a git repository (link to GitHub, BitBucket, etc)
We are looking for a simple solution representative of an enterprise deliverable
Use TDD
Please pay attention to OO design; clean code, adherence to SOLID principles
As a simplification offers may be persisted to a file, embedded database or held in
memory
You can ignore authentication and authorization concerns
Feel free to make any assumptions and document in a README markdown file, or
otherwise, with the submission