package com.company;

import java.util.ArrayList;
import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Converter converter = new Converter();
        converter.readMarkDownFile("/home/juliazhao/Documents/MyCode/Java/MarkdownConverter/test.md");
        converter.analysisMarkDown("/home/juliazhao/Documents/MyCode/Java/MarkdownConverter/test.md");
        converter.writeToFile("/home/juliazhao/Documents/MyCode/Java/MarkdownConverter/test.html");
    }
}
