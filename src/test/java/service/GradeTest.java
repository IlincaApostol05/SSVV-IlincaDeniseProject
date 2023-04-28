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


public class GradeTest {

    private Service service;

    @Before
    public void setUp(){
        Validator<Student> validator = new StudentValidator();
        Validator<Tema> validator1 = new TemaValidator();
        Validator<Nota> validator2 = new NotaValidator();
        StudentXMLRepository studentFileRepository = new StudentXMLRepository( validator,"studentiTest.xml");
        TemaXMLRepository temaFileRepository = new TemaXMLRepository(validator1, "teme.xml");
        NotaXMLRepository notaFileRepository = new NotaXMLRepository(validator2, "note.xml", false);

        service = new Service(studentFileRepository, temaFileRepository, notaFileRepository);
    }

    @Test
    public void addGrade(){
        assertEquals(service.saveNota("1", "1", 10, 8, "aaaa"), 1);
    }

    @Test
    public void addStudent() {
        assertEquals(service.saveStudent("1", "aaa", 935), 0);
    }

    @Test
    public void addAssignment(){assertEquals(service.saveTema("1", "description", 9, 7), 0);}

    @Test
    public void integrationAssignment(){
        addStudent();
        addAssignment();
    }

    @Test
    public void integrationGrade(){
        addStudent();
        addAssignment();
        assertEquals(service.saveNota("1", "1", 10, 8, "aaa"), 1);
    }

    @Test
    public void integrationTesting(){
        addGrade();
        addStudent();
        addAssignment();
    }

}
