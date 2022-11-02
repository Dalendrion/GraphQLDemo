# GraphQLDemo
Demo for GraphQL in Spring as seen in https://youtu.be/atA2OovQBic.

This is the version from the end of the video

After starting the app, open http://localhost:8080/graphql

Then use the following query examples:

### Get authors
```graphql
query {
  authors {
    id
    name
    books {
      id
      title
      publisher
    }
  }
}
```

### Get only the ids of authors
```graphql
query {
  authors {
    id
  }
}
```

### Get a specific author
```graphql
query {
  authorById(id: 1) {
    id
    name
    books {
      id
      title
      publisher
    }
  }
}
```

### Get a specific author, but books only contain titles
```graphql
query {
  authorById(id: 1) {
    id
    name
    books {
      title
    }
  }
}
```

### Combine queries
```graphql
query {
  authorById(id: 1) {
    id
    name
    books {
      id
      title
      publisher
    }
  }
  authorById(id: 1) {
    id
    name
    books {
      title
    }
  }
}
```

### Add a new book
```graphql
mutation {
  addBook(book: {title: "Spring Cloud in Action", publisher: "Manning", authorId: 2}) {
    id
  }
}
```

### Fetch all books of author 2 to see the newly created book
```graphql
query {
  authorById(id: 2) {
    books {
      id
      title
      publisher
    }
  }
}
```
