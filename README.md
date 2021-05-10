# Space Launch

Retrieval of the space launches using clean MVVM architecture. 

Things to do with more time. 

- General UI improvements
- Storage. Currently the api is being hit and rendering the items within the adapter. I would aim to cache these items using a third part library, or store against a database using a library such as Room. 
- I would like pagination, haven't checked if the API supports this, but I don't believe it does. 
- Error handler for network requests. 
- More thorough testing. 
- Kotlin tidy up. There is likely a few bits and pieces that I could tidy up with kotlin language functions.
- Pull to refresh or work manager timer. There is not currently a way to refresh the list. This would need to be added. 