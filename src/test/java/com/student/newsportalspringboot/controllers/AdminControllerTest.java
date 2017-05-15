package com.student.newsportalspringboot.controllers;

import com.student.newsportalspringboot.entities.Admin;
import com.student.newsportalspringboot.entities.Category;
import com.student.newsportalspringboot.entities.Person;
import com.student.newsportalspringboot.entities.Post;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class AdminControllerTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private PersonService personService;
    @Mock
    private Category category;
    @Mock
    private Admin admin;
    @Mock
    private Page<Post> page;
    @Mock
    private Post post;

    @InjectMocks
    private AdminController adminController;

    private final String name = "name";
    private final long idLong = 1;
    private final String categoryName = "category";
    private final int MAX_SIZE_PAGES = 100;
    private final Sort sort = new Sort(Sort.Direction.DESC, "datePublication");
    private Person person;
    private final int idInt = 1;

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
        Assert.assertEquals("admin/listPerson", adminController.showAdmins().getViewName());
        verify(personService).listAllPerson();
        verify(categoryRepository).findAll();
    }

    @Test
    public void TestSaveAdminEnterCorrectEmailAndGetRedirect() {
        when(admin.getEmail()).thenReturn(name);
        Assert.assertEquals("redirect:/admin/person", adminController.saveAdmin(admin));
        verify(personService).saveAdmin(admin);
    }

    @Test
    public void TestDeleteAdmin() {
        Assert.assertEquals("redirect:/admin/person", adminController.deleteAdmin(idInt));
        verify(personService).deletePerson(idInt);
    }

    @Test
    public void TestBlockedPerson() {
        Assert.assertEquals("redirect:/admin/person", adminController.blockedPerson(idInt));
        verify(personService).getPersonById(idInt);
        verify(personService).blockPerson(person = personService.getPersonById(idInt));
    }

    @Test
    public void TestUnblockedPerson() {
        Assert.assertEquals("redirect:/admin/person", adminController.unblockedPerson(idInt));
        verify(personService).getPersonById(idInt);
        verify(personService).unblockPerson(person = personService.getPersonById(idInt));
    }

    @Test
    public void TestGetAllPost() {
        when(postRepository.findAll(new PageRequest(0, MAX_SIZE_PAGES, sort))).thenReturn(page);
        Assert.assertEquals("admin/listPost", adminController.getAllPost().getViewName());
        verify(postRepository).findAll(new PageRequest(0, MAX_SIZE_PAGES, sort));
        verify(categoryRepository).findAll();
    }

    @Test
    public void TestGetAllPostByCategory() {
        when(postRepository.findAllByCategory(categoryName, new PageRequest(0, MAX_SIZE_PAGES, sort))).thenReturn(page);
        Assert.assertEquals("admin/listPost", adminController.getAllPostByCategory(categoryName).getViewName());
        verify(postRepository).findAllByCategory(categoryName, new PageRequest(0, MAX_SIZE_PAGES, sort));
        verify(categoryRepository).findByUrl(categoryName);
        verify(categoryRepository).findAll();
    }

    @Test
    public void TestNewPost() {
        Assert.assertEquals("news/addPost", adminController.newPost().getViewName());
        verify(categoryRepository).findAll();
    }

    @Test
    public void TestEditPost() {
        Assert.assertEquals("news/addPost", adminController.editPost(idLong, categoryName).getViewName());
        verify(postRepository).findOne(idLong);
        verify(categoryRepository).findAll();
    }

    @Test
    public void TestSavePost() {
        Assert.assertEquals("redirect:/post/" + post.getCategory() + "/" + post.getId(), adminController.savePost(post));
        verify(postRepository).save(post);
    }

    @Test
    public void TestDeletePost() {
        Assert.assertEquals("redirect:/admin/post/all", adminController.deletePost(idLong));
        verify(postRepository).delete(idLong);
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

    @Test
    public void TestDeteleCategory() {
        Assert.assertEquals("redirect:/admin/category", adminController.deleteCategory(idInt));
        verify(categoryRepository).delete(idInt);
    }

}
