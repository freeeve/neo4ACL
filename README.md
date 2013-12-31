# neo4ACL
An unmanaged extension to provide a generic access control layer with Neo4j, through http/json endpoints. This is not a REST API.

## installation

## usage
The standalone endpoints are a generic ACL solution, disconnected from the rest of your graph. The cypher-oriented endpoints allow you to customize the functionality and connect your ACL to an existing graph via cypher queries.

### standalone endpoints
The standalone endpoints are meant to be treated as a user/group/permission/content service. The goal is to provide a way for non-neo4j experts to take advantage of the speed of hierarchical traversals for access control management.

#### /acl/init GET
Creates appropriate indexes. Only needs to be run once, but won't hurt anything if it is run again.

#### /acl/users POST

##### /acl/users/create
The users create action takes 1 required parameter: `id`, and one optional parameters: `props`, an object that can be primitives or collections of primitives as values.

``` JavaScript
{ 
  "id":"unique string id",
  "props":{"foo":"bar"} // optional
}
```

##### /acl/users/update
The users update action takes the same parameters as "create", but won't create a new user.
``` JavaScript
{ 
  "id":"unique string id",
  "props":{"foo":"bar"} // optional
}
```

##### /acl/users/query
query will return the properties of a user, along with optionally their memberships, permissions, and content access.

``` JavaScript
// request
{
  "id":"unique string id",
  "memberships":true, // optional
  "permissions":true, // optional
  "access":true       // optional
}

// response
{
  "id":"unique string id",
  "memberships":["a group id"],
  "permissions":["permission id"],
  "access":{"content id":["read-only"]}
}

```

##### /acl/users/delete
``` JavaScript
{ 
  "id":"unique string id"
}
```

##### /acl/users/check-access
A function to:

* check membership in select groups
* check whether a user inherits select permissions
* check what types of access a user has to select content

``` JavaScript
// request
{ 
  "id":"unique string id",
  "membership":["a group id"], // optional; check whether user is a member of groups
  "permissions":["permission id", "another"], // optional; check whether user inherits a permission
  "content":["content id"] // optional; check whether user has access to content, and return access type
}

// response
{
  "id":"unique string id",
  "membership":{"a group id":true},
  "permissions":{"permission id":true, "another":false},
  "content":{"content id":["read-only"]}
}
```

#### /acl/groups

##### /acl/groups/create
The groups create action takes 1 required parameter: `id`, and one optional parameters: `props`, an object that can be primitives or collections of primitives as values.

``` JavaScript
{ 
  "id":"unique string id",
  "props":{"foo":"bar"}
}
```

##### /acl/groups/update
The groups update action takes the same parameters as "create", but won't create a new group. Properties will be overwritten.
``` JavaScript
{ 
  "id":"unique string id",
  "props":{"foo":"bar2"}
}
```

##### /acl/groups/query
query will return the properties of a group, along with optionally their memberships, permissions, and content access.

``` JavaScript
// request
{
  "id":"unique string id",
  "memberships":true, // optional
  "permissions":true, // optional
  "access":true       // optional
}

// response
{
  "id":"unique string id",
  "memberships":["a group id"],
  "permissions":["permission id"],
  "access":{"content id":["read-only"]}
}

```

##### /acl/groups/delete
``` JavaScript
{ 
  "id":"unique string id"
}
```

##### /acl/groups/add-member
``` JavaScript
{ 
  "id":"unique string id",
  "member":"member id"
}
```

#### /acl/permissions

##### /acl/permissions/create
``` JavaScript
{
  "id":"unique string id"
}
```

##### /acl/permissions/drop
``` JavaScript
{
  "id":"unique string id"
}
```

##### /acl/permissions/add-access
``` JavaScript
{
  "id":"unique string id",
  "content":"content id",
  "type":"read-only"
}
```

##### /acl/permissions/drop-access
``` JavaScript
{
  "id":"unique string id",
  "content":"content id",
  "type":"read-only"
}
```

#### /acl/contents

##### /acl/contents/create
``` JavaScript
{ 
  "id":"unique string id",
  "props":{"foo":"bar2"}
}
```

##### /acl/contents/update
``` JavaScript
{ 
  "id":"unique string id",
  "props":{"foo":"bar"}
}
```

##### /acl/contents/query
``` JavaScript
// request
{ 
  "id":"unique string id"
}

// response
{ 
  "id":"unique string id",
  "props":{"foo":"bar"}
}
```

##### /acl/contents/drop
``` JavaScript
{ 
  "id":"unique string id",
}
```

### cypher-oriented endpoints
The goal of these endpoints are to provide a way to link up ACL to your existing graph by providing the lookup Cypher queries to use to find the nodes, rather than using content ids. Be sure to use unique relationship types for the ACL pieces of the graph.


## license
MIT license.
