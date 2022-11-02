# GraphQLDemo
Demo for GraphQL in Spring as seen in https://youtu.be/atA2OovQBic.

This version is an extension of the previous.
It solves the N+1 problem.

When you get all authors and their books, it sends one query to fetch the authors (1).
And for each author it sends a query to retrieve the books for that one author (N).

To solve this, `AuthorController` gets an additional method:
```java
    @BatchMapping
    Map<Author, List<Book>> books(List<Author> authors) {
        Iterable<Book> books = bookRepository.findAll();
        return authors.stream()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                author ->
                                        StreamSupport.stream(books.spliterator(), false)
                                                .filter(b -> author.equals(b.getAuthor()))
                                                .toList()));
    }
```

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
