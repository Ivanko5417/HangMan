package com.hangman.database;

import android.content.Context;

import com.hangman.word.GlobalWords;
import com.hangman.word.WordStructure;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ReadXML implements GlobalWords {

    public static void readFile(Context context) {

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(context.getAssets().open("hmwords.xml"));

            doc.getDocumentElement().normalize();
//
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//
            NodeList nList = doc.getElementsByTagName("word");
//
            System.out.println(nList.getLength());
//
            for (int temp = 0; temp < nList.getLength(); temp++) {
                WordStructure word = null;

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    String text = eElement.getElementsByTagName("text").item(0).getTextContent();
                    String category = eElement.getElementsByTagName("category").item(0).getTextContent();
                    word = new WordStructure(text, category);
                    structuredWordList.add(word);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}