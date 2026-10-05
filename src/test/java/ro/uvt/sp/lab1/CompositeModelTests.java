package ro.uvt.sp.lab1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompositeModelTests {
    @Test
    void getAndRemovePreserveInsertionOrder() {
        Section section = new Section("Chapter");
        Element first = new Paragraph("First");
        Element middle = new Image("Middle");
        Element last = new Table("Last");
        section.add(first);
        section.add(middle);
        section.add(last);

        assertSame(first, section.get(0));
        assertSame(middle, section.get(1));
        assertSame(last, section.get(2));
        section.remove(middle);
        assertSame(first, section.get(0));
        assertSame(last, section.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> section.get(2));
    }

    @Test
    void mixedLeafTypesPrintRecursivelyInInsertionOrder() {
        Section chapter = new Section("Chapter");
        Section subchapter = new Section("Subchapter");
        chapter.add(new Paragraph("Before"));
        chapter.add(subchapter);
        subchapter.add(new Image("picture.png"));
        subchapter.add(new Table("Results"));
        chapter.add(new TableOfContents());

        assertEquals("""
                Chapter
                Paragraph: Before
                Subchapter
                Image with name:picture.png
                Table with Title: Results
                Table of Contents
                """, capture(chapter::print));
    }

    @Test
    void parentReferencesAllowBottomUpTraversal() {
        Book book = new Book("Book");
        Section chapter = new Section("Chapter");
        Element paragraph = new Paragraph("Text");
        book.addContent(chapter);
        chapter.add(paragraph);

        assertNull(book.getParent());
        assertSame(chapter, paragraph.getParent());
        assertSame(book, paragraph.getParent().getParent());
    }

    @Test
    void sharingAnElementBetweenSectionsFailsWithoutChangingEitherSection() {
        Section first = new Section("First");
        Section second = new Section("Second");
        Element paragraph = new Paragraph("Shared?");
        first.add(paragraph);

        assertThrows(IllegalStateException.class, () -> second.add(paragraph));
        assertSame(first, paragraph.getParent());
        assertSame(paragraph, first.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> second.get(0));
    }

    @Test
    void duplicateInsertionIntoTheSameSectionIsRejected() {
        Section section = new Section("Chapter");
        Element paragraph = new Paragraph("Once");
        section.add(paragraph);

        assertThrows(IllegalStateException.class, () -> section.add(paragraph));
        assertSame(paragraph, section.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> section.get(1));
    }

    @Test
    void booksAndSectionsApplyTheSameOwnershipRule() {
        Book first = new Book("First");
        Book second = new Book("Second");
        Section chapter = new Section("Chapter");
        Element paragraph = new Paragraph("Text");
        first.addContent(paragraph);

        assertThrows(IllegalStateException.class, () -> second.addContent(paragraph));
        assertThrows(IllegalStateException.class, () -> second.add(paragraph));
        assertThrows(IllegalStateException.class, () -> chapter.add(paragraph));
        assertSame(first, paragraph.getParent());
    }

    @Test
    void sharingASectionWithItsEntireSubtreeIsRejected() {
        Book first = new Book("First");
        Book second = new Book("Second");
        Section chapter = new Section("Chapter");
        Element image = new Image("picture.png");
        chapter.add(image);
        first.addContent(chapter);

        assertThrows(IllegalStateException.class, () -> second.addContent(chapter));
        assertSame(first, chapter.getParent());
        assertSame(chapter, image.getParent());
    }

    @Test
    void aSectionCannotContainItself() {
        Section section = new Section("Chapter");

        assertThrows(IllegalArgumentException.class, () -> section.add(section));
        assertNull(section.getParent());
        assertThrows(IndexOutOfBoundsException.class, () -> section.get(0));
    }

    @Test
    void aDescendantCannotContainAnAncestorEvenWhenTheAncestorHasNoParent() {
        Book book = new Book("Book");
        Section chapter = new Section("Chapter");
        Section subchapter = new Section("Subchapter");
        book.addContent(chapter);
        chapter.add(subchapter);

        assertThrows(IllegalArgumentException.class, () -> subchapter.add(book));
        assertNull(book.getParent());
        assertSame(book, chapter.getParent());
        assertSame(chapter, subchapter.getParent());
    }

    @Test
    void removingAnElementAllowsItToMoveToAnotherSection() {
        Section first = new Section("First");
        Section second = new Section("Second");
        Element paragraph = new Paragraph("Move me");
        first.add(paragraph);
        first.remove(paragraph);

        assertNull(paragraph.getParent());
        assertThrows(IndexOutOfBoundsException.class, () -> first.get(0));
        second.add(paragraph);
        assertSame(second, paragraph.getParent());
        assertSame(paragraph, second.get(0));
    }

    @Test
    void removingANonChildDoesNotDetachItFromItsActualParent() {
        Section owner = new Section("Owner");
        Section other = new Section("Other");
        Element paragraph = new Paragraph("Text");
        owner.add(paragraph);
        other.remove(paragraph);

        assertSame(owner, paragraph.getParent());
        assertSame(paragraph, owner.get(0));
        assertThrows(IllegalStateException.class, () -> other.add(paragraph));
    }

    @Test
    void removingASectionPreservesOwnershipInsideItsSubtree() {
        Book book = new Book("Book");
        Section chapter = new Section("Chapter");
        Element paragraph = new Paragraph("Text");
        chapter.add(paragraph);
        book.addContent(chapter);
        book.remove(chapter);

        assertNull(chapter.getParent());
        assertSame(chapter, paragraph.getParent());
        assertSame(paragraph, chapter.get(0));
    }

    @Test
    void nullContentIsRejectedWithoutAddingAChild() {
        Book book = new Book("Book");
        assertThrows(NullPointerException.class, () -> book.addContent(null));
        assertThrows(NullPointerException.class, () -> book.addAuthor(null));
        assertThrows(IndexOutOfBoundsException.class, () -> book.get(0));
    }

    @Test
    void allLeafTypesRejectCompositeOnlyOperations() {
        List<Element> leaves = List.of(new Paragraph("Text"), new Image("picture.png"),
                new Table("Results"), new TableOfContents());
        for (Element leaf : leaves) {
            assertThrows(UnsupportedOperationException.class,
                    () -> leaf.add(new Paragraph("Child")));
            assertThrows(UnsupportedOperationException.class,
                    () -> leaf.remove(new Paragraph("Child")));
            assertThrows(UnsupportedOperationException.class, () -> leaf.get(0));
            assertNull(leaf.getParent());
        }
    }

    @Test
    void everyLeafTypeHasTheSameParentProtection() {
        Section first = new Section("First");
        Section second = new Section("Second");
        List<Element> leaves = List.of(new Paragraph("Text"), new Image("picture.png"),
                new Table("Results"), new TableOfContents());
        for (Element leaf : leaves) {
            first.add(leaf);
            assertSame(first, leaf.getParent());
            assertThrows(IllegalStateException.class, () -> second.add(leaf));
        }
    }

    @Test
    void authorsArePrintedInOrderAndCanBeSharedBetweenBooks() {
        Book first = new Book("First");
        Book second = new Book("Second");
        Author shared = new Author("Radu Pavel", "Gheo");
        first.addAuthor(shared);
        first.addAuthor(new Author("Another author"));
        second.addAuthor(shared);

        assertEquals("""
                Book: First

                Authors:
                Author: Radu Pavel Gheo
                Author: Another author

                """, capture(first::print));
        assertEquals("""
                Book: Second

                Authors:
                Author: Radu Pavel Gheo

                """, capture(second::print));
    }

    private static String capture(Runnable action) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;
        try (PrintStream stream = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(stream);
            action.run();
        } finally {
            System.setOut(original);
        }
        return output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }
}
