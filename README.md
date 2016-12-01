# transport-app-demo

![Overview Screen](https://raw.githubusercontent.com/toni-rajkovski/transport-app-demo/master/overview_screen.png)
![Maps Screen](https://raw.githubusercontent.com/toni-rajkovski/transport-app-demo/master/maps_screen.png)

# Overview
The App loads the json data from the assets folder and displays it.

* Currently there is no implementation for loading the json data from the server
* The images are loaded from network, therefore the internet connection is required in order to see the images
* The caching of the images is only in memory right now
* Google Maps has only API_KEY for the debug build. The API_KEY along with the singing key for debug are both committed in the code
* The release version of the App needs generation of new signing key and API_KEY for Google Maps
* Proguard configuration is still not finished even though there is not visible problems with the release build
* Minimum supported version of Android is KitKat(4.4, API Level 19)

# Build
### Build the project with the gradle wrapper
```
gradlew build
```
When you clone the project you need to build it first before start with development. Both Model classes and Dagger 2 specific classes needs to be generated first.

### Run the Unit tests
```
gradlew test
```
The App uses both JUnit tests and Robolectric Unit tests for Android specific stuff.
Most of the tests are pure JUnit tests which are quite faster then Robolectric ones. Only in the place where Android specific stuff is required for testing Robolectric is used.

### Run the UI tests(Espresso)
```
gradlew cAT
```
You'll need a connected device in order to run the UI tests(no internet connection is required, the data is mocked).


# Libraries & patterns
* MVP pattern for the activities
* RxAndroid for asynchronous calls
* OkHttp to server calls
* Dagger 2 for dependency injection
* Gson for parsing the json data
* Google Play Services(Maps) for the Maps
* SVG Kit(libsvg) for displaying SVG images
* jsonschema2pojo to generate the model from the json file
* RecyclerView for displaying the list of the routes
* Android Support Library for backwards compatibility
* Robolectric for Unit tests
* Mockito for mocking data
* Espresso for UI tests
