package nl.avermaas.graphqldemo;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class AuthorController {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @QueryMapping
    Iterable<Author> authors() {
        return authorRepository.findAll();
    }

    @QueryMapping
    Optional<Author> authorById(@Argument Long id) {
        return authorRepository.findById(id);
    }

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

    @MutationMapping
    Book addBook(@Argument BookInput book) {
        Author author = authorRepository.findById(book.authorId()).orElseThrow(() -> new IllegalArgumentException("Author not found"));
        Book b = new Book(book.title(), book.publisher(), author);
        return bookRepository.save(b);
    }

    record BookInput(String title, String publisher, Long authorId) {}
}
