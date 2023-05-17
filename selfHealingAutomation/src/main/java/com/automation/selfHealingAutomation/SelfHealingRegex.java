package com.automation.selfHealingAutomation;

import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SelfHealingRegex {
	private WebDriver driver;

    public SelfHealingRegex() {
        // Initialize WebDriver (assuming ChromeDriver in this example)
        driver = new ChromeDriver();
    }

    public void clickButton() {
        String initialXPath = "//button[@id='initialButtonId']"; // Initial XPath to start with

        WebElement button = findElementByDynamicXPath(initialXPath);

        if (button != null) {
            System.out.println("Button found: " + button.getAttribute("outerHTML"));
            button.click();
        } else {
            System.out.println("Button not found");
        }
    }

    private WebElement findElementByDynamicXPath(String initialXPath) {
        WebElement element = null;

        try {
            element = driver.findElement(By.xpath(initialXPath));
        } catch (NoSuchElementException e) {
            System.out.println("Initial XPath not found: " + initialXPath);
        }

        if (element != null) {
            // Build a dynamic XPath based on the found element's attributes
            String dynamicXPath = buildDynamicXPath(element);
            System.out.println("Dynamic XPath: " + dynamicXPath);

            try {
                element = driver.findElement(By.xpath(dynamicXPath));
            } catch (NoSuchElementException e) {
                System.out.println("Dynamic XPath not found: " + dynamicXPath);
                element = null;
            }
        }

        return element;
    }

    private String buildDynamicXPath(WebElement element) {
        String outerHTML = element.getAttribute("outerHTML");

        // Regex patterns for extracting attributes
        String idPattern = "id=['\"]([^'\"]+)['\"]";
        String classPattern = "class=['\"]([^'\"]+)['\"]";
        String textPattern = ">([^<]+)<";

        // Extract attribute values using regex
        String id = extractAttribute(outerHTML, idPattern);
        String className = extractAttribute(outerHTML, classPattern);
        String buttonText = extractAttribute(outerHTML, textPattern);

        StringBuilder dynamicXPath = new StringBuilder("//")
                .append(element.getTagName());

        if (id != null) {
            dynamicXPath.append("[@id='").append(id).append("']");
        } else if (className != null) {
            dynamicXPath.append("[contains(@class,'").append(className).append("')]");
        } else if (buttonText != null) {
            dynamicXPath.append("[contains(text(),'").append(buttonText).append("')]");
        }

        return dynamicXPath.toString();
    }

    private String extractAttribute(String input, String pattern) {
        Pattern attributePattern = Pattern.compile(pattern);
        java.util.regex.Matcher matcher = attributePattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static void main(String[] args) {
        SelfHealingRegex example = new SelfHealingRegex();
        example.clickButton();
        example.quit();
    }

    public void quit() {
        // Quit the WebDriver
        if (driver != null) {
            driver.quit();
        }
    }
}