package com.student.newsportalspringboot.controllers;

import com.student.newsportalspringboot.entities.Category;
import com.student.newsportalspringboot.entities.Person;
import com.student.newsportalspringboot.repositories.CategoryRepository;
import com.student.newsportalspringboot.repositories.PostRepository;
import com.student.newsportalspringboot.services.person.PersonService;
import org.junit.Assert;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

public class AdminControllerTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private PersonService personService;
    @Mock
    private Category category;
    @InjectMocks
    private AdminController adminController;
    @Mock
    private ModelAndView mav;
    @Mock
    private Person person;

    private final int id = 0;

    @Before
    public void setUp() {
        adminController = new AdminController();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestAdmin() {
        Assert.assertEquals("admin", adminController.admin());
    }

    @Test
    public void TestShowsAdmins() {
        Assert.assertEquals("admin/listPerson", adminController.showPersons().getViewName());
        verify(personService).listAllPerson();
    }

    @Test
    public void TestDeleteAdmin() {
        Assert.assertEquals("redirect:/admin/person", adminController.deleteAdmin(id));
        verify(personService).deletePerson(id);
    }

    @Test
    public void TestBlockedPerson() {
        Assert.assertEquals("redirect:/admin/person", adminController.blockedPerson(id));
        verify(personService).getPersonById(id);
        verify(personService).blockPerson(person = personService.getPersonById(id));
    }

    @Test
    public void TestUnblockedPerson() {
        Assert.assertEquals("redirect:/admin/person", adminController.unblockedPerson(id));
        verify(personService).getPersonById(id);
        verify(personService).unblockPerson(person = personService.getPersonById(id));
    }

    @Test
    public void TestGetCategory() {
        Assert.assertEquals("admin/listCategory", adminController.getCategory().getViewName());
        verify(categoryRepository).findAll();
    }

    @Test
    public void TestSaveCategory() {
        Assert.assertEquals("redirect:/admin/category", adminController.SaveCategory(category));
        verify(categoryRepository).save(category);
    }

}
