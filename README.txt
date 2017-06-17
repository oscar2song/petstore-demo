To test the application, follow these steps:

- download and unzip the project
- build the project with maven 	=> mvn clean install 
- run the application 		=> java -jar target/pet-store-1.0.0.jar
- open your favorite browser and go to localhost:8080

Deploy to Bluemix
cf login
cf push petstorecloud -p target/petstore-demo-1.0.0.jar
