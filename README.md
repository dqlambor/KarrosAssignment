# KarrosAssignment 2020.04
<duyquang.nguyen>

In order to run the application, please setup the environment as follwing informations:
- Firstly, make sure you have setup and successfully run the FirefoxDriver on your machine.
- Download Rest-Assured Jars/Library to include into the project: https://github.com/rest-assured/rest-assured/wiki/Downloads
- Download and open the project in your development IDE ( I use Intellij 2018.3) 
  + Please select "Import changes" when you get event message "Maven project need to be imported"
  + And select "Configure..." to remove the "Invalid VSC root mapping" once you get the "Error Loading Project: Cannot load module KarrosAssignment".

- Add all the "jar" files from Rest-Assured into the project as an External Library (This should include the "rest-assured-4.3.0.jar" and all jars inside the "rest-assured-4.3.0-deps" folder.
- Then you should be able to combine and run the "AssignmentTest".
- Mornitor the test Result via console output.
*Please notes:
- @Test(priority = 3) will run the method to Verify filter Student Access Request with INACTIVE.
- @Test(priority = 5) and @Test(priority = 6) will run the methods to Verify sorting of First Name column.
- @Test(priority = 7) will run the method for testing API endpoint.


Regards,
Duy Quang
