package com.student.newsportalspringboot.controllers;

import com.student.newsportalspringboot.entities.Category;
import com.student.newsportalspringboot.entities.Post;
import com.student.newsportalspringboot.repositories.CategoryRepository;
import com.student.newsportalspringboot.repositories.PostRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

public class PostControllerTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private Category c;
    @Mock
    private Page<Post> page;
    @InjectMocks
    private PostController postController;

    private StringBuilder stringBuilder;
    private final String category = "category";
    private final int MAX_SIZE_PAGES = 5;
    private final Sort sort = new Sort(Sort.Direction.DESC, "datePublication");

    @Before
    public void setUp() {
        postController = new PostController();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void TestMethodPost() {
        Assert.assertEquals("redirect:/post/page/1", postController.post());
    }

    @Test
    public void TestMethodPostByCategory() {
        Assert.assertEquals("redirect:/post/{category}/page/1", postController.postByCategory(category));
    }

    @Test
    public void TestMethodShowPostEnterStringValueIdAndGetError404() {
        stringBuilder = new StringBuilder("number");
        Assert.assertEquals("error/404", postController.showPost(stringBuilder.toString(), category).getViewName());
        Assert.assertEquals(HttpStatus.NOT_FOUND, postController.showPost(stringBuilder.toString(), category).getStatus());

    }

    @Test
    public void TestMethodShowPostEnterIncorrectValueIdAndGetError404() {
        stringBuilder = new StringBuilder("-5");
        Assert.assertEquals("error/404", postController.showPost(stringBuilder.toString(), category).getViewName());
        Assert.assertEquals(HttpStatus.NOT_FOUND, postController.showPost(stringBuilder.toString(), category).getStatus());

    }

    @Test
    public void TestMethodShowPostAndGetModel() {
        stringBuilder = new StringBuilder("1");
        when(postRepository.exists(Long.parseLong(stringBuilder.toString()))).thenReturn(true);
        when(categoryRepository.findByUrl(category)).thenReturn(c);
        Assert.assertEquals("news/post", postController.showPost(stringBuilder.toString(), category).getViewName());
        verify(postRepository).exists(Long.parseLong(stringBuilder.toString()));
        verify(postRepository).findOne(Long.parseLong(stringBuilder.toString()));
        verify(categoryRepository).findByUrl(category);
        verify(categoryRepository).findAll();

    }

    @Test
    public void TestMethodShowAllPostEnterStringPageAndGetRedirect() {
        stringBuilder = new StringBuilder("number");
        Assert.assertEquals("redirect:/post/page/1", postController.showAllPost(stringBuilder.toString()).getViewName());
    }

    @Test
    public void TestMethodShowAllPostEnterNegativeNumberPageAndGetRedirect() {
        stringBuilder = new StringBuilder("-1");
        Assert.assertEquals("redirect:/post/page/1", postController.showAllPost(stringBuilder.toString()).getViewName());
    }

    @Test
    public void TestMethodShowAllPostEnterZeroNumberPageAndGetRedirect() {
        stringBuilder = new StringBuilder("0");
        Assert.assertEquals("redirect:/post/page/1", postController.showAllPost(stringBuilder.toString()).getViewName());
    }

    @Test
    public void TestMethodShowAllPostByCategoryEnterStringPageAndGetRedirect() {
        stringBuilder = new StringBuilder("number");
        when(categoryRepository.existsByUrl(category)).thenReturn(true);
        Assert.assertEquals("redirect:/post/" + category + "/page/1", postController.showPostByCategory(category, stringBuilder.toString()).getViewName());
    }

    @Test
    public void TestMethodShowAllPostByCategoryEnterNegativeNumberPageAndGetRedirect() {
        stringBuilder = new StringBuilder("-1");
        when(categoryRepository.existsByUrl(category)).thenReturn(true);
        Assert.assertEquals("redirect:/post/" + category + "/page/1", postController.showPostByCategory(category, stringBuilder.toString()).getViewName());
    }

    @Test
    public void TestMethodShowAllPostByCategoryEnterZeroNumberPageAndGetRedirect() {
        stringBuilder = new StringBuilder("0");
        when(categoryRepository.existsByUrl(category)).thenReturn(true);
        Assert.assertEquals("redirect:/post/" + category + "/page/1", postController.showPostByCategory(category, stringBuilder.toString()).getViewName());
    }

    @Test
    public void TestMethodShowAllPostByCategoryEnterNotCategoryAndGetRedirect() {
        stringBuilder = new StringBuilder("1");
        when(categoryRepository.existsByUrl(category)).thenReturn(false);
        Assert.assertEquals("redirect:/post/page/1", postController.showPostByCategory(category, stringBuilder.toString()).getViewName());
    }

    @Test
    public void TestMethodShowAllPostAndGetModel() {
        stringBuilder = new StringBuilder("1");
        when(postRepository.findAll(new PageRequest(Integer.parseInt(stringBuilder.toString()) - 1, MAX_SIZE_PAGES, sort))).thenReturn(page);
        when(page.hasContent()).thenReturn(true);
        Assert.assertEquals("news/listPost", postController.showAllPost(stringBuilder.toString()).getViewName());
        verify(postRepository).findAll(new PageRequest(Integer.parseInt(stringBuilder.toString()) - 1, MAX_SIZE_PAGES, sort));
        verify(categoryRepository).findAll();
    }

    @Test
    public void TestMethodShowAllPostByCategoryAndGetModel() {
        stringBuilder = new StringBuilder("1");
        when(categoryRepository.existsByUrl(category)).thenReturn(true);
        when(categoryRepository.findByUrl(category)).thenReturn(c);
        when(postRepository.findAllByCategory(c.getUrl(), new PageRequest(Integer.parseInt(stringBuilder.toString()) - 1, MAX_SIZE_PAGES, sort))).thenReturn(page);
        when(page.hasContent()).thenReturn(true);
        Assert.assertEquals("news/listPost", postController.showPostByCategory(category, stringBuilder.toString()).getViewName());
    }

}
