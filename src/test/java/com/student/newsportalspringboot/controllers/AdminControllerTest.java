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
    @Mock
    private Post news;

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
    public void setService() {
        adminController.setPostService(personService);
        Assert.assertEquals(this.personService, personService);
    }

    @Test
    public void setRepository() {
        adminController.setRepository(postRepository, categoryRepository);
        Assert.assertEquals(this.postRepository, postRepository);
        Assert.assertEquals(this.categoryRepository, categoryRepository);
    }

    @Test
    public void admin() {
        Assert.assertEquals("admin", adminController.admin());
    }

    @Test
    public void showsAdmins() {
        Assert.assertEquals("admin/listPerson", adminController.showAdmins().getViewName());
        verify(personService).listAllPerson();
        verify(categoryRepository).findAll();
    }

    @Test
    public void saveAdminEnterCorrectEmailAndGetRedirect() {
        when(admin.getEmail()).thenReturn(name);
        Assert.assertEquals("redirect:/admin/person", adminController.saveAdmin(admin));
        verify(personService).saveAdmin(admin);
    }

    @Test
    public void deleteAdmin() {
        Assert.assertEquals("redirect:/admin/person", adminController.deleteAdmin(idInt));
        verify(personService).deletePerson(idInt);
    }

    @Test
    public void blockedPerson() {
        Assert.assertEquals("redirect:/admin/person", adminController.blockedPerson(idInt));
        verify(personService).getPersonById(idInt);
        verify(personService).blockPerson(person = personService.getPersonById(idInt));
    }

    @Test
    public void unblockedPerson() {
        Assert.assertEquals("redirect:/admin/person", adminController.unblockedPerson(idInt));
        verify(personService).getPersonById(idInt);
        verify(personService).unblockPerson(person = personService.getPersonById(idInt));
    }

    @Test
    public void getAllPost() {
        when(postRepository.findAll(new PageRequest(0, MAX_SIZE_PAGES, sort))).thenReturn(page);
        Assert.assertEquals("admin/listPost", adminController.getAllPost().getViewName());
        verify(postRepository).findAll(new PageRequest(0, MAX_SIZE_PAGES, sort));
        verify(categoryRepository).findAll();
    }

    @Test
    public void getAllPostByCategory() {
        when(postRepository.findAllByCategory(categoryName, new PageRequest(0, MAX_SIZE_PAGES, sort))).thenReturn(page);
        Assert.assertEquals("admin/listPost", adminController.getAllPostByCategory(categoryName).getViewName());
        verify(postRepository).findAllByCategory(categoryName, new PageRequest(0, MAX_SIZE_PAGES, sort));
        verify(categoryRepository).findByUrl(categoryName);
        verify(categoryRepository).findAll();
    }

    @Test
    public void newPost() {
        Assert.assertEquals("news/addPost", adminController.newPost().getViewName());
        verify(categoryRepository).findAll();
    }

    @Test
    public void editPost() {
        Assert.assertEquals("news/addPost", adminController.editPost(idLong, categoryName).getViewName());
        verify(postRepository).findOne(idLong);
        verify(categoryRepository).findAll();
    }

    @Test
    public void savePost() {
        when(postRepository.exists(post.getId())).thenReturn(true);
        when(postRepository.findOne(post.getId())).thenReturn(news);
        Assert.assertEquals("redirect:/post/" + post.getCategory() + "/" + post.getId(), adminController.savePost(post));
        verify(postRepository).save(post);
        verify(postRepository).findOne(post.getId());
        verify(postRepository).exists(post.getId());
    }
    @Test
    public void deletePost() {
        Assert.assertEquals("redirect:/admin/post/all", adminController.deletePost(idLong));
        verify(postRepository).delete(idLong);
    }

    @Test
    public void getCategory() {
        Assert.assertEquals("admin/listCategory", adminController.getCategory().getViewName());
        verify(categoryRepository).findAll();
    }

    @Test
    public void saveCategory() {
        Assert.assertEquals("redirect:/admin/category", adminController.SaveCategory(category));
        verify(categoryRepository).save(category);
    }

    @Test
    public void deteleCategory() {
        Assert.assertEquals("redirect:/admin/category", adminController.deleteCategory(idInt));
        verify(categoryRepository).delete(idInt);
    }

}
