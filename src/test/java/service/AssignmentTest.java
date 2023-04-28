package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import static org.junit.Assert.assertEquals;

public class AssignmentTest {

    private Service service;

    @Before
    public void setUp(){
        Validator<Student> validator = new StudentValidator();
        Validator<Tema> validator1 = new TemaValidator();
        Validator<Nota> validator2 = new NotaValidator();
        StudentXMLRepository studentFileRepository = new StudentXMLRepository( validator,"studentiTest.xml");
        TemaXMLRepository temaFileRepository = new TemaXMLRepository(validator1, "teme.xml");
        NotaXMLRepository notaFileRepository = new NotaXMLRepository(validator2, "note.xml");

        service = new Service(studentFileRepository, temaFileRepository, notaFileRepository);
    }

    @Test
    public void addAssignment_nullId(){
        assertEquals(service.saveTema(null, "aaaa", 6, 4), 1);
    }

    @Test
    public void addAssignment_invalidId(){assertEquals(service.saveTema("", "aaaa", 6, 4), 1);}

    @Test
    public void addAssignment_nullDescription(){
        assertEquals(service.saveTema("1", null, 6, 4), 1);
    }

    @Test
    public void addAssignment_invalidDescription(){assertEquals(service.saveTema("1", "", 6, 4), 1);}

    @Test
    public void addAssignment_deadlineSmaller(){
        assertEquals(service.saveTema("1", "description", 0, 4), 1);
    }

    @Test
    public void addAssignment_deadlineBigger(){assertEquals(service.saveTema("1", "description", 15, 4), 1);}

    @Test
    public void addAssignment_startlineSmaller(){
        assertEquals(service.saveTema("1", "description", 4, 0), 1);
    }

    @Test
    public void addAssignment_startlineBigger(){assertEquals(service.saveTema("1", "description", 4, 15), 1);}

    @Test
    public void addValidAssignment(){assertEquals(service.saveTema("11", "description", 9, 7), 0);}

}
