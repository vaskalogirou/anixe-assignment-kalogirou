# Getting started

Run the tests:

    mvnw test

Run the application:

	mvnw spring-boot:run

After the application has started, you can see a friendly greeting by hitting this on your browser:

	http://localhost:8080/hello
	
The app uses an H2 in memory database. Some initial data are loaded each time the app starts (via liquibase xml scripts).

You can see these hotels and bookings by hitting the *hotels base url* and the *bookings base url*:

	http://localhost:8080/api/hotels
	http://localhost:8080/api/bookings

Use your favorite http request tool (e.g. Postman) to post this raw json hotel:

```json
{
  "name": "A new Hotel in town",
  "address": "Syntagma square",
  "starRating": 8.1
}
```

to the *hotels base url* and then refresh the hotels page from the previous step.


# Assignment specific tasks

Hit this url:

	http://localhost:8080/api/hotels-for-surname/Tolstoy
	
in order to see the list of hotels for which mr Tolstoy (case sensitive) has made bookings.


Hit this url:
	
	localhost:8080/api/bookings-by-hotel-id/1

in order to see all the bookings for the hotel *Four Seasons Astir Palace*, whose id is 1.

Post this json:

```json
{
  "customerName": "Paris",
  "customerSurname": "Hilton",
  "numberOfPax": 2,
  "hotel": {
    "name": "Hilton",
    "address": "Leof. Vasilissis Sofias 46",
    "starRating": 9.2
  },
  "priceAmount": 1100,
  "currency": "USD"
}
```

to the *bookings base url*. If you query the hotels again, you will notice that the Hilton hotel is now there.



Moreover, you can hit this:

	localhost:8080/api/hotels/1/price-amount-sum-in-euro

in order to get the sum of the price amounts of all the bookings related to the hotel whose id is 1.

Keep in mind that this result adds prices converted to euro. 


Lastly, you can navigate to this folder on your computer:

	\anixe-assignment-kalogirou\logs\userRequestLogs
	
so as to see the logs for the requests you just made (just your requests, not the whole bunch of spring logs!)

# Ιmprovements

This assignment is implemented with a simplistic mindset, meaning that the simplest approach has been chosen for nearly every decision.

Some of the numerous candidate improvements are:

- Auditing:   
	- Our entities should extend some base audit class that has *created_at* and *modified_at* dates.


- Exception handling:    
	- Our endpoints could throw meaningful exceptions instead of returning 40x (for example *HotelAlreadyExistsException* or *HotelNotFoundException*)


- Profiles:    
	- There should be separate *.yml* and *logback.xml* files for *prod* and *dev* environments


- Validations    
	- Let's validate every piece of data that comes into our app, even the user interface dropdown values. There will always be some smartass that will try to "hack" our database and rate a hotel with 11 points (on a scale from one to 10). 

# Discussion

Many marginal decisions had to be made during this project. Some of those can be subject to fruitful discussion. For example:

- Do we really need a DTO layer?


- Is the bidirectional relationship between hotels and bookings the *correct* one? Why did we choose the unidirectional approach?


- Which is the best way to inject dependencies? Field injection or constructor injection? (spoiler alert: it is the constructor injection)


- Should every field of a booking be updatable? Can there be a real life scenario where we will need to change the hotel of a booking leaving all other data intact, and if so, shouldn't we just delete the booking and create a new one?


- Why do we need to sent all the entity data to our *update* endpoint, if we just want to edit one field?

# Foresight

As all developers know, a product owner can ask for all kinds of stuff. However, some of them are not so unpredictable. As the project grows, we can expect them to say:

- *"I tried to delete a hotel, but I couldn't because there were bookings attached to it! I want you to cascade the request so that everything gets deleted with one click!"*


- *"I changed the currency of a booking, but the price didn't change. I want the price to automatically get converted according to the new currency."*


- *"Our servers are getting slower and slower, since we now have millions of bookings. We need a performance boost. Start using caches, both first and second level. Oh, and you should check our database queries in order to decide if some query-critical columns need indexing."*


- *"Remember the last time I asked you to make it easy to delete stuff? Well I changed my mind. From now on nothing gets deleted. Just soft-delete them and I will decide what to do with it later."*



That's it. Any feedback is welcome :)


