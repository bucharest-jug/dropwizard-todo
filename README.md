Todo list with Dropwizard
=========================

A simple todo list sample application implemented using dropwizad
as the core framework with a simple UI that consumes the REST API
based on twitter bootstrap, backbone.js, jquery and jasmine.

**Our focus is on**: project structure, code quality, testing, deployment

Specification - DRAFT 1 -
------------------------

The service will be mounted on $BASE_URL. It will expose a simple *todos* resource that has the following API:

* GET     /todos - will return the list of todos
* POST    /todos - will create another todo and return an URL to the new resource

* GET     /todos/{id}  - will get details about a single todo (including all items)
* PUT     /todos/{id}  - will update the todo specified by {id}
* DELETE  /todos/{id}  - will delete the todo specified by {id}

A **todo** has the following information:
  - id : assigned by server on creation (Read Only)
  - created : assigned on creation (Read Only)
  - email: owner email address
  - items: list of items
  
A **todo item** has the following information:
  - title: a text description of what need to be done
  - created: date time assigned on creation
  - finished: date time assigned when the item was finished

Links
-----

* http://dropwizard.codahale.com/
* http://twitter.github.com/bootstrap/
* http://pivotal.github.com/jasmine/ 

