package pom.posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import pom.posters.pageobjects.pages.browsing.CategoryPage;
import pom.posters.pageobjects.pages.browsing.NoHitsPage;

public class Search extends AbstractComponent
{
    private final SelenideElement searchField = $("#s");

    @Override
    public void isComponentAvailable()
    {
        searchField.should(exist);
    }

    @Step("search for \"{searchTerm}\" without result")
    public NoHitsPage noResult(final String searchTerm)
    {
        search(searchTerm);
        return new NoHitsPage();
    }

    @Step("search for \"{searchTerm}\" with result")
    public CategoryPage categoryPageResult(final String searchTerm)
    {
        search(searchTerm);
        return new CategoryPage();
    }

    @Step("search for \"{searchTerm}\"")
    public void search(final String searchTerm)
    {
        openSearch();
        searchField.val(searchTerm).pressEnter();
    }

    @Step("open search field")
    public void openSearch()
    {
        $("#header-search-trigger").scrollTo().click();
    }

    @Step("validate that {searchTerm} is still visible after")
    public void validateSearchTerm(final String searchTerm)
    {
        // Validate the entered search phrase is still visible in the input
        openSearch();
        // Validate the entered search phrase is still visible in the input
        searchField.should(visible);
        // Validate the entered search phrase is still visible in the input
        searchField.should(exactValue(searchTerm));
    }
}
