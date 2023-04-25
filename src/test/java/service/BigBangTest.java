package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import static org.junit.Assert.assertEquals;

public class BigBangTest {
    private Service service;

    @Before
    public void setUp() {
        Validator<Student> validator = new StudentValidator();
        Validator<Tema> validator1 = new TemaValidator();
        Validator<Nota> validator2 = new NotaValidator();
        StudentXMLRepository studentFileRepository = new StudentXMLRepository( validator,"studentiTest.xml");
        TemaXMLRepository temaFileRepository = new TemaXMLRepository(validator1, "teme.xml");
        NotaXMLRepository notaFileRepository = new NotaXMLRepository(validator2, "note.xml");

        service = new Service(studentFileRepository, temaFileRepository, notaFileRepository);
    }

    @Test
    public void addStudent() {
        assertEquals(service.saveStudent("15", "ana", 934), 0);
    }

    @Test
    public void addAssignment() {
        assertEquals(service.saveTema("15", "descriere", 10, 1), 0);
    }

    @Test
    public void addGrade() {
        service.saveTema("16", "descriere", 10, 1);
        service.saveStudent("16", "bob", 934);
        assertEquals(service.saveNota("2", "2", 10, 3, "very good"), 1);
    }

    @Test
    public void integrateBigBang() {
        addStudent();
        addAssignment();
        addGrade();
    }
}
