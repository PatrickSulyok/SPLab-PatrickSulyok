# Design Patterns — Lab 1

This project implements the final book model and both homework tasks in
**SP — Lab 1 — Project setup and initial book model**. The original repository
README is preserved.

## Setup and running

The project was generated through the official Spring Initializr service with
Maven, Java 17, and Spring Web. It uses Spring Boot 4.1.1 and the generated
Maven wrapper. Lombok is intentionally omitted as requested; constructors and
methods are written explicitly. Spring Web's modern starter is
`spring-boot-starter-webmvc`. No controllers or web APIs are needed for this lab.

Open the repository's `pom.xml` in IntelliJ as a Maven project and select JDK 17
or newer. Run `SpLabPatrickSulyokApplication.main` in IntelliJ, or use:

```bash
./mvnw clean verify
java -jar target/splab-patricksulyok-0.0.1-SNAPSHOT.jar
```

On Windows, use `mvnw.cmd clean verify`. The first wrapper run downloads Maven
and dependencies. `SpringApplication.run(...)` is commented out, as instructed
on page 6, so running the main method prints the example and exits without
starting Spring or a web server.

## Model and ownership

- `Element` is an abstract class with `print`, `add`, `remove`, `get(int)`, and
  a private parent reference, following solution 2 on pages 8–9.
- `Section` stores its children in a private, ordered `List<Element>`. It prints
  its heading and recursively prints its children in insertion order.
- `Book` extends `Section`, aggregates authors, and delegates `addContent` to
  `add`. Its `print` prints the book title and authors before calling
  `super.print()` to traverse the content. A small `printTitle` override prevents
  the title from being printed twice. This follows the explicit `super.print`
  instruction on page 6; the diagram itself does not show the inheritance arrow.
- `Paragraph`, `Image`, `Table`, and `TableOfContents` are leaves. Their
  composite-only operations throw `UnsupportedOperationException`.
- `Author` has the diagram's `name` and `surname` fields. The single-argument
  constructor accepts the full name used in the professor's example; a second
  constructor accepts the fields separately. Authors can be shared between
  books, because this relationship remains aggregation.

`Section.add` rejects any element that already has a parent, including duplicate
insertion in the same section, with `IllegalStateException`. Successful insertion
assigns the parent. The same rule applies to books. Self-containment and inserting
an ancestor are rejected with `IllegalArgumentException` to prevent recursion
cycles. Removing a child clears its parent so it can be moved; removing an
unrelated element changes nothing. Removing a section preserves its subtree.
There is no public parent setter or exposed mutable child list.

As the PDF notes, solution 2 prevents shared ownership but does not implement
deterministic destruction of child objects. Java garbage collection controls
object lifetime. No manual destruction is claimed.

The diagram gives no table data schema or automatic table-of-contents algorithm.
`Table` prints its title, and `TableOfContents` prints a simple heading. They can
be added as ordinary elements and are tested separately. Neither is inserted into
the main demo, because the professor's expected example contains neither.

## Professor's expected output

The main method reproduces the construction code from pages 6–7 unchanged in
content and order. The output is checked exactly by an automated test:

```text
Book: Noapte buna, copii!

Authors:
Author: Radu Pavel Gheo

Paragraph: Multumesc celor care ...
Capitolul 1
Paragraph: Moto capitol
Capitolul 1.1
Paragraph: Text from subchapter 1.1
Capitolul 1.1.1
Paragraph: Text from subchapter 1.1.1
Subchapter 1.1.1.1
Image with name:Image subchapter 1.1.1.1
```

## Requirement review

Validated with OpenJDK 17.0.20 and the generated Maven 3.9.16 wrapper:
`clean verify` succeeded; all 17 JUnit tests passed with no failures, errors, or
skipped tests. Running the repackaged JAR exited successfully and its complete
output matched the example above exactly.

| PDF requirement | Implementation / verification |
| --- | --- |
| Spring Initializr project, pages 1–2 | Generated Maven project, Boot parent/plugin, wrappers, application properties |
| Disable Spring startup, page 6 | Commented `SpringApplication.run` in the generated application class |
| Final model, page 6 | `Element`, `Book`, `Author`, `Section`, all four leaf types |
| Common `add`, `remove`, `get`, `print` operations | `Element`; composite implementations in `Section`; leaf exceptions |
| Ordered content and nested sections | Private `ArrayList`; recursive printing; example and mixed-content tests |
| Book authors and `super.print` | `Book.print`, `Author.print`; exact-output and multiple-author tests |
| Example book and expected output, pages 6–7 | Main method and `SpLabPatrickSulyokApplicationTests` |
| First homework, page 8 | Entire final model is implemented |
| Second homework, pages 8–9 | Solution 2: private parent, assignment/removal, sharing rejection |
| Ownership and recursion checks | `CompositeModelTests`: all leaves, whole sections, books, duplicate insertion, cycles, movement |
| Exclude IDE/build files, pages 7–8 | `.gitignore`: `.idea`, `*.iml`, `out`, `target`, other generated output and OS metadata |
| Compile, test, run | Maven verification and running the packaged application |
| Version control | Implementation on `lab1`, based on the original `main` commit; no merge into `main` |

## Short answers to the introductory questions

1. An IDE combines an editor, compiler/build integration, debugger, and project
   tools. IntelliJ IDEA is the IDE used in the lab. IDE installation is performed
   on the student's computer and is not part of the remote source code.
2. The sample `Car` diagram has three attributes and two methods. A constructor
   is useful for initializing valid state. If no constructor is declared, Java
   supplies a no-argument constructor; numeric instance fields default to zero.
   Attributes should normally be private.
3. Inheritance is an "is-a" relationship. Association connects objects.
   Aggregation is a "has-a" relationship with independent lifetimes and possible
   sharing. Composition models exclusive ownership of parts.
4. In the initial book diagram: `Book`–`TableOfContents` is association;
   `Book`–`Author` is aggregation; `Book`–`Chapter`, `Chapter`–`SubChapter`, and
   `SubChapter`–content are composition. No direct relationship is drawn between
   the different leaf content types.
5. The initial model requires changes for each new content type, cannot easily
   preserve a mixed content order, disallows content directly in chapters, and
   fixes the nesting depth through separate chapter/subchapter types.
6. Composite fixes these issues by treating a section and a leaf uniformly as
   an `Element`, allowing arbitrary nesting and one ordered child collection.
   Parent protection addresses the sharing flaw identified by the second task.
