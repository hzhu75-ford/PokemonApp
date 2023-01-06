# Pokemon
 
Android Pokemon Take-Home Project
Write a simple Android app using whichever libraries you wish to include to speed up your development. This project will
be presented to team members for discussion where you will get a chance to show off your project and explain your
design process.
● Use the API data from: https://pokeapi.co/
● Use this as an opportunity to showcase your abilities: easy to follow, clean code is preferred
● Steps:
○ Set up your environment
○ Write your code
○ When ready, email us a zip of the project or make a pull request on Github and share the link
Requirements:
Use the pokeapi.co API to get data from the network and display it in a list in the main app screen. Selecting an item in
the list should bring up a detail page. Refer to the requirements below to implement the UX.
For requirements questions that pop up, please use your best judgment as there will not be an opportunity to ask
questions regarding the take-home. Please list and explain important assumptions you felt needed to be made.
- Do not use any of the supplied wrappers (libraries to load the poke data), we want to see how you would code
this.
- The UI must be built using Jetpack Compose
- The project must contain at least one unit test.
App Main Page:
1. Create a list of Pokemon gathered from the API call to http://pokeapi.co/api/v2/pokemon/ from the results object
a. Display: The relevant Pokemon icon, its name, and list its type(s).
2. NOTE: Each list item will require you to make an additional call to retrieve the Image link, and types (from the links
provided in the above call)
3. Supply a 'Load More' button if there are more items in the list for the user to retrieve (from the next link in the
above API call)
4. Handle a click on a row that sends a user to a detail screen
App Details Page:
1. Create a title row with the Pokemon's name (from the name object in the
http://pokeapi.co/api/v2/pokemon/[number]/ call)
2. Display: The relevant Pokemon icon (first available link from 'sprites' object), its speed (from 'stats[].stat.speed'),
attack (from 'stats[].stat.attack')) and defense (from 'from 'stats[].stat.defense') values
3. Show a list of the Pokemon's abilities (from the 'abilities[].ability.name' object)
