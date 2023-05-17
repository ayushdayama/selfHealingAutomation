package com.automation.selfHealingAutomation;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SelfHealingCreateXPath {
	private WebDriver driver;

    public SelfHealingCreateXPath() {
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
        String tagName = element.getTagName();
        String id = element.getAttribute("id");
        String className = element.getAttribute("class");
        String buttonText = element.getText();

        StringBuilder dynamicXPath = new StringBuilder("//")
            .append(tagName);

        if (!id.isEmpty()) {
            dynamicXPath.append("[@id='").append(id).append("']");
        } else if (!className.isEmpty()) {
            dynamicXPath.append("[contains(@class,'").append(className).append("')]");
        } else if (!buttonText.isEmpty()) {
            dynamicXPath.append("[contains(text(),'").append(buttonText).append("')]");
        }

        return dynamicXPath.toString();
    }

    public static void main(String[] args) {
        SelfHealingCreateXPath example = new SelfHealingCreateXPath();
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