# todo-endpoint

The goal of this project is to go through the manual steps of creating an endpoint within Google App Engine.

Basically this endpoint has the following apis:

1. add
 * POST: http://localhost:8888/_ah/api/todo/v1/todo/{message}
2. get
 * GET: http://localhost:8888/_ah/api/todo/v1/todos
3. update
 * PUT: http://localhost:8888/_ah/api/todo/v1/todo/{id}
4. delete
 * DELETE: http://localhost:8888/_ah/api/todo/v1/todo/{id}

##Getting Started##

1. In build.gradle Define the version of GAE you want to use and your email address

    ```
    5. def appengineVersion = "1.9.24"
    6. def appEmail = "your@email.com"
    ```
2. Update the Constant.java file with your Google App Engine ClientIds
3. To run the endpoint run

    ```
    $ gradle appengineRun 
    ```
4. To run the unit tests

    ```
    $ gradle appengineFunctionalTest 
    ```

##Using Eclipse##
1. If you haven't already, install https://marketplace.eclipse.org/content/gradle-integration-eclipse-0
2. Import a gradle project, and select the option to run cleanEclipse eclispe
3. Alternatively, you can run the below within command line and then import existing project

    ```
    $ gradle cleanEclipse
    $ gradle eclipse
    ```

###Happy Todo'ing :)###
