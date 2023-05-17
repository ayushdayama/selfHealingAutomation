package com.automation.selfHealingAutomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SelfHealingMultipleXPaths {
	private WebDriver driver;

    public SelfHealingMultipleXPaths() {
        // Initialize WebDriver (assuming ChromeDriver in this example)
        driver = new ChromeDriver();
    }

    public void clickButton() {
        String[] xpaths = {
            "//button[@id='buttonId']",  // Initial XPath
            "//button[@class='buttonClass']",  // XPath after first update
            "//button[contains(text(),'Button Text')]"  // XPath after second update
            // Add more XPaths for subsequent updates
        };

        WebElement button = null;

        for (String xpath : xpaths) {
            try {
                button = driver.findElement(By.xpath(xpath));
                break;  // Stop searching if element is found
            } catch (Exception e) {
                System.out.println("XPath not found: " + xpath);
            }
        }

        if (button != null) {
            System.out.println("Button found: " + button.getAttribute("outerHTML"));
            button.click();
        } else {
            System.out.println("Button not found");
        }
    }

    public static void main(String[] args) {
    	SelfHealingMultipleXPaths example = new SelfHealingMultipleXPaths();
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