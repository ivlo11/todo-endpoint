# todo-endpoint

The goal of this project is to go through the manual steps of creating an end point within google app engine.

Basically this endpoint has the following apis:

1. add
 * POST: http://localhost:8888/_ah/api/todo/v1/todo/{message}
2. get
 * GET: http://localhost:8888/_ah/api/todo/v1/todos
3. update
 * PUT: http://localhost:8888/_ah/api/todo/v1/todo/{id}
4. delete
 * DELETE: http://localhost:8888/_ah/api/todo/v1/todo/{id}

Note: all jars were omitted from git

Once cloned and imported into Eclipse:

1. Add the below jars
 * 'war/WEB-INF/lib/objectify-5.1.7.jar'
 * 'war/WEB-INF/lib/guava-18.0.jar'
 * 'lib/appengine-api-stubs.jar' (jar is in SDK directory lib/impl/)
 * 'lib/appengine-testing.jar' (jar is in SDK directory lib/testing/)
2. Configure build path and bound App Engine SDK to your configured SDKs (this was written with appengine-java-sdk-1.9.24 - 1.9.22)
3. Update the Constant.java file with your Google App Engine ClientIds
