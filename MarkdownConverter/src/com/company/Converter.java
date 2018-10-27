package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

public class Converter {

    public ArrayList<String> html;

    public Converter() {
    }

    public ArrayList<String> readMarkDownFile(String path) {
        ArrayList<String> textdata = new ArrayList<String>();
        File file = new File(path);
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                textdata.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return textdata;
    }

    public void analysisMarkDown(String path) {
        ArrayList<String> textdata = readMarkDownFile(path);
        html = new ArrayList<String>();
        for (int i = 0; i < textdata.size(); i++) {
            String linedata = textdata.get(i);
            String convertToHtml = new String();
            if (canConvertHeader(linedata) == true) {
                String head = convertHeader(linedata);
                out.println(head);
                if (canConvertItalic(head) == true) {
                    convertToHtml = convertItalic(head);
                    out.println(convertToHtml);
                    html.add(convertToHtml);
                } else {
                    html.add(head);
                }
            } else if (canConvertParagraph(linedata) == true) {
                convertToHtml = convertParagraph(linedata);
                html.add(convertToHtml);
            } else if (canConvertBold(linedata) == true) {
                convertToHtml = convertBold(linedata);
                html.add(convertToHtml);
            } else if (canConvertItalic(linedata) == true) {
                convertToHtml = convertItalic(linedata);
                html.add(convertToHtml);
            } else if (canConvertList(linedata) == true) {
                convertToHtml = convertList(linedata);
                html.add(convertToHtml);
            } else if (canConvertLink(linedata) == true) {
                convertToHtml = convertLink(linedata);
                html.add(convertToHtml);
            } else {
                html.add(linedata);
            }
        }
    }

    public boolean canConvertHeader(String text) {
        String pattern = "(#+)(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    public String convertHeader(String text) {
        String result = new String();
        String pattern = "(#+)(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find()) {
            if (m.group(1).length() == 1) {
                result = "<h1>" + m.group(2) + "</h1>";
            } else if (m.group(1).length() == 2) {
                result = "<h2>" + m.group(2) + "</h2>";
            } else if (m.group(1).length() == 3) {
                result = "<h3>" + m.group(2) + "</h3>";
            } else if (m.group(1).length() == 4) {
                result = "<h4>" + m.group() + "</h3>";
            }
        }
        return result;
    }

    public String convertItalic(String text) {
        String result = new String();
        String pattern = new String("(\\*)(.*?)\\1");
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find()) {
            out.println(m.start());
            out.println(m.end());
            if (m.start() == 0 && m.end() == text.length() - 1) {
                result = "<em>" + m.group(2) + "</em>";
            } else if (m.start() != 0 && m.end() == text.length() - 1) {
                result = text.substring(0, m.start() - 1) + " " + "<em>" + m.group(2) + "</em>";
            } else if (m.start() == 0 && m.end() != text.length() - 1) {
                result = "<em>" + m.group(2) + "</em>" + text.substring(m.end(), text.length());
            } else {
                result = text.substring(0, m.start() - 1) + " " + "<em>" + m.group(2) + "</em>"
                        + text.substring(m.end(), text.length());
            }

        }
        return result;
    }

    public boolean canConvertItalic(String text) {
        String pattern = new String("(\\*|_)(.*?)\\1");
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    public String convertBold(String text) {
        String result = new String();
        String pattern = "(\\*\\*|__)(.*?)\\1";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find()) {
            out.println(m.start());
            out.println(m.end());
            if (m.start() == 0 && m.end() == text.length() - 1) {
                result = "<strong>" + m.group(2) + "</strong>";
            } else if (m.start() != 0 && m.end() == text.length() - 1) {
                result = text.substring(0, m.start() - 1) + " " + "<strong>" + m.group(2) + "</strong>";
            } else if (m.start() == 0 && m.end() != text.length() - 1) {
                result = "<strong>" + m.group(2) + "</strong>" + text.substring(m.end(), text.length());
            } else {
                result = text.substring(0, m.start() - 1) + " " + "<strong>" + m.group(2) + "</strong>"
                        + text.substring(m.end(), text.length());
            }

        }
        return result;
    }

    public boolean canConvertBold(String text) {
        String pattern = "(\\*\\*|__)(.*?)\\1";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    public String convertList(String text) {
        String result = new String();
        String pattern = "\\*(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find()) {
            result = "<li>" + m.group(1) + "</li>";
        }
        return result;
    }

    public boolean canConvertList(String text) {
        String pattern = "\\*(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    public String convertLink(String text) {
        String result = new String();
        String pattern = "\\[([^\\[]+)\\]\\(([^\\)]+)\\)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find()) {
            if (m.start() != 0) {
                result = text.substring(0, m.start() - 1) + "<a href=\"" + m.group(2) + "\">"
                        + m.group(1) + "</a>";
            } else if (m.end() != text.length()) {
                result = "<a href=\"" + m.group(2) + "\">"
                        + m.group(1) + "</a>" + text.substring(m.end(), text.length() - 1);
            } else {
                result = "<a href=\"" + m.group(2) + "\">"
                        + m.group(1) + "</a>";
            }
        }
        return result;
    }

    public boolean canConvertLink(String text) {
        String pattern = "\\[([^\\[]+)\\]\\(([^\\)]+)\\)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }


    public boolean canConvertParagraph(String text) {
        String pattern = "\n([^\n]+)\n";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find()) {
            return true;
        } else {
            return false;
        }

    }

    public String convertParagraph(String text) {
        String result = new String();
        String pattern = "\n([^\n]+\n)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(text);
        if (m.find()) {
            result = "<p>" + m.group(1) + "</p>";
        }
        return result;

    }

    public void writeToFile(String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (int i = 0; i < html.size(); i++) {
                writer.write(html.get(i));
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
