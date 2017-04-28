# FBSearchAndroid
Android Application Built using the Facebook API REST Service


Ø	Mobile user experience for Facebook Search using the Facebook Graph API

Ø	Add social networking features using the Facebook SDK for Android

Ø Facebook Graph API

The Graph API is the primary way to get data out of, and put date into Facebook's platform. It's a lowlevel HTTP-based API that you can use to programmatically query data, post new stories, manage ads, upload photos, and perform a variety of other tasks that an app might implement. To learn more about the Facebook Graph API visit:

https://developers.facebook.com/docs/graph-api

Ø Amazon Web Services (AWS)

AWS is Amazon’s implementation of cloud computing. Included in AWS is Amazon Elastic Compute Cloud (EC2), which delivers scalable, pay-as-you-go compute capacity in the cloud, and AWS Elastic Beanstalk, an even easier way to quickly deploy and manage applications in the AWS cloud. You simply upload your application, and Elastic Beanstalk automatically handles the deployment details of capacity provisioning, load balancing, auto-scaling, and application health monitoring. Elastic Beanstalk is built using familiar software stacks such as the Apache HTTP Server, PHP, and Python, Passenger for Ruby, IIS 7.5 for .NET, and Apache Tomcat for Java.

The Amazon Web Services homepage is available at: http://aws.amazon.com/

Ø Prerequisites
A.	Download and install Android Studio. You may use any other IDE other than Android Studio such as Eclipse, but you will be on your own if problems spring up.

B.	First you need to install Java on your local machine. You can download JDK 8 from - http://www.oracle.com/technetwork/java/javase/downloads/index.html. For windows users, after installing the JDK, you need to add environment variables for JDK.

•	Properties -> Advanced -> Environment Variables -> System variables -> New Variable

Name: JAVA_HOME, Variable Value: <Full path to the JDK>

•	Typically, this full path looks something like C:\Program Files\Java\jdk1.8.0.

Then modify the PATH variable as follows on Microsoft Windows: C:\WINDOWS\system32;C:\WINDOWS;C:\Program Files\Java\jdk1.8.0\bin This path may vary depending on your installation.

•	Note: The PATH environment variable is a series of directories separated by semicolons (;) and is not case-sensitive. Microsoft Windows looks for programs in the PATH directories in order, from left to right. You should only have one bin directory for a JDK in the path at a time. Those following the first instance are ignored. If you are not sure where to add the path, add it to the right of the value of the PATH variable. The new path takes effect in each new command window you open after setting the PATH variable.

•	Reboot your computer and type “java –version” in the terminal to see whether your JDK has been installed correctly.

Set up the Android Studio environment so that you can run any sample android app on your phone/tablet/virtual device from it. Then you can start with this homework app. You will need to enable “Developer Options” and “USB debugging” if you are using an actual device. There are endless resources a simple search away on how to setup your Android Studio. 

. High Level Design

There will be a slide out-menu which will provide access to the different screens such as Home, Favorites and About Me. The content displayed in each of these sections will be similar to Homework 8 but we will go into details later on.

Ø Home Screen
The initial screen would default to showing the home screen as shown below.
 
![Alt text](/screenshots/home.jpg?raw=true "Home Screen")

The interface consists of the following:

•	A ‘TextView’ to allow the user to enter the search query.

•	2 ‘Button’ for clearing and submitting the query.

•	A component to show the navigation bar that has the hamburger menu icon to show the slide-out menu.


Ø Slide Out App Drawer

![Alt text](/screenshots/slide_out.png?raw=true "App Drawer Slide Out Functionality")


Ø Results Screen
![Alt text](/screenshots/results.png?raw=true "Results Screen")

The search results would display the results in 5 tabs for the following types, like in Homework 8:

      •	User

      •	Page

      •	Event

      •	Place

      •	Group
      
Ø User Tab

The user tab would display the search result for the type – ‘user’. The screen consists of the following:

1.	‘ListView’ to show the users corresponding to the search query.
2.	2 ‘Button’ for pagination
3.	The navigation bar to display back button to go back to home screen

Each row in the table would contain the following:

1.	Icon – An ‘ImageView’ component for the icon of the user
2.	Name – A ‘TextView’ component for the name of the user
3.	Favorite – ‘ImageView’ component indicating whether the user has been marked as favorite
4.	Detail Disclosure – Use the ‘ImageView’ component to handle click action for user’s detail described in the user detail screen next.

Ø Pages Tab

The page tab would display the search result for the type – ‘page’. The screen consists of the following:

1.	‘ListView’ to show the pages corresponding to the search query.
2.	2 ‘Button’ for pagination
3.	The navigation bar to display back button to go back to home screen

Each row in the table would contain the following:

1.	Icon – An ‘ImageView’ component for the icon of the page
2.	Name – A ‘TextView’ component for the name of the page
3.	Favorite – ‘ImageView’ component indicating whether the page has been marked as favorite
4.	Detail Disclosure – Use the ‘ImageView’ component to handle click action for page’s detail described in the page detail screen next.

Ø Events Tab

The page tab would display the search result for the type – ‘page’. The screen consists of the following:

1.	‘ListView’ to show the pages corresponding to the search query.
2.	2 ‘Button’ for pagination
3.	The navigation bar to display back button to go back to home screen

Each row in the table would contain the following:

1.	Icon – An ‘ImageView’ component for the icon of the page
2.	Name – A ‘TextView’ component for the name of the page
3.	Favorite – ‘ImageView’ component indicating whether the page has been marked as favorite
4.	Detail Disclosure – Use the ‘ImageView’ component to handle click action for page’s detail described in the page detail screen next.

Ø Places Tab

The page tab would display the search result for the type – ‘page’. The screen consists of the following:

1.	‘ListView’ to show the pages corresponding to the search query.
2.	2 ‘Button’ for pagination
3.	The navigation bar to display back button to go back to home screen

Each row in the table would contain the following:

1.	Icon – An ‘ImageView’ component for the icon of the page
2.	Name – A ‘TextView’ component for the name of the page
3.	Favorite – ‘ImageView’ component indicating whether the page has been marked as favorite
4.	Detail Disclosure – Use the ‘ImageView’ component to handle click action for page’s detail described in the page detail screen next.

Please note that you get the user’s current location to passed to the API for the parameter center.

Ø Group Tab

The page tab would display the search result for the type – ‘page’. The screen consists of the following:

1.	‘ListView’ to show the pages corresponding to the search query.
2.	2 ‘Button’ for pagination
3.	The navigation bar to display back button to go back to home screen

Each row in the table would contain the following:

1.	Icon – An ‘ImageView’ component for the icon of the page
2.	Name – A ‘TextView’ component for the name of the page
3.	Favorite – ‘ImageView’ component indicating whether the page has been marked as favorite
4.	Detail Disclosure – Use the ‘ImageView’ component to handle click action for page’s detail described in the page detail screen next.

Ø Details Activity

![Alt text](/screenshots/details.png?raw=true "Details Screen")

The albums tab should display the 5 albums, if available within the ‘ExpandableListView’ component. Also note that the cell should be collapsed by default. However, if no album data is found, just show a ‘TextView’ component showing an appropriate message.

The user detail screen also contains the option menu option in the navigation bar. It would allow to mark the user as a favorite as well as share the user on Facebook. It would bring up the menu to show the two options – Favorite and Share. Please refer the below screenshot for reference.

Please note that the text can be ‘Add to favorites’ or ‘Remove from favorite’ depending whether the user has already been added to favorite or not. Also note that marking the user as a favorite or removing the user from favorite should display an appropriate message.

![Alt text](/screenshots/favs.png?raw=true "Facebook Share")


![Alt text](/screenshots/albums.jpg?raw=true "Albums")


![Alt text](/screenshots/posts.png?raw=true "Posts")


![Alt text](/screenshots/favorites.png?raw=true "Favorites")














