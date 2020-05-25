# Jokes
Fetch jokes and display using MVVM, Dagger,RxJava. Jetpack, etc.

# Task

Fetch Jokes list from [icndb](http://www.icndb.com/) and list them on the screen. Also persist the data across the app.

# Project Structure

Project uses MVVM approach. Dagger2 is used for dependency injection. Retrofit is used for networking along with
RxJava for reactive programming and other libraries. It has two modules **app** and **data**.

###### :app
Handles all the presentaion logic. It receieves data from **data** and draws on the views. It contains ViewModels and 
android components

###### :data
**data** fetches the data from the server using retrofit and passes it to presentaion. It also moldes the data so that views 
can use it. This moduls also consists local database (Room) and SharedPreferences that are used to persist data locally.

# Requirements
- Android SDK 21 or above
- Android Studio 3.6+
- Kotlin 

# Working
This app has four screens. 

- Jokes List: 
  - Displays jokes in the form of list that can be shared or liked
  - **data** persists jokes in Room
  - Fresh data can be fetched by shaking the device.
  - If offline mode is enabled, displays random (to update the joke, give device a shake) joke by the user also replaces name of the character given in Settings 
  
|List|Random Joke|
|------------|-------------|
|![screenshot-1590375348824](https://user-images.githubusercontent.com/45944138/82774095-8be58780-9e4c-11ea-92b8-9a13000cbfa2.jpg)|![screenshot-1590375370308](https://user-images.githubusercontent.com/45944138/82774166-c0f1da00-9e4c-11ea-81fc-93383e6c926e.jpg)|

- My Jokes: 
  - Displays jokes liked/added by user
  - **data** persists in Room
  - User can delete and add the jokes

|My Jokes|
|------------|
<img src="https://user-images.githubusercontent.com/45944138/82774516-c26fd200-9e4d-11ea-8185-6cf95f145cb7.jpg" height="750" width="405">

- Add Joke: 
  - User can write own jokes 
  - **data** persists joke in Room
  
|Add Joke|
|------------|
<img src="https://user-images.githubusercontent.com/45944138/82774913-cd773200-9e4e-11ea-9625-3c3b33086312.jpg" height="750" width="405">

- Settings: 
  - User can switch on/off the ofline mode 
  - User can change the name of character that will replace default name when in offline mode on **Jokes List** screen
  - **data** persists user choices in SharedPreference
  
|Settings|
|------------|
<img src="https://user-images.githubusercontent.com/45944138/82775042-2e066f00-9e4f-11ea-8981-a997e0a5afc1.jpg" height="750" width="405">












